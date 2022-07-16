/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.wiring;

import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * The Driver
 */
public class DriverInvSel extends Driver {

    /**
     * The Driver description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(DriverInvSel.class,
            input("in"),
            input("sel"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS)
            .addAttribute(Keys.FLIP_SEL_POSITON);

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     */
    public DriverInvSel(ElementAttributes attributes) {
        super(attributes);
    }

    @Override
    protected boolean isOutHighZ(boolean sel) {
        return sel;
    }
}
