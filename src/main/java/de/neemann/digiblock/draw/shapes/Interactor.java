/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.draw.shapes;

import de.neemann.digiblock.core.SyncAccess;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.draw.elements.IOState;
import de.neemann.digiblock.draw.graphics.Transform;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.gui.components.CircuitComponent;

import java.awt.*;

/**
 * The VisualParts Interactor instance is called if the element is clicked
 * during execution. So the user can interact with the element during execution.
 * Used at the InputShape to let the user toggle the inputs state.
 * @see InputShape
 */
public abstract class Interactor implements InteractorInterface {

    @Override
    public boolean pressed(CircuitComponent cc, Point pos, IOState ioState, Element element, SyncAccess modelSync) {
        return false;
    }

    @Override
    public boolean released(CircuitComponent cc, Point pos, IOState ioState, Element element, SyncAccess modelSync) {
        return false;
    }

    @Override
    public boolean dragged(CircuitComponent cc, Point posOnScreen, Vector pos, Transform transform, IOState ioState, Element element, SyncAccess modelSync) {
        return false;
    }
}
