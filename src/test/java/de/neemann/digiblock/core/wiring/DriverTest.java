/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.wiring;

import de.neemann.digiblock.TestExecuter;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.element.ElementAttributes;
import junit.framework.TestCase;

import static de.neemann.digiblock.TestExecuter.HIGHZ;
import static de.neemann.digiblock.core.ObservableValues.ovs;

/**
 */
public class DriverTest extends TestCase {

    public void testDriver() throws Exception {
        ObservableValue a = new ObservableValue("a", 2);
        ObservableValue sel = new ObservableValue("sel", 1);

        Model model = new Model();
        Driver out = model.add(new Driver(new ElementAttributes().setBits(2)));
        out.setInputs(ovs(a, sel));

        TestExecuter sc = new TestExecuter(model).setInputs(a, sel).setOutputs(out.getOutputs());
        sc.check(0, 1, 0);
        sc.check(2, 1, 2);
        sc.checkZ(2, 0, HIGHZ);
        sc.check(2, 1, 2);
    }

}
