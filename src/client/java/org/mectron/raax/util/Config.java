package org.mectron.raax.util;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import org.mectron.raax.commands.Manager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.io.IOException;

public class Config {
    public static int rad = 5;
    public static long delay = 20;
    public static Manager cmdmanager = new Manager();
    public static boolean scanAll = false;
    public static boolean auto = false;
    public static int movethreshhold = 5;
    public static Block[] checkblocks = {Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.ANCIENT_DEBRIS,
            Blocks.OBSIDIAN,
            Blocks.CLAY,
            Blocks.MOSSY_COBBLESTONE
    };
    public static int kcScan;
    public static int kcRemove;
    public static boolean SAFE_PACKETS = false;

    public static SimpleOption<Double> GAMMA = new SimpleOption<>("options.gamma", SimpleOption.emptyTooltip(), (optionText, value) -> {
        return Text.of("options.gamma.max");
    }, SimpleOption.DoubleSliderCallbacks.INSTANCE, (double)10000F, (value) -> {
    });

    static {
        try {
            kcScan = ConfigHelper.getScanKBFromFile();
            kcRemove = ConfigHelper.getRemoveKBFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


