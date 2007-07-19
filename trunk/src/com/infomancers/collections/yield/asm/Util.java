package com.infomancers.collections.yield.asm;

import org.objectweb.asm.Opcodes;

/**
 * Utility class for doing redundant checks on fields, variables and methods while
 * visiting different classes.
 */
final class Util {
    private final static String[] descs = {"I", "L", "F", "D", "Ljava/lang/Object;"};

    public static boolean isYieldNextCoreMethod(String name, String desc) {
        return "yieldNextCore".equals(name) && "()V".equals(desc);
    }

    static boolean isInvokeYieldReturn(int opcode, String name, String desc) {
        return opcode == Opcodes.INVOKEVIRTUAL && "yieldReturn".equals(name) && "(Ljava/lang/Object;)V".equals(desc);
    }

    public static boolean isYielderClassName(String name) {
        return "com/infomancers/collections/yield/Yielder".equals(name);
    }

    public static int offsetForDesc(String desc) {
        for (int i = 0; i < descs.length; i++) {
            String cur = descs[i];
            if (cur.equals(desc)) {
                return i;
            }
        }

        return -1;
    }

    public static String descForOffset(int offset) {
        return descs[offset];
    }
}
