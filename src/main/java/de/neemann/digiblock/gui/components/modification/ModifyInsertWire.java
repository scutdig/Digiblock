/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.Wire;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.undo.Modification;

/**
 * Modifier to insert a wire.
 */
public class ModifyInsertWire implements Modification<Circuit> {
    private final Vector p1;
    private final Vector p2;

    /**
     * Creates a new instance
     *
     * @param w the wire to insert
     */
    public ModifyInsertWire(Wire w) {
        p1 = w.p1;
        p2 = w.p2;
    }

    @Override
    public void modify(Circuit circuit) {
        circuit.add(new Wire(p1, p2));
    }

    /**
     * @return null if this is a wire with zero length
     */
    public Modification<Circuit> checkIfLenZero() {
        if ((p1.x == p2.x) && (p1.y == p2.y))
            return null;
        else
            return this;
    }

    @Override
    public String toString() {
        return Lang.get("mod_insertedWire");
    }
}
