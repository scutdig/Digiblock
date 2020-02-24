/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.gui.components.modification;

import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.Wire;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.undo.Modification;
import de.neemann.digiblock.undo.ModifyException;

/**
 * A modification on a wire
 * The wire is identified by its position.
 */
public abstract class ModificationOfWire implements Modification<Circuit> {

    private final Vector p1;
    private final String description;
    private final Vector p2;

    /**
     * Creates a new instance
     *
     * @param wire        the wire to modify
     * @param description description of modification
     */
    ModificationOfWire(Wire wire, String description) {
        this.description = description;
        p1 = wire.p1;
        p2 = wire.p2;
    }

    @Override
    public String toString() {
        return description;
    }

    /**
     * Returns the wire to modify
     *
     * @param circuit the circuit to modify
     * @return the wire to modify
     * @throws ModifyException ModifyException
     */
    Wire getWire(Circuit circuit) throws ModifyException {
        for (Wire w : circuit.getWires()) {
            if (w.p1.equals(p1) && w.p2.equals(p2))
                return w;
        }
        throw new ModifyException("internal error: Wire not found!");
    }
}
