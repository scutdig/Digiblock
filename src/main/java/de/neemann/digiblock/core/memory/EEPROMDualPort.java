/*
 * Copyright (c) 2017 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.core.memory;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.ModelEvent;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.memory.rom.ROMInterface;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * A EEPROM module.
 */
public class EEPROMDualPort extends RAMDualPort implements ROMInterface {

    /**
     * The EEPROMs {@link ElementTypeDescription}
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(EEPROMDualPort.class,
            input("A"),
            input("Din"),
            input("str"),
            input("C").setClock(),
            input("ld"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.BITS)
            .addAttribute(Keys.ADDR_BITS)
            .addAttribute(Keys.INT_FORMAT)
            .addAttribute(Keys.IS_PROGRAM_MEMORY)
            .addAttribute(Keys.LABEL)
            .addAttribute(Keys.DATA);

    private final ElementAttributes attr;
    private DataField memory;

    /**
     * Creates a new instance
     *
     * @param attr the elements attributes
     */
    public EEPROMDualPort(ElementAttributes attr) {
        super(attr);
        this.attr = attr;
    }

    @Override
    protected DataField createDataField(ElementAttributes attr, int size) {
        memory = attr.get(Keys.DATA);
        return memory;
    }

    @Override
    public void registerNodes(Model model) {
        super.registerNodes(model);

        if (memory.isEmpty())
            model.addObserver(event -> attr.set(Keys.DATA, memory), ModelEvent.STOPPED);
    }

}
