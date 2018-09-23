package org.xbib.gradle.plugin.shadow.internal

import static org.junit.Assert.assertEquals

import org.junit.Test

import static org.xbib.gradle.plugin.shadow.internal.DependencyUtils.getDependenciesOfClass

class DependencyUtilsTest {

    @Test
    void testShouldFindDependenciesOfClassObject() throws Exception {
        Collection<String> dependencies = getDependenciesOfClass(Object).sort()
        Collection<String> expectedDependencies = Arrays.asList(
                "java.lang.Class",
                "java.lang.CloneNotSupportedException",
                "java.lang.Deprecated", // Java 11
                "java.lang.IllegalArgumentException",
                "java.lang.Integer",
                "java.lang.InterruptedException",
                "java.lang.Object",
                "java.lang.String",
                "java.lang.StringBuilder",
                "java.lang.Throwable",
                "jdk.internal.HotSpotIntrinsicCandidate" // Java 11
                ).sort()
        assertEquals(expectedDependencies, dependencies)
    }
}
