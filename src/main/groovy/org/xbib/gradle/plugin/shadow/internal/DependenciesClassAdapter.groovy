package org.xbib.gradle.plugin.shadow.internal

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.TypePath
import org.objectweb.asm.commons.ClassRemapper
import org.objectweb.asm.commons.Remapper

class DependenciesClassAdapter extends ClassRemapper {

    private static final EmptyVisitor ev = new EmptyVisitor()

    DependenciesClassAdapter() {
        super(ev, new CollectingRemapper())
    }

    Set<String> getDependencies() {
        return ((CollectingRemapper) super.remapper).classes
    }

    private static class CollectingRemapper extends Remapper {
        Set<String> classes = new HashSet<String>()

        @Override
        String map(String pClassName) {
            classes.add(pClassName.replace('/', '.'))
            pClassName
        }
    }

    static class EmptyVisitor extends ClassVisitor {

        private static final AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM6) {
            @Override
            AnnotationVisitor visitAnnotation(String name, String desc) {
                this
            }

            @Override
            AnnotationVisitor visitArray(String name) {
                this
            }
        };

        private static final MethodVisitor mv = new MethodVisitor(Opcodes.ASM6) {
            @Override
            AnnotationVisitor visitAnnotationDefault() {
                av
            }

            @Override
            AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                av
            }

            @Override
            AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
                av
            }

            @Override
            AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                av
            }

            @Override
            AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start,
                                                                   Label[] end, int[] index, String descriptor,
                                                                   boolean visible) {
                av
            }

            @Override
            AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible ) {
                av
            }

            @Override
            AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible ) {
                av
            }
        };

        private static final FieldVisitor fieldVisitor = new FieldVisitor(Opcodes.ASM6) {
            @Override
            AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                av
            }
            @Override
            AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                av
            }
        };

        EmptyVisitor() {
            super(Opcodes.ASM6);
        }

        @Override
        AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            av
        }

        @Override
        FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            fieldVisitor
        }

        @Override
        MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            mv
        }

        @Override
        AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible ) {
            av
        }
    }
}
