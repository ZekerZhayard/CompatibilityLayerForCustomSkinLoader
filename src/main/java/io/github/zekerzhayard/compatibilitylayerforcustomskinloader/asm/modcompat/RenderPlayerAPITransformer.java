package io.github.zekerzhayard.compatibilitylayerforcustomskinloader.asm.modcompat;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RenderPlayerAPITransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.model.ModelPlayer")) {
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            new ClassReader(basicClass).accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    super.visit(version, access, name, signature, "api/player/model/ModelPlayer", interfaces);
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                            super.visitMethodInsn(opcode, opcode == Opcodes.INVOKESPECIAL && owner.equals("net/minecraft/client/model/ModelBiped") ? "api/player/model/ModelPlayer" : owner, name, desc, itf);
                        }
                    };
                }
            }, ClassReader.EXPAND_FRAMES);
            basicClass = classWriter.toByteArray();
        }
        return basicClass;
    }
}
