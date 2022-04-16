package io.github.zekerzhayard.cflcsl.gradle.unsafe.modifiers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ProcessJarTaskModifier implements IClassModifier {
    @Override
    public String getClassName() {
        return "net/minecraftforge/gradle/tasks/ProcessJarTask.class";
    }

    @Override
    public byte[] modify(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, ClassReader.EXPAND_FRAMES);
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("deobfJar") && mn.desc.equals("(Ljava/io/File;Ljava/io/File;Ljava/io/File;Ljava/util/Collection;)V")) {
                for (AbstractInsnNode ain : mn.instructions.toArray()) {
                    if (ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) ain;
                        if (min.owner.equals("net/md_5/specialsource/provider/JointProvider") && min.name.equals("add") && min.desc.equals("(Lnet/md_5/specialsource/provider/InheritanceProvider;)V")) {
                            mn.instructions.insertBefore(min, new InsnNode(Opcodes.SWAP));
                            mn.instructions.insertBefore(min, new InsnNode(Opcodes.DUP_X1));
                            mn.instructions.insertBefore(min, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/cflcsl/gradle/unsafe/modifiers/hooks/ProcessJarTaskHook", "addProvider", "(Lnet/md_5/specialsource/provider/InheritanceProvider;Lnet/md_5/specialsource/provider/JointProvider;)Lnet/md_5/specialsource/provider/InheritanceProvider;", false));
                        }
                    }
                }
            }
        }
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();
    }
}
