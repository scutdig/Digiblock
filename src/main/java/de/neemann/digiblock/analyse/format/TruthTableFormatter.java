/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.analyse.format;

import de.neemann.digiblock.analyse.TruthTable;
import de.neemann.digiblock.analyse.expression.ExpressionException;

/**
 * Formats a truth table
 */
public interface TruthTableFormatter {

    /**
     * Creates a string representation of the table
     *
     * @param truthTable the table
     * @return the string representation
     * @throws ExpressionException ExpressionException
     */
    String format(TruthTable truthTable) throws ExpressionException;
}
