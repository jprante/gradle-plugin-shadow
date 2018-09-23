package org.xbib.gradle.plugin.shadow.internal

import org.gradle.api.internal.file.archive.compression.ArchiveOutputStreamFactory
import org.xbib.gradle.plugin.shadow.zip.ZipOutputStream

interface ZipCompressor extends ArchiveOutputStreamFactory {

    ZipOutputStream createArchiveOutputStream(File destination)

}
