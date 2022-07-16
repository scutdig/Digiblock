/*
 * Copyright (c) 2019 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.toolchain;

import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.draw.library.ElementNotFoundException;
import de.neemann.digiblock.integration.Resources;
import de.neemann.digiblock.integration.ToBreakRunner;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class ReferenceTest extends TestCase {

    public void testRef() throws IOException, ElementNotFoundException, PinException, NodeException, InterruptedException {
        File f = new File(Resources.getRoot(), "toolchain/main.xml");
        ToBreakRunner br = new ToBreakRunner(new File(Resources.getRoot(), "dig/hdl/negSimple.dig"));
        Configuration c = Configuration.load(f)
                .setFilenameProvider(() -> new File("z/test.dig"))
                .setLibraryProvider(br::getLibrary)
                .setCircuitProvider(br::getCircuit);
        final ConfigurationTest.TestIOInterface ioInterface = new ConfigurationTest.TestIOInterface();
        c.setIoInterface(ioInterface);
        c.executeCommand(c.getCommands().get(0),null, null).join();

        assertEquals("Test content", ioInterface.getFiles().get("z/test1.txt").toString());
        assertEquals("deep content", ioInterface.getFiles().get("z/test2.txt").toString());
    }
}
