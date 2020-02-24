/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.Model;
import de.neemann.digiblock.core.Observer;
import de.neemann.digiblock.core.Signal;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.element.PinDescriptions;
import de.neemann.digiblock.data.DataPlotter;
import de.neemann.digiblock.data.Value;
import de.neemann.digiblock.data.ValueTable;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.graphics.Graphic;
import de.neemann.digiblock.draw.graphics.Style;
import de.neemann.digiblock.draw.model.ModelCreator;
import de.neemann.digiblock.draw.model.ModelEntry;
import de.neemann.digiblock.gui.components.OrderMerger;
import de.neemann.digiblock.gui.components.data.ValueTableObserver;
import de.neemann.digiblock.testing.parser.TestRow;

import java.util.ArrayList;

/**
 * Shape which shows the data graph inside the models circuit area.
 */
public class DataShape implements Shape {

    private final boolean microStep;
    private final int maxSize;
    private ValueTable logDataModel;
    private ValueTable logData;

    /**
     * Creates a new instance
     *
     * @param attr    the attributes
     * @param inputs  the inputs
     * @param outputs the outputs
     */
    public DataShape(ElementAttributes attr, PinDescriptions inputs, PinDescriptions outputs) {
        microStep = attr.get(Keys.MICRO_STEP);
        maxSize = attr.get(Keys.MAX_STEP_COUNT);
    }

    @Override
    public Pins getPins() {
        return new Pins();
    }

    @Override
    public Interactor applyStateMonitor(IOState ioState, Observer guiObserver) {
        return null;
    }


    @Override
    public void readObservableValues() {
        if (logDataModel != null)
            logData = new ValueTable(logDataModel);
    }

    @Override
    public void drawTo(Graphic graphic, Style heighLight) {
        if (logData == null) {
            logData = new ValueTable("A", "B", "C")
                    .add(new TestRow(new Value(0), new Value(0), new Value(0)))
                    .add(new TestRow(new Value(0), new Value(1), new Value(0)));
        }
        new DataPlotter(logData).drawTo(graphic, null);
    }

    @Override
    public void registerModel(ModelCreator modelCreator, Model model, ModelEntry element) {
        ArrayList<Signal> signals = model.getSignalsCopy();
        new OrderMerger<String, Signal>(modelCreator.getCircuit().getMeasurementOrdering()) {
            @Override
            public boolean equals(Signal a, String b) {
                return a.getName().equals(b);
            }
        }.order(signals);

        ValueTableObserver valueTableObserver = new ValueTableObserver(microStep, signals, maxSize);
        logDataModel = valueTableObserver.getLogData();
        model.addObserver(valueTableObserver);
    }
}
