/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.Observer;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.PinDescriptions;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.graphics.*;
import de.neemann.digiblock.draw.graphics.Polygon;

import java.awt.*;

import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE;
import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE2;

/**
 * The shape to visualize a test case
 */
public class TestCaseShape implements Shape {

    private static final Style TESTSTYLE = Style.NORMAL.deriveFillStyle(new Color(180, 255, 180, 200));
    private final String label;

    /**
     * Creates a new instance
     *
     * @param attributes the attributes
     * @param inputs     inputs
     * @param outputs    ans autputs
     */
    public TestCaseShape(ElementAttributes attributes, PinDescriptions inputs, PinDescriptions outputs) {
        label = attributes.getLabel();
    }

    @Override
    public Pins getPins() {
        return new Pins();
    }

    @Override
    public InteractorInterface applyStateMonitor(IOState ioState, Observer guiObserver) {
        return null;
    }

    @Override
    public void drawTo(Graphic graphic, Style highLight) {
        if (!graphic.isFlagSet(Graphic.Flag.hideTest)) {
            Polygon pol = new Polygon(true)
                    .add(SIZE2, SIZE2)
                    .add(SIZE2 + SIZE * 4, SIZE2)
                    .add(SIZE2 + SIZE * 4, SIZE * 2 + SIZE2)
                    .add(SIZE2, SIZE * 2 + SIZE2);
            graphic.drawPolygon(pol, TESTSTYLE);
            graphic.drawPolygon(pol, Style.THIN);
            graphic.drawText(new Vector(SIZE2 + SIZE * 2, SIZE + SIZE2), "Test", Orientation.CENTERCENTER, Style.NORMAL);
            graphic.drawText(new Vector(SIZE2 + SIZE * 2, 0), label, Orientation.CENTERBOTTOM, Style.NORMAL);
        }
    }
}
