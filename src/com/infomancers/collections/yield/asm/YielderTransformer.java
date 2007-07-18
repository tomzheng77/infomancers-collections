package com.infomancers.collections.yield.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.EmptyVisitor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by IntelliJ IDEA.
* User: freakazoid
* Date: Jul 18, 2007
* Time: 11:16:54 AM
* To change this template use File | Settings | File Templates.
*/
public class YielderTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] result = classfileBuffer;

        if (classBeingRedefined == null) {
            // first pass - gather statistics
            ClassReader reader = new ClassReader(classfileBuffer);
            YieldReturnCounter counter = new YieldReturnCounter(new EmptyVisitor());
            YielderChecker checker = new YielderChecker(counter);

            reader.accept(checker, 0);

            if (checker.isYielder()) {
                // second pass - write new code
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//                        TraceClassVisitor tracer = new TraceClassVisitor(writer, new PrintWriter(System.out));
                LocalVariablePromoter promoter = new LocalVariablePromoter(writer);
                StateKeeper stateKeeper = new StateKeeper(promoter, counter);

                reader.accept(stateKeeper, 0);

                result = writer.toByteArray();
            }
        }
        return result;
    }
}