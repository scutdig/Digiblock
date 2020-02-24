/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.memory;

import de.neemann.digiblock.core.IntFormat;
import de.neemann.digiblock.core.stats.Countable;

/**
 * Interface to get access to the rams data.
 */
public interface RAMInterface extends ProgramMemory, Countable {
    /**
     * @return the {@link DataField} containing the RAMs data
     */
    DataField getMemory();

    /**
     * @return the rams size
     */
    int getSize();

    /**
     * @return the addr bits
     */
    int getAddrBits();

    /**
     * @return the integer format to be used to visualize the values
     */
    default IntFormat getIntFormat() {
        return IntFormat.hex;
    }
}
