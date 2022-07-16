package de.neemann.digiblock.gui.components.terminal.Serial;

import de.neemann.digiblock.core.Node;
import de.neemann.digiblock.core.NodeException;
import de.neemann.digiblock.core.ObservableValue;
import de.neemann.digiblock.core.ObservableValues;
import de.neemann.digiblock.core.element.Element;
import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.ElementTypeDescription;
import de.neemann.digiblock.core.element.Keys;
import de.neemann.digiblock.core.io.In;
import de.neemann.digiblock.draw.elements.PinException;
import de.neemann.digiblock.gui.components.terminal.Serial.Utils.ArrayUtils;
import de.neemann.digiblock.gui.components.terminal.Serial.Utils.ByteUtils;
import de.neemann.digiblock.gui.components.terminal.Terminal;
import de.neemann.digiblock.gui.components.terminal.TerminalDialog;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

import static de.neemann.digiblock.core.element.PinInfo.input;

public class Bus extends Node implements Element {

    /**
     * The serialport description
     */
    public static final ElementTypeDescription DESCRIPTION
            = new ElementTypeDescription(Bus.class,
            input("C").setClock(),
            input("DI"))
            .addAttribute(Keys.ROTATE)
            .addAttribute(Keys.LABEL);

    private final String label;
    private final ElementAttributes attr;
    private SerialDialog serialDialog;
    private ObservableValue clock;
    private boolean lastClock;
    private ObservableValue dataIn;
    private ObservableValue dataOut;

    public Bus(ElementAttributes attributes) {
        attr = attributes;
        label = attributes.getLabel();
        dataOut = new ObservableValue("DO", 32)
                .setToHighZ()
                .setPinDescription(DESCRIPTION);
    }

    @Override
    public void readInputs() throws NodeException {
        boolean clockVal = clock.getBool();
        if (!lastClock && clockVal) {
            int value = (int) this.dataIn.getValue();
            String hexStr = Integer.toHexString(value);
            int len = hexStr.length();
            for (int i = 0; i < 32 - len; i++) {
                hexStr = "0" + hexStr;
            }
            if (serialDialog != null) {
                serialDialog.sendData(ByteUtils.hexStr2Byte(hexStr));
            }
        }
        lastClock = clockVal;
    }

    @Override
    public void writeOutputs() throws NodeException {
        if (serialDialog != null) {
            dataOut.setValue(serialDialog.getReadData());
        } else {
            dataOut.setValue(0L);
        }
    }

    @Override
    public void setInputs(ObservableValues inputs) throws NodeException {
        clock = inputs.get(0).addObserverToValue(this).checkBits(1, this);
        dataIn = inputs.get(1).addObserverToValue(this).checkBits(32, this);
    }

    @Override
    public ObservableValues getOutputs() throws PinException {
        return new ObservableValues(dataOut);
    }
    public void setSerial(SerialDialog serialDialog) {
        this.serialDialog = serialDialog;
    }
    protected void finalize() throws Throwable {
        if (serialDialog.isOpen()) {
            serialDialog.close();
        }
    }
}
