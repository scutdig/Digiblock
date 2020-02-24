/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.io;

import de.neemann.digiblock.core.*;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * The measurement Probe
 */
public class Probe implements Element {

    /**
     * The Probe description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription("Probe", Probe.class, input("in"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.INT_FORMAT)
            .addAttribute(Keys.ADD_VALUE_TO_GRAPH);

    private final String label;
    private final IntFormat format;
    private final boolean showInGraph;
    private ObservableValue value;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     */
    public Probe(ElementAttributes attributes) {
        label = attributes.get(Keys.LABEL);
        format = attributes.get(Keys.INT_FORMAT);
        showInGraph = attributes.get(Keys.ADD_VALUE_TO_GRAPH);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        value = inputs.get(0);
    }

    @Override
    public ObservableValues getOutputs() {
        return ObservableValues.EMPTY_LIST;
    }

    @Override
    public void registerNodes(Model model) {
        model.addSignal(new Signal(label, value).setShowInGraph(showInGraph).setFormat(format));
        model.registerGlobalValue(label, value);
    }

}
