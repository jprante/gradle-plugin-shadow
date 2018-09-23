package org.xbib.gradle.plugin.shadow

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer
import org.gradle.api.artifacts.maven.MavenPom
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.MavenPlugin
import org.gradle.api.tasks.Upload
import org.gradle.configuration.project.ProjectConfigurationActionContainer
import org.xbib.gradle.plugin.shadow.tasks.ShadowJar

import javax.inject.Inject

class ShadowJavaPlugin implements Plugin<Project> {

    static final String SHADOW_JAR_TASK_NAME = 'shadowJar'

    static final String SHADOW_UPLOAD_TASK = 'uploadShadow'

    static final String SHADOW_GROUP = 'Shadow'

    private final ProjectConfigurationActionContainer configurationActionContainer;

    @Inject
    ShadowJavaPlugin(ProjectConfigurationActionContainer configurationActionContainer) {
        this.configurationActionContainer = configurationActionContainer
    }

    @Override
    void apply(Project project) {
        configureShadowTask(project)

        project.configurations.compileClasspath.extendsFrom project.configurations.shadow

    }

    protected void configureShadowTask(Project project) {
        JavaPluginConvention convention = project.convention.getPlugin(JavaPluginConvention)
        ShadowJar shadow = project.tasks.create(SHADOW_JAR_TASK_NAME, ShadowJar)
        shadow.group = SHADOW_GROUP
        shadow.description = 'Create a combined JAR of project and runtime dependencies'
        shadow.conventionMapping.with {
            map('classifier') {
                'all'
            }
        }
        shadow.manifest.inheritFrom project.tasks.jar.manifest
        shadow.doFirst {
            def files = project.configurations.findByName(ShadowBasePlugin.CONFIGURATION_NAME).files
            if (files) {
                def libs = [project.tasks.jar.manifest.attributes.get('Class-Path')]
                libs.addAll files.collect { "${it.name}" }
                manifest.attributes 'Class-Path': libs.findAll { it }.join(' ')
            }
        }
        shadow.from(convention.sourceSets.main.output)
        shadow.configurations = [project.configurations.findByName('runtimeClasspath') ?
                                         project.configurations.runtimeClasspath : project.configurations.runtime]
        shadow.exclude('META-INF/INDEX.LIST', 'META-INF/*.SF', 'META-INF/*.DSA', 'META-INF/*.RSA', 'module-info.class')

        project.artifacts.add(ShadowBasePlugin.CONFIGURATION_NAME, shadow)
        configurationActionContainer.add(new Action<Project>() {
            void execute(Project p) {
                p.plugins.withType(MavenPlugin) {
                    Upload upload = p.tasks.withType(Upload).findByName(SHADOW_UPLOAD_TASK)
                    if (!upload) {
                        return
                    }
                    upload.configuration = p.configurations.shadow
                    MavenPom pom = upload.repositories.mavenDeployer.pom
                    pom.scopeMappings.mappings.remove(p.configurations.compile)
                    pom.scopeMappings.mappings.remove(p.configurations.runtime)
                    pom.scopeMappings.addMapping(MavenPlugin.RUNTIME_PRIORITY, p.configurations.shadow,
                            Conf2ScopeMappingContainer.RUNTIME)
                }
            }
        })
    }
}
