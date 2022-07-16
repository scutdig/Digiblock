package de.neemann.digiblock.plugin;

import de.neemann.digiblock.core.IntFormat;
import de.neemann.digiblock.core.element.Key;

/**
 * Define the key constants for plugin
 */
public final class PluginKeys {
    private PluginKeys() {}

    /**
     * Set the mode to normal
     */
    public static final Key<Boolean> IS_NORMAL
            = new Key<>("isNormal", true)
            .setName("Normal")
            .setSecondary();


    /**
     * Set the mode to read_before_write
     */
    public static final Key<Boolean> IS_READ_BEFORE_WRITE
            = new Key<>("isReadBeforeWrite", true)
            .setName("Read before write")
            .setSecondary();

    /**
     * Set the mode to write_through
     */
    public static final Key<Boolean> IS_WRITE_THROUGH
            = new Key<>("isWriteThrough", false)
            .setName("Write through")
            .setSecondary();

    /**
     *  设置是否有输出锁存
     */
    public static final Key<Boolean> WITH_OUTPUT_REG
            = new Key<>("withOutputReg", false)
            .setName("With output reg")
            .setSecondary();

    /**
     * Output format for numbers
     */
    public static final Key<IntFormat> INT_FORMAT
            = new Key.KeyEnum<>("intFormat", IntFormat.def, IntFormat.values())
            .setSecondary();

    /**
     * set the mode to primary i2c
     */
    public static final Key<Boolean> Primary_I2C
            = new Key<>("primary_i2c",false)
            .setName("primary i2c mode")
            .setSecondary();


    /**
     * set the mode to secondary i2c
     */
    public static final Key<Boolean> Secondary_I2C
            = new Key<>("secondary_i2c",false)
            .setName("secondary i2c mode")
            .setSecondary();
    /**
     * set the mode to spi
     */
    public static final Key<Boolean> SPI
            = new Key<>("spi",false)
            .setName("spi mode")
            .setSecondary();
}
