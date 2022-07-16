/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.io;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.lang.Lang;

/**
 * A constant
 */
public class Ground implements Element {

    /**
     * The Constant description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(Ground.class)
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS);

    private final ObservableValue output;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     */
    public Ground(ElementAttributes attributes) {
        output = new ObservableValue("out", attributes.get(Keys.BITS)).setPinDescription(DESCRIPTION);
        output.setValue(0);
        output.setConstant();
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        throw new NodeException(Lang.get("err_noInputsAvailable"));
    }

    @Override
    public ObservableValues getOutputs() {
        return output.asList();
    }

    @Override
    public void registerNodes(Model model) {
    }
}
