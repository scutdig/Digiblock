/*
 * Copyright (c) 2018 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.extern.handler;

import de.neemann.digiblock.core.extern.ProcessStarter;
import junit.framework.TestCase;

public class ProcessStarterTest extends TestCase {

    public void testJoinStrings() {
        assertEquals(null, ProcessStarter.joinStrings(null,null));
        assertEquals(null, ProcessStarter.joinStrings("","",""));
        assertEquals(null, ProcessStarter.joinStrings(" ","\n","\t"));
        assertEquals("Hello", ProcessStarter.joinStrings("Hello ","\n","\t"));
        assertEquals("Hello\nWorld", ProcessStarter.joinStrings("Hello ","\n","\tWorld"));
    }
}