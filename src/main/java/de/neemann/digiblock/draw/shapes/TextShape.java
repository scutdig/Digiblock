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
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.graphics.Graphic;
import de.neemann.digiblock.draw.graphics.Orientation;
import de.neemann.digiblock.draw.graphics.Style;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.lang.Lang;

/**
 * Simple text
 */
public class TextShape implements Shape {
    private final String text;
    private final int fontSize;
    private Orientation orientation;

    /**
     * Create a new instance
     *
     * @param attr    attributes
     * @param inputs  the inputs
     * @param outputs the outputs
     */
    public TextShape(ElementAttributes attr, PinDescriptions inputs, PinDescriptions outputs) {
        String text = attr.get(Keys.DESCRIPTION);

        if (text.length() == 0) { // ToDo: used to be compatible with old files. Can be removed in the future
            text = attr.getLabel();
            if (text.length() > 0) {
                attr.set(Keys.DESCRIPTION, text);
                attr.set(Keys.LABEL, "");
            }
        }

        if (text.length() == 0)
            text = Lang.get("elem_Text");
        this.text = Lang.evalMultilingualContent(text);

        fontSize = attr.get(Keys.FONT_SIZE);
        orientation = attr.get(Keys.TEXT_ORIENTATION);
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
    public void drawTo(Graphic graphic, Style highLight) {

        StringBuilder sb = new StringBuilder();
        Style style = Style.NORMAL.deriveFontStyle(fontSize, true);
        Vector pos = new Vector(0, 0);
        final int dy = (style.getFontSize() * 20) / 16;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                if (sb.length() > 0) {
                    graphic.drawText(pos, sb.toString(), orientation, style);
                    sb.setLength(0);
                }
                pos = pos.add(0, dy);
            } else
                sb.append(c);
        }
        if (sb.length() > 0)
            graphic.drawText(pos, sb.toString(), orientation, style);
    }
}
