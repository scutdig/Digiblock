/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.library;

import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.integration.Resources;
import de.neemann.digiblock.integration.ToBreakRunner;
import de.neemann.digiblock.testing.TestCaseDescription;
import de.neemann.digiblock.testing.TestCaseElement;
import de.neemann.digiblock.testing.TestExecutor;
import de.neemann.digiblock.testing.TestingDataException;
import de.neemann.digiblock.testing.parser.ParserException;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class JarComponentManagerTest extends TestCase {

    public void testMissingJar() throws PinException, NodeException, IOException {
        try {
            new ToBreakRunner("dig/jarLib/jarLibTest.dig");
            fail();
        } catch (ElementNotFoundException e) {
            assertTrue(true);
        }
    }

    public void testJarAvailable() throws PinException, NodeException, IOException, ElementNotFoundException, TestingDataException, ParserException {
        ToBreakRunner br = new ToBreakRunner("dig/jarLib/jarLibTest.dig") {
            @Override
            public void initLibrary(ElementLibrary library) {
                library.addExternalJarComponents(new File(Resources.getRoot(), "dig/jarLib/pluginExample-1.0-SNAPSHOT.jar"));
                assertNull(library.checkForException());
            }
        };

        for (VisualElement ve : br.getCircuit().getElements()) {
            if (ve.equalsDescription(TestCaseElement.TESTCASEDESCRIPTION)) {
                TestCaseDescription td = ve.getElementAttributes().get(TestCaseElement.TESTDATA);
                TestExecutor tr = new TestExecutor(td).create(br.getModel());
                assertTrue(tr.allPassed());
            }
        }
    }

}
