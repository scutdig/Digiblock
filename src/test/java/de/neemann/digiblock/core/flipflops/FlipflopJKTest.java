/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.flipflops;

import de.neemann.digiblock.TestExecuter;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.element.ElementAttributes;
import junit.framework.TestCase;

import static de.neemann.digiblock.core.ObservableValues.ovs;

/**
 */
public class FlipflopJKTest extends TestCase {
    public void testFlipFlop() throws Exception {
        ObservableValue j = new ObservableValue("j", 1);
        ObservableValue c = new ObservableValue("c", 1);
        ObservableValue k = new ObservableValue("k", 1);

        Model model = new Model();
        FlipflopJK out = model.add(new FlipflopJK(new ElementAttributes()));
        out.setInputs(ovs(j, c, k));

        TestExecuter sc = new TestExecuter(model).setInputs(j, c, k).setOutputs(out.getOutputs());
        //       J  C  K  Q  ~Q
        sc.check(0, 0, 0, 0, 1);
        sc.check(0, 0, 0, 0, 1);
        sc.check(1, 0, 0, 0, 1);
        sc.check(0, 1, 0, 0, 1);
        sc.check(1, 0, 0, 0, 1);
        sc.check(1, 1, 0, 1, 0);
        sc.check(1, 0, 1, 1, 0);
        sc.check(1, 1, 1, 0, 1);
        sc.check(1, 0, 1, 0, 1);
        sc.check(1, 1, 1, 1, 0);
        sc.check(0, 0, 1, 1, 0);
        sc.check(0, 1, 1, 0, 1);
        sc.check(0, 0, 0, 0, 1);
    }
}
