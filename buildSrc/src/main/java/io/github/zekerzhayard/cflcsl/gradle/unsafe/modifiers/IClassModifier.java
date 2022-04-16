package io.github.zekerzhayard.cflcsl.gradle.unsafe.modifiers;

public interface IClassModifier {
    String getClassName();

    byte[] modify(byte[] classBytes);
}
