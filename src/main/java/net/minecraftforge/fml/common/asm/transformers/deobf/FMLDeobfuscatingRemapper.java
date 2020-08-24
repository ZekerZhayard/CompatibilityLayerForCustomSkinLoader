package net.minecraftforge.fml.common.asm.transformers.deobf;

import java.io.File;
import java.util.Set;

import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.commons.Remapper;

public class FMLDeobfuscatingRemapper extends Remapper {
    public final static FMLDeobfuscatingRemapper INSTANCE = new FMLDeobfuscatingRemapper();
    private static final cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper instance = cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper.INSTANCE;

    public void setupLoadOnly(String deobfFileName, boolean loadAll) {
        instance.setupLoadOnly(deobfFileName, loadAll);
    }

    public void setup(File mcDir, LaunchClassLoader classLoader, String deobfFileName) {
        instance.setup(mcDir, classLoader, deobfFileName);
    }

    public boolean isRemappedClass(String className) {
        return instance.isRemappedClass(className);
    }

    public String unmap(String typeName) {
        return instance.unmap(typeName);
    }

    public void mergeSuperMaps(String name, String superName, String[] interfaces) {
        instance.mergeSuperMaps(name, superName, interfaces);
    }

    public Set<String> getObfedClasses() {
        return instance.getObfedClasses();
    }

    public String getStaticFieldType(String oldType, String oldName, String newType, String newName) {
        return instance.getStaticFieldType(oldType, oldName, newType, newName);
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        return instance.mapMethodName(owner, name, desc);
    }

    @Override
    public String mapMethodDesc(String desc) {
        return instance.mapMethodDesc(desc);
    }

    @Override
    public String map(String typeName) {
        return instance.map(typeName);
    }

    @Override
    public String mapDesc(String desc) {
        return instance.mapDesc(desc);
    }

    @Override
    public String mapType(String type) {
        return instance.mapType(type);
    }

    @Override
    public String[] mapTypes(String[] types) {
        return instance.mapTypes(types);
    }

    @Override
    public Object mapValue(Object value) {
        return instance.mapValue(value);
    }

    @Override
    public String mapSignature(String signature, boolean typeSignature) {
        return instance.mapSignature(signature, typeSignature);
    }

    @Override
    public String mapInvokeDynamicMethodName(String name, String desc) {
        return instance.mapInvokeDynamicMethodName(name, desc);
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        return instance.mapFieldName(owner, name, desc);
    }
}
