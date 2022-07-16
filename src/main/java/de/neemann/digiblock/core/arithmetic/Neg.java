/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.arithmetic;

import de.neemann.digiblock.core.Node;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.stats.Countable;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * Negation, twos complement
 */
public class Neg extends Node implements Element, Countable {

    /**
     * The element description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(Neg.class, input("in"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS);

    private final ObservableValue output;
    private final int bits;
    private ObservableValue input;
    private long value;

    /**
     * Creates a new instance
     *
     * @param attributes attributes
     */
    public Neg(ElementAttributes attributes) {
        bits = attributes.get(Keys.BITS);
        output = new ObservableValue("out", bits).setPinDescription(DESCRIPTION);
    }

    @Override
    public void readInputs() throws NodeException {
        value = input.getValue();
    }

    @Override
    public void writeOutputs() throws NodeException {
        output.setValue(-value);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        input = inputs.get(0).addObserverToValue(this).checkBits(bits, this, 0);
    }

    @Override
    public ObservableValues getOutputs() {
        return output.asList();
    }

    @Override
    public int getDataBits() {
        return bits;
    }
}
