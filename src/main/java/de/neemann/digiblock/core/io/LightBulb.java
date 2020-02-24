/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.io;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.draw.elements.PinException;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * A light bulb
 */
public class LightBulb implements Element {

    /**
     * The LED description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(LightBulb.class, input("A"), input("B"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.COLOR);

    /**
     * Creates a new light bulb
     * @param attr the attributes
     */
    public LightBulb(ElementAttributes attr) {
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        inputs.get(0).checkBits(1, null, 0);
        inputs.get(1).checkBits(1, null, 1);
    }

    @Override
    public ObservableValues getOutputs() throws PinException {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void registerNodes(Model model) {
    }
}
