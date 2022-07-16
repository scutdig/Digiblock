/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.wiring;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * The Break element
 */
public class Break implements Element {

    /**
     * The Break description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(Break.class, input("brk"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.ENABLED)
            .addAttribute(Keys.CYCLES);

    private final int cycles;
    private final String label;
    private final boolean enabled;
    private ObservableValue input;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     */
    public Break(ElementAttributes attributes) {
        cycles = attributes.get(Keys.CYCLES);
        enabled = attributes.get(Keys.ENABLED);
        label = attributes.getLabel();
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        input = inputs.get(0).checkBits(1, null);
    }

    /**
     * @return the break value
     */
    public ObservableValue getBreakInput() {
        return input;
    }

    /**
     * @return the timeout cycles
     */
    public int getCycles() {
        return cycles;
    }

    @Override
    public ObservableValues getOutputs() {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void registerNodes(Model model) {
        model.addBreak(this);
    }

    /**
     * @return the break label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }
}
