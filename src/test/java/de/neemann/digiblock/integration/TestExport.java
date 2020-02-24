/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.integration;

import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.draw.graphics.*;
import de.neemann.digiblock.draw.library.ElementNotFoundException;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Loads the processor and exports it to the different export instances
 * Only checks that something is written without an error
 */
public class TestExport extends TestCase {

    private static ByteArrayOutputStream export(String file, ExportFactory creator) throws NodeException, PinException, IOException, ElementNotFoundException {
        Circuit circuit = new ToBreakRunner(file).getCircuit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Export(circuit, creator).export(baos);
        return baos;
    }

    public void testSVGExport() throws NodeException, PinException, IOException, ElementNotFoundException {
        ByteArrayOutputStream baos
                = export("../../main/dig/processor/Processor.dig",
                (out) -> new GraphicSVG(out, null, 15));

        assertTrue(baos.size() > 20000);
    }

    public void testPNGExport() throws NodeException, PinException, IOException, ElementNotFoundException {
        ByteArrayOutputStream baos
                = export("../../main/dig/processor/Processor.dig",
                (out) -> new GraphicsImage(out, "PNG", 1));

        assertTrue(baos.size() > 45000);
    }
}
