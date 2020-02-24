/*
 * Copyright (c) 2019 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.io;

import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * The button combined with a LED.
 */
public class ButtonLED extends Button {

    /**
     * The ButtonLED description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(ButtonLED.class, input("in"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.ACTIVE_LOW)
            .addAttribute(Keys.MAP_TO_KEY)
            .addAttribute(Keys.COLOR);

    /**
     * Creates a new instance
     *
     * @param attributes the buttons attributes
     */
    public ButtonLED(ElementAttributes attributes) {
        super(attributes);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        inputs.get(0).checkBits(1, null);
    }
}
