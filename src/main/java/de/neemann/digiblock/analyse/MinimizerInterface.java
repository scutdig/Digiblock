/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.analyse;

import de.neemann.digiblock.analyse.expression.ExpressionException;
import de.neemann.digiblock.analyse.expression.Variable;
import de.neemann.digiblock.analyse.expression.format.FormatterException;
import de.neemann.digiblock.analyse.quinemc.BoolTable;
import de.neemann.digiblock.gui.components.table.ExpressionListener;

import java.util.List;

/**
 * Interface to abstract a minimizer algorithm.
 */
public interface MinimizerInterface {

    /**
     * Called to minimize a bool table
     *
     * @param vars       the variables used
     * @param boolTable  the bool table
     * @param resultName name of the result
     * @param listener   the listener to report the result to
     * @throws ExpressionException ExpressionException
     * @throws FormatterException FormatterException
     */
    void minimize(List<Variable> vars, BoolTable boolTable, String resultName, ExpressionListener listener) throws ExpressionException, FormatterException;

}
