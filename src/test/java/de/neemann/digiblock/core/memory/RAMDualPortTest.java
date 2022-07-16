/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.memory;

import de.neemann.digiblock.TestExecuter;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.Keys;
import junit.framework.TestCase;

import static de.neemann.digiblock.TestExecuter.HIGHZ;
import static de.neemann.digiblock.core.ObservableValues.ovs;

/**
 */
public class RAMDualPortTest extends TestCase {

    public void testRAM() throws Exception {
        ObservableValue a = new ObservableValue("a", 4);
        ObservableValue d = new ObservableValue("d", 4);
        ObservableValue str = new ObservableValue("str", 1);
        ObservableValue clk = new ObservableValue("clk", 1);
        ObservableValue ld = new ObservableValue("ld", 1);

        Model model = new Model();
        RAMDualPort out = model.add(new RAMDualPort(
                new ElementAttributes()
                        .set(Keys.ADDR_BITS, 4)
                        .setBits(4)));
        out.setInputs(ovs(a, d, str, clk, ld));

        TestExecuter sc = new TestExecuter(model).setInputs(a, d, str, clk, ld).setOutputs(out.getOutputs());
        //       A  D  ST C  LD
        sc.checkZ(0, 0, 0, 0, 0, HIGHZ);  // def
        sc.checkZ(0, 5, 1, 1, 0, HIGHZ);  // st  0->5
        sc.checkZ(0, 0, 0, 0, 0, HIGHZ);  // def
        sc.checkZ(1, 9, 1, 1, 0, HIGHZ);  // st  1->9
        sc.check(0, 0, 0, 0, 1, 5);      // rd  5
        sc.check(1, 0, 0, 0, 1, 9);      // rd  5
    }


}
