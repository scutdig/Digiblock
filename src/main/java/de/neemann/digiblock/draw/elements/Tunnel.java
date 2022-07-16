/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.elements;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * Allows a tunneling of wires to make the schematic more readable then drawing
 * long wires.
 */
public class Tunnel implements Element {

    /**
     * The TunnelElement description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(Tunnel.class, input("in"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.NETNAME);

    private final String label;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     */
    public Tunnel(ElementAttributes attributes) {
        this.label = attributes.getLabel();
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
    }

    @Override
    public ObservableValues getOutputs() {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void registerNodes(Model model) {
    }
}
