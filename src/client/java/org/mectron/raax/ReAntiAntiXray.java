package org.mectron.raax;

import org.mectron.raax.util.Logger;
import org.mectron.raax.util.KeyBind;
import org.mectron.raax.util.Config;
import org.mectron.raax.util.Runner;
import org.mectron.raax.gui.ProgressBar;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.mectron.raax.util.RefreshingJob;

import java.util.ArrayList;
import java.util.List;

public class ReAntiAntiXray implements ClientModInitializer {
    public static KeyBind rvn = new KeyBind(Config.kcScan);
    public static KeyBind removeBlockBeta = new KeyBind(Config.kcRemove);
    public static List<RefreshingJob> jobs = new ArrayList<>();

    public static void revealNewBlocks(int rad, long delayInMS) {
        ProgressBar progressBar = new ProgressBar();
        MinecraftClient.getInstance().getToastManager().add(progressBar);
        RefreshingJob rfj = new RefreshingJob(new Runner(rad, delayInMS, progressBar), progressBar);
        jobs.add(rfj);
    }

    @Override
    public void onInitializeClient() {
        Logger.info("Loading and initializing AAX...");
    }
}
