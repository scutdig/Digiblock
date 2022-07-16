package de.neemann.digiblock.plugin.lattice;

import de.neemann.digiblock.core.Node;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.draw.elements.PinException;

import static de.neemann.digiblock.core.element.PinInfo.input;

/**
 * A PLL component in Lattice
 */
public class PLL extends Node implements Element {

    /**
     * The PLL description
     */
    public static final ElementTypeDescription DESCRIPTION = new ElementTypeDescription(PLL.class,
            input("CLKI", ""))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL);

    private final String label;

    private ObservableValue clkI;

    private ObservableValue clkOp;

    private long outValue;

    /**
     * Creates a new instance
     *
     * @param attributes the elements attributes
     */
    public PLL(ElementAttributes attributes) {
        label = attributes.getLabel();
        clkOp = new ObservableValue("CLKOP", 1).setPinDescription(DESCRIPTION);
    }

    @Override
    public void readInputs() throws NodeException {
        this.outValue = this.clkI.getValue();
    }

    @Override
    public void writeOutputs() throws NodeException {
        clkOp.setValue(this.outValue);
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        this.clkI = inputs.get(0).addObserverToValue(this).checkBits(1, this);
    }

    @Override
    public ObservableValues getOutputs() throws PinException {
        return this.clkOp.asList();
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }
}
