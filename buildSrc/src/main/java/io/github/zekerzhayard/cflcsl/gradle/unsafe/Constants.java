package io.github.zekerzhayard.cflcsl.gradle.unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@SuppressWarnings("sunapi")
public class Constants {
    public final static sun.misc.Unsafe UNSAFE;
    public final static MethodHandles.Lookup IMPL_LOOKUP;

    static {
        try {
            Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) unsafeField.get(null);

            Field implLookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP = (MethodHandles.Lookup) UNSAFE.getObject(UNSAFE.staticFieldBase(implLookupField), UNSAFE.staticFieldOffset(implLookupField));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
