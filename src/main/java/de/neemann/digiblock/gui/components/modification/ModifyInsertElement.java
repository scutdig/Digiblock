/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.undo.Modification;

import static de.neemann.digiblock.gui.components.modification.ModificationOfVisualElement.getToolTipName;

/**
 * Modifier to insert an element
 */
public class ModifyInsertElement implements Modification<Circuit> {
    private final VisualElement element;

    /**
     * Creates a new instance
     *
     * @param element the element to insert
     */
    public ModifyInsertElement(VisualElement element) {
        this.element = new VisualElement(element);
    }

    @Override
    public void modify(Circuit circuit) {
        circuit.add(new VisualElement(element));
    }

    @Override
    public String toString() {
        return Lang.get("mod_insertedElement_N", getToolTipName(element));
    }
}
