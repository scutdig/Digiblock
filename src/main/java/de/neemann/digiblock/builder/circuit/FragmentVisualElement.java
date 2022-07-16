/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digiblock.builder.circuit;

import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Key;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.element.PinDescription;
import de.neemann.digiblock.core.flipflops.FlipflopD;
import de.neemann.digiblock.core.flipflops.FlipflopJK;
import de.neemann.digiblock.draw.elements.Circuit;
import de.neemann.digiblock.draw.elements.Pin;
import de.neemann.digiblock.draw.elements.Pins;
import de.neemann.digiblock.draw.elements.VisualElement;
import de.neemann.digiblock.draw.graphics.GraphicMinMax;
import de.neemann.digiblock.draw.graphics.Vector;
import de.neemann.digiblock.draw.shapes.ShapeFactory;

import java.util.ArrayList;
import java.util.List;

import static de.neemann.digiblock.draw.shapes.GenericShape.SIZE;

/**
 * A fragment describing a VisualElement
 */
public class FragmentVisualElement implements Fragment {

    private final ArrayList<Vector> inputs;
    private final ArrayList<Vector> outputs;
    private VisualElement visualElement;
    private Vector pos;

    /**
     * Creates a new instance
     *
     * @param description  the elements description
     * @param shapeFactory the shapeFactory to use
     */
    public FragmentVisualElement(ElementTypeDescription description, ShapeFactory shapeFactory) {
        this(description, 1, shapeFactory);
    }

    /**
     * Creates a new instance
     *
     * @param description  the elements description
     * @param inputCount   number of inputs
     * @param shapeFactory the shapeFactory to use
     */
    public FragmentVisualElement(ElementTypeDescription description, int inputCount, ShapeFactory shapeFactory) {
        visualElement = new VisualElement(description.getName()).setShapeFactory(shapeFactory);
        visualElement.getElementAttributes().set(Keys.INPUT_COUNT, inputCount);
        Pins pins = visualElement.getShape().getPins();

        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        for (Pin p : pins) {
            if (p.getDirection().equals(PinDescription.Direction.input))
                inputs.add(p.getPos());
            else
                outputs.add(p.getPos());
        }
    }

    /**
     * Used to ignore the c input at a JK flipflop
     *
     * @param n number of pin to ignore
     * @return this for call chaining
     */
    public FragmentVisualElement ignoreInput(int n) {
        inputs.remove(n);
        return this;
    }

    /**
     * Sets an attribute to this VisualElement
     *
     * @param key     the key
     * @param value   the value
     * @param <VALUE> the type of the value
     * @return this for call chaining
     */
    public <VALUE> FragmentVisualElement setAttr(Key<VALUE> key, VALUE value) {
        visualElement.getElementAttributes().set(key, value);
        return this;
    }

    @Override
    public Box doLayout() {
        GraphicMinMax mm = new GraphicMinMax();
        for (Vector p : inputs)
            mm.check(p);
        for (Vector p : outputs)
            mm.check(p);
        Vector delta = mm.getMax().sub(mm.getMin());
        if (visualElement.equalsDescription(FlipflopJK.DESCRIPTION)
                || visualElement.equalsDescription(FlipflopD.DESCRIPTION))
            return new Box(delta.x, delta.y + SIZE);   // Space for label
        else
            return new Box(delta.x, delta.y);
    }

    @Override
    public void setPos(Vector pos) {
        this.pos = pos;
    }

    @Override
    public void addToCircuit(Vector offset, Circuit circuit) {
        visualElement.setPos(pos.add(offset));
        circuit.add(visualElement);
    }

    @Override
    public List<Vector> getInputs() {
        return Vector.add(inputs, pos);
    }

    @Override
    public List<Vector> getOutputs() {
        return Vector.add(outputs, pos);
    }

    /**
     * @return the VisualElement contained in this fragment
     */
    public VisualElement getVisualElement() {
        return visualElement;
    }

    /**
     * Sets the visual element
     *
     * @param visualElement the visual element to set
     */
    public void setVisualElement(VisualElement visualElement) {
        this.visualElement = visualElement;
    }

    @Override
    public <V extends FragmentVisitor> V traverse(V v) {
        v.visit(this);
        return v;
    }
}
