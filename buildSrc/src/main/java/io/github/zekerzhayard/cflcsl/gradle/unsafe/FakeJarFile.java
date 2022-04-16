package io.github.zekerzhayard.cflcsl.gradle.unsafe;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.IOUtils;

public class FakeJarFile extends JarFile {
    // This constructor will be skipped.
    public FakeJarFile(String name) throws IOException {
        super(name);
    }

    public void clone(JarFile jarFile, Class<?> clazz) throws Throwable {
        if (clazz == null || !clazz.isInstance(jarFile)) {
            return;
        }
        Field[] fields = (Field[]) Constants.IMPL_LOOKUP.findVirtual(Class.class, "getDeclaredFields0", MethodType.methodType(Field[].class, boolean.class)).invokeWithArguments(clazz, false);
        for (Field field : fields) {
            if ((field.getModifiers() | Modifier.STATIC) != field.getModifiers()) {
                Constants.IMPL_LOOKUP.findSetter(field.getDeclaringClass(), field.getName(), field.getType())
                    .invokeWithArguments(this, Constants.IMPL_LOOKUP.findGetter(field.getDeclaringClass(), field.getName(), field.getType()).invokeWithArguments(jarFile));
            }
        }
        this.clone(jarFile, clazz.getSuperclass());
    }

    @Override
    public synchronized InputStream getInputStream(ZipEntry ze) throws IOException {
        InputStream is = super.getInputStream(ze);
        if (ze == null || !ClassModifierManager.classModifiers.containsKey(ze.getName())) {
            return is;
        }

        byte[] classBytes = ClassModifierManager.classModifiers.get(ze.getName()).modify(IOUtils.toByteArray(is));
        ze.setSize(classBytes.length);
        return new ByteArrayInputStream(classBytes);
    }
}
