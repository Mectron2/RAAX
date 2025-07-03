package org.mectron.raax;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.mectron.raax.util.Logger;
import org.mectron.raax.util.KeyBind;
import org.mectron.raax.util.Config;
import org.mectron.raax.util.Runner;
import org.mectron.raax.gui.ProgressBar;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.mectron.raax.util.RefreshingJob;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.mectron.raax.manager.FeatureManager;

import java.util.ArrayList;
import java.util.List;

public class ReAntiAntiXray implements ClientModInitializer {
    public static KeyBind rvn = new KeyBind(Config.kcScan);
    public static KeyBind removeBlockBeta = new KeyBind(Config.kcRemove);
    private static KeyBinding toggleXrayKey;
    public static List<RefreshingJob> jobs = new ArrayList<>();

    public static void revealNewBlocks(int rad, long delayInMS) {
        ProgressBar progressBar = new ProgressBar();
        MinecraftClient.getInstance().getToastManager().add(progressBar);
        RefreshingJob rfj = new RefreshingJob(new Runner(rad, delayInMS, progressBar), progressBar);
        jobs.add(rfj);
    }

    @Override
    public void onInitializeClient() {
        Logger.info("Loading and initializing RAAX...");

        FeatureManager.init();

        toggleXrayKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.raax.toggle_xray",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.raax"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleXrayKey.wasPressed()) {
                FeatureManager.XRAY.toggle();
                assert client.player != null;
                client.player.sendMessage(
                        Text.of("§7X-Ray: " + (FeatureManager.XRAY.isEnabled() ? "§aвключён" : "§cвыключен")),
                        true
                );
            }
        });
    }
}
