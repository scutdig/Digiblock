/*
 * Copyright (c) 2018 Helmut Neemann.
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.hdl.vhdl2;

import de.neemann.digiblock.draw.library.ElementLibrary;
import de.neemann.digiblock.draw.library.ElementNotFoundException;
import de.neemann.digiblock.hdl.model2.HDLException;
import de.neemann.digiblock.hdl.model2.HDLNode;
import de.neemann.digiblock.hdl.vhdl2.entities.VHDLEntity;
import de.neemann.digiblock.hdl.vhdl2.entities.VHDLTemplate;
import de.neemann.digiblock.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * The template library.
 * Ensures that every template is only loaded once.
 */
public class VHDLLibrary {
    private static final Logger LOGGER = LoggerFactory.getLogger(VHDLLibrary.class);
    private final ElementLibrary library;

    private HashMap<String, VHDLEntity> map;

    /**
     * Creates a new library
     *
     * @param library the element library used
     */
    VHDLLibrary(ElementLibrary library) {
        this.library = library;
        map = new HashMap<>();
    }

    /**
     * Gets the entity of the given node
     *
     * @param node the node
     * @return the entity
     * @throws HDLException HDLException
     */
    public VHDLEntity getEntity(HDLNode node) throws HDLException {
        String elementName = node.getElementName();
        VHDLEntity e = map.get(elementName);
        if (e == null) {
            try {
                ClassLoader cl = null;
                try {
                    cl = library.getElementType(elementName).getClassLoader();
                } catch (ElementNotFoundException ex) {
                    // try default
                }
                e = new VHDLTemplate(elementName, cl);
                map.put(elementName, e);
            } catch (IOException ex) {
                ex.printStackTrace();
                LOGGER.info("could not load '" + VHDLTemplate.neededFileName(elementName) + "'");
            }
        }

        if (e == null)
            throw new HDLException(Lang.get("err_vhdlNoEntity_N", elementName));
        return e;
    }
}
