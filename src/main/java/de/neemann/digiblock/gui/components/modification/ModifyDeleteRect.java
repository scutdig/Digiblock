/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.undo.Modification;

/**
 * Modifier to delete all elements in a given rectangle
 */
public class ModifyDeleteRect implements Modification<Circuit> {
    private final Vector min;
    private final Vector max;

    /**
     * Creates a new instance
     *
     * @param min the upper left corner
     * @param max the lower right corner
     */
    public ModifyDeleteRect(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void modify(Circuit circuit) {
        circuit.delete(min, max);
    }

    @Override
    public String toString() {
        return Lang.get("mod_deletedSelection");
    }
}
