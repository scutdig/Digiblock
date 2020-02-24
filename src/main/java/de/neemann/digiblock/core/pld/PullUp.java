/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.pld;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.*;

/**
 * Only a placeholder.
 * Has no connections to the model!
 */
public class PullUp implements Element {

    /**
     * The pull up description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription("PullUp", PullUp.class)
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS);

    private final ObservableValue output;

    /**
     * Creates a new pull up element
     *
     * @param attr the attributes
     */
    public PullUp(ElementAttributes attr) {
        int bits = attr.getBits();
        output = new PullDown.PullObservableValue(bits, PinDescription.PullResistor.pullUp).setPinDescription(DESCRIPTION);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
    }

    @Override
    public ObservableValues getOutputs() {
        return output.asList();
    }

    @Override
    public void registerNodes(Model model) {
    }

}
