package org.xbib.gradle.plugin.shadow.transformers

import org.xbib.gradle.plugin.shadow.ShadowStats
import org.xbib.gradle.plugin.shadow.relocation.Relocator
import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Canonical
@Builder
class TransformerContext {

    String path

    InputStream inputStream

    List<Relocator> relocators

    ShadowStats stats
}
