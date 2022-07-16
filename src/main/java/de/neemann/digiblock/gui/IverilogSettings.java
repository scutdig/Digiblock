package de.neemann.digiblock.gui;

import de.neemann.digiblock.core.element.ElementAttributes;
import de.neemann.digiblock.core.element.Key;
import de.neemann.digiblock.core.element.Keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The settings of iverilog
 */
public final class IverilogSettings extends SettingsBase{

    private static final class Holder {
        static final IverilogSettings INSTANCE = new IverilogSettings();
    }

    /**
     * Returns the settings instance
     *
     * @return the Settings
     */
    public static IverilogSettings getInstance() {
        return Holder.INSTANCE;
    }

    private IverilogSettings() { super(createKeyList(), ".iverilog.cfg");}

    private static List<Key> createKeyList() {
        List<Key> intList = new ArrayList<>();
        intList.add(Keys.SETTINGS_IVERILOG_SOURCE_PATH);
        intList.add(Keys.SETTINGS_IVERILOG_TESTBENCH_PATH);

        return Collections.unmodifiableList(intList);
    }

    /**
     * Returns true if the given modification requires a restart.
     *
     * @param modified the modified settings
     * @return true if the modification requires a restart
     */
    public boolean requiresRestart(ElementAttributes modified) {
        for (Key<?> key : getKeys())
            if (key.getRequiresRestart() && !getAttributes().equalsKey(key, modified))
                return true;

        return false;
    }

}

