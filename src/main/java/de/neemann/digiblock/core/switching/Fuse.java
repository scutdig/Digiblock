/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.switching;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.NodeInterface;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;

/**
 * A simple fuse.
 */
public class Fuse implements Element, NodeInterface {

    /**
     * The fuse description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(Fuse.class)
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BLOWN);

    private final PlainSwitch s;

    /**
     * Create a new fuse
     *
     * @param attr the attributes
     */
    public Fuse(ElementAttributes attr) {
        s = new PlainSwitch(attr.getBits(), !attr.get(Keys.BLOWN), "out1", "out2");
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        s.setInputs(inputs.get(0), inputs.get(1));
    }

    @Override
    public ObservableValues getOutputs() {
        return s.getOutputs();
    }

    @Override
    public void registerNodes(Model model) {
    }

    @Override
    public void init(Model model) {
        s.init(model);
    }

    @Override
    public void hasChanged() {
        s.hasChanged();
    }
}
