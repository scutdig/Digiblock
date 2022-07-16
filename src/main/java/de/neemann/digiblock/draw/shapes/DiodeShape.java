/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.Observer;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.element.PinDescriptions;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.elements.Pin;
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.graphics.Graphic;
import de.neemann.digiblock.draw.graphics.Polygon;
import de.neemann.digiblock.draw.graphics.Style;
import de.neemann.digiblock.draw.graphics.Vector;

import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE;
import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE2;

/**
 * The diodes shape
 */
public class DiodeShape implements Shape {

    private final PinDescriptions outputs;
    private final boolean blown;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     * @param inputs     the inputs
     * @param outputs    the outputs
     */
    public DiodeShape(ElementAttributes attributes, PinDescriptions inputs, PinDescriptions outputs) {
        this.outputs = outputs;
        blown = attributes.get(Keys.BLOWN);
    }

    @Override
    public Pins getPins() {
        return new Pins()
                .add(new Pin(new Vector(0, 0), outputs.get(0)))
                .add(new Pin(new Vector(0, -SIZE), outputs.get(1)));
    }

    @Override
    public InteractorInterface applyStateMonitor(IOState ioState, Observer guiObserver) {
        return null;
    }

    @Override
    public void drawTo(Graphic graphic, Style highLight) {
        Style style = blown ? Style.DASH : Style.NORMAL;

        graphic.drawPolygon(
                new Polygon(true)
                        .add(-SIZE2, -SIZE + 1)
                        .add(SIZE2, -SIZE + 1)
                        .add(0, -1),
                style
        );
        graphic.drawLine(new Vector(-SIZE2, -1), new Vector(SIZE2, -1), style);
    }

}
