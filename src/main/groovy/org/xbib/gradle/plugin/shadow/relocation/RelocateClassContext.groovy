package org.xbib.gradle.plugin.shadow.relocation

import org.xbib.gradle.plugin.shadow.ShadowStats
import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Canonical
@Builder
class RelocateClassContext {

    String className
    ShadowStats stats

}
