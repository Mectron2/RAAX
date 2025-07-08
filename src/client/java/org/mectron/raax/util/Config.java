package org.mectron.raax.util;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.mectron.raax.commands.Manager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    };

    public static boolean SAFE_PACKETS = false;

    public static boolean isAffordableBlock(Block block) {
        for (Block b : checkblocks) {
            if (b.equals(block)) return true;
        }
        return false;
    }

    public static SimpleOption<Double> GAMMA = new SimpleOption<>("options.gamma", SimpleOption.emptyTooltip(), (optionText, value) -> {
        return Text.of("options.gamma.max");
    }, SimpleOption.DoubleSliderCallbacks.INSTANCE, (double)10000F, (value) -> {
    });

    public static final Map<Block, Integer> ORE_COLORS = new HashMap<>() {{
        put(Blocks.COAL_ORE,              0xFF2F2F2F);
        put(Blocks.DEEPSLATE_COAL_ORE,    0xFF2F2F2F);

        put(Blocks.IRON_ORE,              0xFFDBB37D);
        put(Blocks.DEEPSLATE_IRON_ORE,    0xFFDBB37D);

        put(Blocks.GOLD_ORE,              0xFFFCDC4A);
        put(Blocks.DEEPSLATE_GOLD_ORE,    0xFFFCDC4A);

        put(Blocks.LAPIS_ORE,             0xFF345EC9);
        put(Blocks.DEEPSLATE_LAPIS_ORE,   0xFF345EC9);

        put(Blocks.REDSTONE_ORE,          0xFFCE2029);
        put(Blocks.DEEPSLATE_REDSTONE_ORE,0xFFCE2029);

        put(Blocks.DIAMOND_ORE,           0xFF4EF2F2);
        put(Blocks.DEEPSLATE_DIAMOND_ORE, 0xFF4EF2F2);

        put(Blocks.EMERALD_ORE,           0xFF00D75D);
        put(Blocks.DEEPSLATE_EMERALD_ORE, 0xFF00D75D);

        put(Blocks.COPPER_ORE,            0xFFB87333);
        put(Blocks.DEEPSLATE_COPPER_ORE,  0xFFB87333);

        put(Blocks.NETHER_GOLD_ORE,       0xFFE7C934);
        put(Blocks.NETHER_QUARTZ_ORE,     0xFFE7C934);

        put(Blocks.ANCIENT_DEBRIS,        0xFF6C3B29);
    }};

    public static KeyBinding toggleXrayKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.raax.toggle_xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.raax"
    ));

    public static KeyBinding clearHighlightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.raax.clear_highlight",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.raax"
    ));

    public static KeyBinding scanBlocksKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.raax.scan_blocks",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.raax"
    ));
}


