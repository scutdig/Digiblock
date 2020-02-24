/*
 * Copyright (c) 2019 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.io;

import de.neemann.digiblock.core.Node;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * The pin control logic
 */
public class PinControl extends Node implements Element {

    /**
     * The description of the pin control logic
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(PinControl.class, input("wr"), input("oe"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS)
            .addAttribute(Keys.MIRROR);

    private final int bits;
    private final ObservableValue rdValue;
    private final ObservableValue outWriteValue;
    private ObservableValue wrValue;
    private ObservableValue oeValue;
    private ObservableValue outReadValue;
    private long wr;
    private boolean oe;
    private long outRead;

    /**
     * Creates a new instance
     *
     * @param attr the elements attributes
     */
    public PinControl(ElementAttributes attr) {
        bits = attr.getBits();
        rdValue = new ObservableValue("rd", bits).setPinDescription(DESCRIPTION);
        outWriteValue = new ObservableValue("pin", bits).setPinDescription(DESCRIPTION).setBidirectional();
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        wrValue = inputs.get(0).addObserverToValue(this).checkBits(bits, this);
        oeValue = inputs.get(1).addObserverToValue(this).checkBits(1, this);
        outReadValue = inputs.get(2).addObserverToValue(this).checkBits(bits, this);
    }

    @Override
    public void readInputs() throws NodeException {
        wr = wrValue.getValue();
        oe = oeValue.getBool();
        outRead = outReadValue.getValue();
    }

    @Override
    public void writeOutputs() throws NodeException {
        if (oe) {
            outWriteValue.setValue(wr);
            rdValue.setValue(wr);
        } else {
            outWriteValue.setToHighZ();
            rdValue.setValue(outRead);
        }
    }


    @Override
    public ObservableValues getOutputs() {
        return new ObservableValues(rdValue, outWriteValue);
    }
}
