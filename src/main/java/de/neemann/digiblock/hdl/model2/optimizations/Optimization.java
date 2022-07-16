/*
 * Copyright (c) 2018 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.hdl.model2.optimizations;

import de.neemann.digiblock.hdl.model2.HDLCircuit;
import de.neemann.digiblock.hdl.model2.HDLException;

/**
 * A model optimization
 */
public interface Optimization {

    /**
     * Performs the optimization.
     *
     * @param circuit the circuit to optimize
     * @throws HDLException HDLException
     */
    void optimize(HDLCircuit circuit) throws HDLException;
}
