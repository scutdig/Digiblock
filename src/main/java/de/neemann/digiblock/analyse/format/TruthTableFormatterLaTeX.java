/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.analyse.format;

import de.neemann.digiblock.analyse.TruthTable;
import de.neemann.digiblock.analyse.expression.ContextFiller;
import de.neemann.digiblock.analyse.expression.ExpressionException;
import de.neemann.digiblock.analyse.expression.Variable;
import de.neemann.digiblock.analyse.quinemc.ThreeStateValue;
import de.neemann.digiblock.draw.graphics.text.formatter.LaTeXFormatter;

/**
 * Creates a LaTeX table using the truth table data
 */
public class TruthTableFormatterLaTeX implements TruthTableFormatter {
    @Override
    public String format(TruthTable truthTable) throws ExpressionException {
        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{center}\n\\begin{tabular}{");
        for (Variable v : truthTable.getVars())
            sb.append("c");
        sb.append("|");
        for (int i = 0; i < truthTable.getResultCount(); i++)
            sb.append("c");
        sb.append("}\n");

        for (Variable v : truthTable.getVars())
            sb.append("$").append(LaTeXFormatter.format(v)).append("$&");
        for (int i = 0; i < truthTable.getResultCount(); i++) {
            sb.append("$").append(LaTeXFormatter.format(new Variable(truthTable.getResultName(i)))).append("$");
            if (i < truthTable.getResultCount() - 1)
                sb.append("&");
        }
        sb.append("\\\\\n");
        sb.append("\\hline\n");

        ContextFiller cf = new ContextFiller(truthTable.getVars());
        for (int i = 0; i < cf.getRowCount(); i++) {
            cf.setContextTo(i);
            for (Variable v : cf)
                sb.append(format(cf.get(v))).append("&");

            for (int j = 0; j < truthTable.getResultCount(); j++) {
                ThreeStateValue r = truthTable.getResult(j).get(i);
                sb.append(format(r));
                if (j < truthTable.getResultCount() - 1)
                    sb.append("&");
            }
            sb.append("\\\\\n");
        }
        sb.append("\\end{tabular}\n\\end{center}\n");
        return sb.toString();
    }

    private String format(boolean b) {
        return format(ThreeStateValue.value(b));
    }

    private String format(ThreeStateValue r) {
        switch (r) {
            case one:
                return "$1$";
            case zero:
                return "$0$";
            case dontCare:
                return "-";
        }
        return null;
    }

}
