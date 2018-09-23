package org.xbib.gradle.plugin.shadow


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.xbib.gradle.plugin.shadow.tasks.ConfigureShadowRelocation
import org.xbib.gradle.plugin.shadow.tasks.ShadowJar

class PluginShadowPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply(ShadowPlugin)
        project.tasks.withType(ShadowJar) { ShadowJar task ->
            if (task.name == ShadowJavaPlugin.SHADOW_JAR_TASK_NAME) {
                ConfigureShadowRelocation relocate = project.tasks.create(ConfigureShadowRelocation.taskName(project.tasks.shadowJar), ConfigureShadowRelocation)
                relocate.target = (ShadowJar) project.tasks.shadowJar
                project.tasks.shadowJar.dependsOn relocate
            }
        }
    }
}
