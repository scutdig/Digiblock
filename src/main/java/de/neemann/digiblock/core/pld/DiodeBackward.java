/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.pld;

import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.element.PinDescription;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * A diode needed to pull a wire to ground.
 * Used to build a wired AND.
 */
public class DiodeBackward extends DiodeForward {

    /**
     * The description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(DiodeBackward.class, input("in"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BLOWN);


    /**
     * Vreates a new instance
     *
     * @param attr the elements attributes
     */
    public DiodeBackward(ElementAttributes attr) {
        super(attr, DESCRIPTION, PinDescription.PullResistor.pullUp);
    }

    @Override
    protected void setOutValue(ObservableValue output, boolean in) {
        output.set(in ? 1 : 0, in ? 1 : 0);
    }

}
