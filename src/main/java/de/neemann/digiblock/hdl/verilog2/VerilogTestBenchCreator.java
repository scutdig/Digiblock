/*
 * Copyright (c) 2018 Ivan Deras
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.hdl.verilog2;

import de.neemann.digiblock.Verification.TestbenchKey;
import de.neemann.digiblock.hdl.vhdl2.*;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.data.Value;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.hdl.model2.HDLCircuit;
import de.neemann.digiblock.hdl.model2.HDLException;
import de.neemann.digiblock.hdl.model2.HDLModel;
import de.neemann.digiblock.hdl.model2.HDLPort;
import de.neemann.digiblock.hdl.printer.CodePrinter;
import de.neemann.digiblock.hdl.printer.CodePrinterStr;
import de.neemann.digiblock.lang.Lang;
import de.neemann.digiblock.testing.TestCaseDescription;
import de.neemann.digiblock.testing.TestCaseElement;
import de.neemann.digiblock.testing.TestingDataException;
import de.neemann.digiblock.testing.parser.Context;
import de.neemann.digiblock.testing.parser.LineListener;
import de.neemann.digiblock.testing.parser.ParserException;
import de.neemann.digiblock.testing.parser.TestRow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static de.neemann.digiblock.testing.TestCaseElement.TESTDATA;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Creates a test bench for a model.
 * The needed test date is taken from the test cases in the circuit
 */
public class VerilogTestBenchCreator {
    private final ArrayList<ElementAttributes> testCases;
    private final HDLCircuit main;
    private final String topModuleName;
    private final HDLModel.Renaming renaming;
    private final ArrayList<File> testFileWritten;

    /**
     * Creates a new instance
     *
     * @param circuit the circuit
     * @param model   the model
     * @param topModuleName the name of the module under test
     */
    public VerilogTestBenchCreator(Circuit circuit, HDLModel model, String topModuleName) {
        this.main = model.getMain();
        this.topModuleName = topModuleName;
        testCases = new ArrayList<>();
        for (VisualElement ve : circuit.getElements())
            if (TestbenchKey.ISTESTBENCH)
                testCases.add(ve.getElementAttributes());
        testFileWritten = new ArrayList<>();
        renaming = model.getRenaming();
    }

    /**
     * Writes the test benches
     *
     * @param file the original verilog file
     * @return this for chained calls
     * @throws IOException  IOException
     * @throws HDLException HDLException
     */
    public VerilogTestBenchCreator write(File file) throws IOException, HDLException {
        String filename = file.getName();
        int p = filename.indexOf('.');
        if (p > 0)
            filename = filename.substring(0, p);

        for (ElementAttributes tc : testCases) {
            String testName = tc.getLabel();
            testName = filename;

            //testName = HDLPort.getHDLName(testName);

            File f = new File(file.getParentFile(), testName + ".v");
            testFileWritten.add(f);
            try (CodePrinter out = new CodePrinter(f)) {
                try {
                    writeTestBench(out, topModuleName, testName, tc);
                } catch (RuntimeException e) {
                    throw new HDLException(Lang.get("err_vhdlErrorWritingTestBench"), e);
                } catch (TestingDataException | ParserException ex) {
                    Logger.getLogger(VerilogTestBenchCreator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return this;
    }

    /**
     * @return returns the files which are written
     */
    public ArrayList<File> getTestFileWritten() {
        return testFileWritten;
    }

    private void writeTestBench(CodePrinter out, String moduleName, String testName, ElementAttributes tc) throws IOException, HDLException, TestingDataException, ParserException {
        out.print("//  A testbench for ").println(testName);
        out.println("`timescale 1us/1ns").println();
        out.print("module ").print(testName).println(";");
        out.inc();
        for (HDLPort p : this.main.getPorts())
            out.print("  ").print(getSignalDeclarationCode(p)).println(";");
        out.println();
        String pmoduleName = null;
        if (moduleName.substring(moduleName.lastIndexOf('_')).toLowerCase().equals("_tb")) {
            pmoduleName = moduleName.substring(0, moduleName.lastIndexOf('_'));
        } else {
            throw new IOException(Lang.get("err_testbenchModuleName"));
        }
        out.print(pmoduleName).print(" ").print(moduleName).print("0 (").println();
        out.inc();
        Separator comma = new Separator(out, ",\n");
        for (HDLPort p : this.main.getPorts()) {
            comma.check();
            out.print(".").print(p.getName()).print("(").print(p.getName()).print(")");
        }
        out.dec().println().print(");").println().println();
        out.print("parameter PERIOD = 10;").println().println();
        String pattern = ".*(clk|clock).*";
        String clkName = "";
        for (HDLPort p : this.main.getPorts()) {
            if (Pattern.matches(pattern, getSignalDeclarationCode(p))) {
                clkName = p.getName();
                TestbenchKey.ISCOMBINATION = true;
            }
        }
        out.println().println("initial begin");
        out.print("  $dumpfile(\"db_").print(moduleName).print(".vcd\");").println();
        out.print("  $dumpvars(0, ").print(moduleName).print(");").println();
        out.print("  /*").println();
        out.print("  * Please insert your code as fellow.").println();
        out.print("  */").println().println();
        if (TestbenchKey.ISCOMBINATION) {
            out.print("  " + clkName + " = 1'b0;").println();
            out.print("  #(PERIOD/2);\n");
            out.print("  forever").println();
            out.print("    #(PERIOD/2)  " + clkName + " =~ " + clkName + ";").println();
        }
        out.print("end").println().println();
        out.print("initial").println();
        out.print("  #100000 $finish;").println();
        out.print("endmodule");
    }


    private boolean loopVarExists(String loopVar, ArrayList<HDLPort> ports) {
        for (HDLPort p : ports)
            if (p.getName().equals(loopVar))
                return true;
        return false;
    }

    private String getSignalDeclarationCode(HDLPort p) throws HDLException {
        String declCode;

        switch (p.getDirection()) {
            case IN: declCode = "wire "; break;
            case OUT: declCode = "reg "; break;
            default:
                declCode = "/* Invalid port */";
        }

        if (p.getBits() > 1)
            declCode += "[" + Integer.toString(p.getBits() - 1) + ":0] ";

        declCode += p.getName();

        return declCode;
    }

    private static final class LineListenerVerilog implements LineListener {
        private final CodePrinter out;
        private final ArrayList<HDLPort> dataOrder;
        private final int rowBits;
        private int rowIndex;

        private LineListenerVerilog(CodePrinter out, ArrayList<HDLPort> dataOrder, int rowBits) {
            this.out = out;
            this.dataOrder = dataOrder;
            this.rowBits = rowBits;
            rowIndex = 0;
        }

        @Override
        public void add(TestRow row) {
            try {
                boolean containsClock = false;
                for (Value v : row.getValues())
                    if (v.getType() == Value.Type.CLOCK)
                        containsClock = true;
                if (containsClock) {
                    writeValues(row.getValues(), true, 0);
                    writeValues(row.getValues(), true, 1);
                }
                writeValues(row.getValues(), false, 0);
            } catch (IOException | HDLException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Returns the number of lines emitted
         *
         * @return the number of lines
         */
        public int getLineCount() {
            return rowIndex;
        }

        private void writeValues(Value[] values, boolean isClock, int clock) throws IOException, HDLException {
            out.print("patterns[").print(rowIndex).print("] = ").print(rowBits).print("'b");

            for (int i = 0; i < values.length; i++) {
                HDLPort p = dataOrder.get(i);

                if (p.getDirection() == HDLPort.Direction.OUT) {
                    if (values[i].getType() == Value.Type.CLOCK) {
                        out.print(clock);
                    } else {
                        out.print(toBinaryString(values[i], p.getBits()));
                    }
                    out.print("_");
                }
            }

            Separator sep = new Separator(out, "_");

            for (int i = 0; i < values.length; i++) {
                HDLPort p = dataOrder.get(i);

                if (p.getDirection() == HDLPort.Direction.IN) {
                    sep.check();

                    if (isClock) {
                        out.print(toBinaryString(0, Value.Type.DONTCARE, p.getBits()));
                    } else {
                        out.print(toBinaryString(values[i], p.getBits()));
                    }
                }
            }

            out.println(";");

            rowIndex++;
        }

        private String toBinaryString(Value v, int bits) {
            return toBinaryString(v.getValue(), v.getType(), bits);
        }

        private String toBinaryString(long val, Value.Type type, int bits) {
            String binStr = "";
            char fillCh = '0';

            switch (type) {
                case DONTCARE:
                    fillCh = 'x';
                    break;
                case HIGHZ:
                    fillCh = 'z';
                    break;
                default:
                    long mask = (bits < 64)? ((1L << bits)-1) : 0xffffffffffffffffL;
                    binStr = Long.toBinaryString(val & mask);
            }

            StringBuilder sb = new StringBuilder();
            if (binStr.length() < bits) {
                int diff = bits - binStr.length();

                for (int i = 0; i < diff; i++) {
                    sb.append(fillCh);
                }
            }
            sb.append(binStr);

            return sb.toString();
        }
    }
}
