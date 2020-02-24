/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.testing;

import de.neemann.digiblock.analyse.expression.Expression;
import de.neemann.digiblock.analyse.parser.ParseException;
import de.neemann.digiblock.analyse.parser.Parser;
import de.neemann.digiblock.builder.BuilderException;
import de.neemann.digiblock.builder.circuit.CircuitBuilder;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.data.ValueTable;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.draw.library.ElementLibrary;
import de.neemann.digiblock.draw.library.ElementNotFoundException;
import de.neemann.digiblock.draw.model.ModelCreator;
import de.neemann.digiblock.draw.shapes.ShapeFactory;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.ArrayList;

/**
 */
public class TestResultTest extends TestCase {

    private Model getModel(String func) throws IOException, ParseException, BuilderException, PinException, NodeException, ElementNotFoundException {
        ArrayList<Expression> exp = new Parser(func).parse();
        ElementLibrary library = new ElementLibrary();
        CircuitBuilder cb = new CircuitBuilder(new ShapeFactory(library));
        cb.addCombinatorial("Y", exp.get(0));
        Circuit circ = cb.createCircuit();
        Model model = new ModelCreator(circ, library).createModel(false);
        model.init();
        return model;
    }

    public void testResultOk() throws Exception {
        Model model = getModel("A^B");
        TestCaseDescription data = new TestCaseDescription(
                "A B Y\n"
                        + "0 0 0\n"
                        + "0 1 1\n"
                        + "1 0 1\n"
                        + "1 1 0\n");
        TestExecutor tr = new TestExecutor(data).create(model);
        assertEquals(4,tr.getResult().getRows());
        assertTrue(tr.allPassed());
    }

    public void testResultError() throws Exception {
        Model model = getModel("A+B");
        TestCaseDescription data = new TestCaseDescription(
                "A B Y\n"
                        + "0 0 0\n"
                        + "0 1 1\n"
                        + "1 0 1\n"
                        + "1 1 0\n");
        TestExecutor te = new TestExecutor(data).create(model);
        ValueTable tr = te.getResult();
        assertEquals(4,tr.getRows());
        assertFalse(te.allPassed());
        assertEquals(true, ((MatchedValue) tr.getValue(0, 2)).isPassed());
        assertEquals(true, ((MatchedValue) tr.getValue(1, 2)).isPassed());
        assertEquals(true, ((MatchedValue) tr.getValue(2, 2)).isPassed());
        assertEquals(false, ((MatchedValue) tr.getValue(3, 2)).isPassed());
    }

    public void testResultDontCare() throws Exception {
        Model model = getModel("A+B");
        TestCaseDescription data = new TestCaseDescription(
                "A B Y\n"
                        + "0 0 0\n"
                        + "0 1 1\n"
                        + "1 0 1\n"
                        + "1 1 x\n");
        TestExecutor te = new TestExecutor(data).create(model);
        ValueTable tr = te.getResult();
        assertEquals(4,tr.getRows());
        assertTrue(te.allPassed());
    }

    public void testResultDontCare2() throws Exception {
        Model model = getModel("A+B");
        TestCaseDescription data = new TestCaseDescription(
                "A B Y\n"
                        + "0 0 x\n"
                        + "0 1 1\n"
                        + "1 0 1\n"
                        + "1 1 1\n");
        TestExecutor te = new TestExecutor(data).create(model);
        ValueTable tr = te.getResult();
        assertEquals(4,tr.getRows());
        assertTrue(te.allPassed());
    }

    public void testResultDontCareInput() throws Exception {
        Model model = getModel("A*0+B");
        TestCaseDescription data = new TestCaseDescription(
                "A B Y\n"
                        + "x 0 0\n"
                        + "x 1 1\n");
        TestExecutor te = new TestExecutor(data).create(model);
        ValueTable tr = te.getResult();
        assertEquals(4,tr.getRows());
        assertTrue(te.allPassed());
    }

    public void testResultDontCareInput2() throws Exception {
        Model model = getModel("A*0+B*0+C");
        TestCaseDescription data = new TestCaseDescription(
                "A B C Y\n"
                        + "x x 0 0\n"
                        + "x x 1 1\n");
        TestExecutor te = new TestExecutor(data).create(model);
        ValueTable tr = te.getResult();
        assertEquals(8,tr.getRows());
        assertTrue(te.allPassed());
    }

}
