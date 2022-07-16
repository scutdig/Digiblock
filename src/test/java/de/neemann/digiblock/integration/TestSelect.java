/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.integration;

import de.neemann.digiblock.core.io.Out;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.gui.components.data.DummyElement;
import junit.framework.TestCase;

/**
 */
public class TestSelect extends TestCase {

    public void testSelect() throws Exception {
        Circuit c = new ToBreakRunner("dig/selectOuter.dig").getCircuit();

        // don't select by clicking in label size bounding box
        VisualElement el = c.getElementAt(new Vector(65, 15));
        assertNull(el);

        // select by clicking in shape size bounding box
        el = c.getElementAt(new Vector(55, 15));
        assertNotNull(el);
        assertEquals("selectInnerLongName.dig", el.getElementName());

        // select output by clicking in shape size bounding box
        el = c.getElementAt(new Vector(195, 20));
        assertNotNull(el);
        assertEquals(Out.DESCRIPTION.getName(), el.getElementName());

        // don't select output by clicking in label text
        el = c.getElementAt(new Vector(250, 20));
        assertNull(el);

        // select text by clicking in text size bounding box
        el = c.getElementAt(new Vector(20, 110));
        assertNotNull(el);
        assertEquals(DummyElement.TEXTDESCRIPTION.getName(), el.getElementName());
    }
}



