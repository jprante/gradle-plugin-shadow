package org.xbib.gradle.plugin.shadow.transformers

import org.gradle.api.file.FileTreeElement
import org.xbib.gradle.plugin.shadow.zip.ZipEntry
import org.xbib.gradle.plugin.shadow.zip.ZipOutputStream

import java.util.jar.*
import java.util.jar.Attributes.Name

/**
 * A resource processor that allows the arbitrary addition of attributes to
 * the first MANIFEST.MF that is found in the set of JARs being processed, or
 * to a newly created manifest for the shaded JAR.
 * Modified from org.apache.maven.plugins.shade.resource.ManifestResourceTransformer
 */
class ManifestResourceTransformer implements Transformer {

    // Configuration
    private String mainClass

    private Map<String, Attributes> manifestEntries

    // Fields
    private boolean manifestDiscovered

    private Manifest manifest

    @Override
    boolean canTransformResource(FileTreeElement element) {
        def path = element.relativePath.pathString
        if (JarFile.MANIFEST_NAME.equalsIgnoreCase(path)) {
            return true
        }
        return false
    }

    @Override
    void transform(TransformerContext context) {
        // We just want to take the first manifest we come across as that's our project's manifest. This is the behavior
        // now which is situational at best. Right now there is no context passed in with the processing so we cannot
        // tell what artifact is being processed.
        if (!manifestDiscovered) {
            manifest = new Manifest(context.inputStream)
            manifestDiscovered = true
            if (context.inputStream) {
                context.inputStream.close()
            }
        }
    }

    @Override
    boolean hasTransformedResource() {
        return true
    }

    @Override
    void modifyOutputStream(ZipOutputStream os) {
        // If we didn't find a manifest, then let's create one.
        if (manifest == null) {
            manifest = new Manifest()
        }

        Attributes attributes = manifest.getMainAttributes()

        if (mainClass != null) {
            attributes.put(Name.MAIN_CLASS, mainClass)
        }

        if (manifestEntries != null) {
            for (Map.Entry<String, Attributes> entry : manifestEntries.entrySet()) {
                attributes.put(new Name(entry.getKey()), entry.getValue())
            }
        }

        os.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME))
        manifest.write(os)
    }

    ManifestResourceTransformer attributes(Map<String, ?> attributes) {
        if (manifestEntries == null) {
            manifestEntries = [:]
        }
        manifestEntries.putAll(attributes)
        this
    }
}
