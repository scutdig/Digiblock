/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.lang;

import de.neemann.digiblock.core.element.Key;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.draw.graphics.GraphicSVG;
import junit.framework.TestCase;

/**
 */
public class TestKeyConsistence extends TestCase {

    /**
     * Checks if key descriptions are complete
     */
    public void testConsistence() {
        for (Key key : Keys.getKeys()) {
            checkKey(key.getLangKey());
            checkKey(key.getLangKey() + "_tt");

            if (key instanceof Key.KeyEnum) {
                Key.KeyEnum ke = (Key.KeyEnum) key;
                for (Enum v : ke.getValues())
                    checkKey(ke.getLangKey(v));
            }
        }
    }

    private void checkKey(String key) {
        String str = Lang.getNull(key);
        if (str == null)
            missing(key);
    }

    private void missing(String key) {
        final String xml = GraphicSVG.escapeXML(key);
        System.out.println("<string name=\"" + xml + "\">" + xml + "</string>");
        fail("key '" + key + "' is missing!");
    }

}
