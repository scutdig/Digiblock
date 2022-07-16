/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.undo.ModifyException;

/**
 * Sets all attributes of an element
 */
public class ModifyAttributes extends ModificationOfVisualElement {

    private final ElementAttributes attributes;

    /**
     * Creates a new instance
     *
     * @param ve       the element to modify
     * @param modified the new attributes
     */
    public ModifyAttributes(VisualElement ve, ElementAttributes modified) {
        super(ve, Lang.get("mod_setAttributesIn_N", getToolTipName(ve)));
        attributes = modified;
    }

    @Override
    public void modify(Circuit circuit) throws ModifyException {
        VisualElement ve = getVisualElement(circuit);
        ve.getElementAttributes().getValuesFrom(attributes);
    }
}
