/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.Bits;
import de.neemann.digiblock.core.Observer;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.element.PinDescriptions;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.elements.Pin;
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.graphics.Graphic;
import de.neemann.digiblock.draw.graphics.Orientation;
import de.neemann.digiblock.draw.graphics.Style;
import de.neemann.digiblock.draw.graphics.Vector;

import java.awt.*;

import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE;
import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE2;
import static de.neemann.digiblock.draw.shapes.OutputShape.OUT_SIZE;

/**
 * The LED shape
 */
public class RGBLEDShape implements Shape {
    private static final int D = SIZE / 3;
    private final String label;
    private final PinDescriptions inputs;
    private final long max;
    private final int size;
    private IOState ioState;
    private Color color;

    /**
     * Creates a new instance
     *
     * @param attr    the attributes
     * @param inputs  the inputs
     * @param outputs the outputs
     */
    public RGBLEDShape(ElementAttributes attr, PinDescriptions inputs, PinDescriptions outputs) {
        this.inputs = inputs;
        this.label = attr.getLabel();
        max = Bits.mask(attr.getBits());
        final int s = attr.get(Keys.LED_SIZE);
        this.size = s > 0 ? s * OUT_SIZE : SIZE2;
    }

    @Override
    public Pins getPins() {
        return new Pins()
                .add(new Pin(new Vector(0, -SIZE), inputs.get(0)))
                .add(new Pin(new Vector(0, 0), inputs.get(1)))
                .add(new Pin(new Vector(0, SIZE), inputs.get(2)));
    }

    @Override
    public Interactor applyStateMonitor(IOState ioState, Observer guiObserver) {
        this.ioState = ioState;
        ioState.getInput(0).addObserverToValue(guiObserver);
        ioState.getInput(1).addObserverToValue(guiObserver);
        ioState.getInput(2).addObserverToValue(guiObserver);
        return null;
    }

    @Override
    public void readObservableValues() {
        if (ioState != null) {
            long r = ioState.getInput(0).getValue() * 255 / max;
            long g = ioState.getInput(1).getValue() * 255 / max;
            long b = ioState.getInput(2).getValue() * 255 / max;
            color = new Color((int) r, (int) g, (int) b);
        }
    }

    @Override
    public void drawTo(Graphic graphic, Style heighLight) {
        if (color == null)
            color = Color.RED;

        Vector rad = new Vector(size - 2, size - 2);
        Vector radL = new Vector(size, size);

        graphic.drawLine(new Vector(0, -SIZE), new Vector(D, -SIZE + D), Style.NORMAL);
        graphic.drawLine(new Vector(0, SIZE), new Vector(D, SIZE - D), Style.NORMAL);

        Vector center = new Vector(1 + size, 0);
        graphic.drawCircle(center.sub(radL), center.add(radL), Style.FILLED);
        graphic.drawCircle(center.sub(rad), center.add(rad), Style.FILLED.deriveColor(color));
        Vector textPos = new Vector(2 * size + OUT_SIZE, 0);
        graphic.drawText(textPos, label, Orientation.LEFTCENTER, Style.NORMAL);
    }
}
