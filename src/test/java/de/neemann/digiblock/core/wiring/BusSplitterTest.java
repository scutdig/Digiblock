/*
 * Copyright (c) 2018 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.wiring;

import de.neemann.digiblock.TestExecuter;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.element.ElementAttributes;
import junit.framework.TestCase;

import static de.neemann.digiblock.core.ObservableValues.ovs;

public class BusSplitterTest extends TestCase {

    public void testBusSplitter() throws Exception {
        Model model = new Model();
        ObservableValue oe = new ObservableValue("oe", 1);
        ObservableValue d = new ObservableValue("d", 4);
        ObservableValue d0 = new ObservableValue("d0", 1);
        ObservableValue d1 = new ObservableValue("d1", 1);
        ObservableValue d2 = new ObservableValue("d2", 1);
        ObservableValue d3 = new ObservableValue("d3", 1);
        BusSplitter out = model.add(new BusSplitter(
                new ElementAttributes()
                        .setBits(4)));
        out.setInputs(ovs(oe, d, d0, d1, d2, d3));


        TestExecuter te = new TestExecuter(model).setInputs(oe, d, d0, d1, d2, d3).setOutputs(out.getOutputs());
        te.check(1,  0, 0, 0, 0, 0,   0, 0, 0, 0, 0);
        te.check(1,  5, 0, 0, 0, 0,   0, 1, 0, 1, 0);
        te.check(1, 15, 0, 0, 0, 0,   0, 1, 1, 1, 1);
        te.check(0,  0, 0, 0, 0, 0,   0, 0, 0, 0, 0);
        te.check(0,  0, 1, 0, 1, 0,   5, 0, 0, 0, 0);
        te.check(0,  0, 1, 1, 1, 1,  15, 0, 0, 0, 0);
    }

}
