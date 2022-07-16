/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.integration;

import de.neemann.digiblock.TestExecuter;
import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.Node;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.library.ElementLibrary;
import de.neemann.digiblock.draw.model.ModelCreator;
import de.neemann.digiblock.draw.model.ModelEntry;
import de.neemann.digiblock.draw.shapes.ShapeFactory;
import junit.framework.TestCase;

import java.io.File;
import java.util.List;

/**
 */
public class TestAnd extends TestCase {

    private ElementLibrary library = new ElementLibrary();

    /**
     * Reads a file and sets up a model from it.
     * After that the model - a simple AND gate - is tested to be a working AND gate.
     *
     * @throws Exception
     */
    public void testAnd() throws Exception {
        File filename = new File(Resources.getRoot(), "dig/and.dig");
        Circuit circuit = Circuit.loadCircuit(filename, new ShapeFactory(new ElementLibrary()));

        ModelCreator md = new ModelCreator(circuit, library);
        Model model = md.createModel(false);

        List<Node> nodes = model.getNodes();
        assertEquals(1, nodes.size());

        // get inputs and outputs
        List<ModelEntry> inputs = md.getEntries("In");
        assertEquals(2, inputs.size());
        List<ModelEntry> outputs = md.getEntries("Out");
        assertEquals(1, outputs.size());

        // check the inputs state: the input itself has an output
        assertEquals(0, inputs.get(0).getIoState().inputCount());
        assertEquals(1, inputs.get(0).getIoState().outputCount());
        assertEquals(0, inputs.get(1).getIoState().inputCount());
        assertEquals(1, inputs.get(1).getIoState().outputCount());

        // check the output state: the output itself has an input
        assertEquals(1, outputs.get(0).getIoState().inputCount());
        assertEquals(0, outputs.get(0).getIoState().outputCount());

        // setup the test executer
        TestExecuter te = new TestExecuter(model).setInputs(inputs).setOutputs(outputs);

        te.check(0, 0, 0);
        te.check(0, 1, 0);
        te.check(1, 0, 0);
        te.check(1, 1, 1);
    }

    /**
     * Same test as above written more simple
     *
     * @throws Exception
     */
    public void testAnd2() throws Exception {
        TestExecuter te = TestExecuter.createFromFile("dig/and.dig", library);

        te.check(0, 0, 0);
        te.check(0, 1, 0);
        te.check(1, 0, 0);
        te.check(1, 1, 1);

        // only a single And-Node
        assertEquals(1, te.getModel().getNodes().size());

        // every calculation needs a single micro step
        assertEquals(4, te.getModel().getStepCounter());
    }

}
