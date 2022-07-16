/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.Wire;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.undo.ModifyException;

/**
 * Modifier to delete a wire
 */
public class ModifyDeleteWire extends ModificationOfWire {

    /**
     * Creates a new instance
     *
     * @param wire       the wire to delete
     */
    public ModifyDeleteWire(Wire wire) {
        super(wire, Lang.get("mod_wireDeleted"));
    }

    @Override
    public void modify(Circuit circuit) throws ModifyException {
        circuit.delete(getWire(circuit));
    }
}
