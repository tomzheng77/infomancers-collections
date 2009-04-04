package com.infomancers.tests.enhancers;

import com.infomancers.collections.yield.asmbase.YielderInformationContainer;
import com.infomancers.collections.yield.asmtree.InsnEnhancer;
import com.infomancers.collections.yield.asmtree.enhancers.YieldBreakEnhancer;
import com.infomancers.tests.TestYIC;
import static com.infomancers.tests.enhancers.TestUtil.compareLists;
import static com.infomancers.tests.enhancers.TestUtil.createList;
import org.junit.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

/**
 * Copyright (c) 2009, Aviad Ben Dov
 * <p/>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 3. Neither the name of Infomancers, Ltd. nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class YieldBreakTests extends EnhancerTestsBase {

    @Test
    public void yieldBreak() {
        YielderInformationContainer info = new TestYIC(1);

        final AbstractInsnNode insn = new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "yieldBreak", "()V");
        InsnList original = createList(insn);

        InsnList expected = createList(
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner.name, "yieldBreak", "()V"),
                new InsnNode(Opcodes.RETURN)
        );

        InsnEnhancer enhancer = new YieldBreakEnhancer();

        enhancer.enhance(owner, original, info, insn);

        compareLists(expected, original);
    }
}
