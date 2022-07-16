/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.Observer;
import de.neemann.digiblock.core.SyncAccess;
import de.neemann.digiblock.core.element.*;
import de.neemann.digiblock.core.memory.DataField;
import de.neemann.digiblock.core.memory.RAMInterface;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.draw.model.ModelCreator;
import de.neemann.digiblock.draw.model.ModelEntry;
import de.neemann.digiblock.gui.components.CircuitComponent;
import de.neemann.digiblock.gui.components.DataEditor;

import java.awt.*;

/**
 * The RAM shape
 */
public class RAMShape extends GenericShape {
    private final int dataBits;
    private final int addrBits;
    private final String dialogTitle;
    private Model model;

    /**
     * Creates a new instance
     *
     * @param attr        the attributes of the element
     * @param description element type description
     * @throws NodeException NodeException
     * @throws PinException  PinException
     */
    public RAMShape(ElementAttributes attr, ElementTypeDescription description) throws NodeException, PinException {
        this(attr, description, 3);
    }

    /**
     * Creates a new instance
     *
     * @param attr        the attributes of the element
     * @param description element type description
     * @param width       the used width
     * @throws NodeException NodeException
     * @throws PinException  PinException
     */
    public RAMShape(ElementAttributes attr, ElementTypeDescription description, int width) throws NodeException, PinException {
        super(description.getShortName(),
                description.getInputDescription(attr),
                description.getOutputDescriptions(attr),
                attr.getLabel(), true, width);
        if (attr.getLabel().length() > 0)
            dialogTitle = attr.getLabel();
        else
            dialogTitle = description.getShortName();
        dataBits = attr.get(Keys.BITS);
        addrBits = attr.get(Keys.ADDR_BITS);
        setInverterConfig(attr.get(Keys.INVERTER_CONFIG));
    }

    @Override
    public Interactor applyStateMonitor(IOState ioState, Observer guiObserver) {
        return new Interactor() {
            @Override
            public boolean clicked(CircuitComponent cc, Point pos, IOState ioState, Element element, SyncAccess modelSync) {
                if (element instanceof RAMInterface) {
                    RAMInterface ram = (RAMInterface) element;
                    DataField dataField = ram.getMemory();
                    DataEditor dataEditor = new DataEditor(cc, dataField, dataBits, addrBits, true, modelSync, ram.getIntFormat());
                    dataEditor.showDialog(dialogTitle, model);
                }
                return false;
            }
        };
    }

    @Override
    public void registerModel(ModelCreator modelCreator, Model model, ModelEntry element) {
        this.model = model;
    }
}
