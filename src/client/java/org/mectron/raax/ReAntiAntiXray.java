package org.mectron.raax;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.mectron.raax.gui.ProgressBar;
import org.mectron.raax.manager.FeatureManager;
import org.mectron.raax.mixin.WorldRendererAccessor;
import org.mectron.raax.util.Logger;
import org.mectron.raax.util.NoDepthRenderLayer;
import org.mectron.raax.util.RefreshingJob;
import org.mectron.raax.util.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.mectron.raax.util.Config.ORE_COLORS;
import static org.mectron.raax.util.Config.clearHighlightKey;
import static org.mectron.raax.util.Config.isAffordableBlock;
import static org.mectron.raax.util.Config.scanBlocksKey;
import static org.mectron.raax.util.Config.toggleXrayKey;

public class ReAntiAntiXray implements ClientModInitializer {
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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleXrayKey.wasPressed()) {
                FeatureManager.XRAY.toggle();
                assert client.player != null;
                client.player.sendMessage(
                        Text.of("§7X-Ray: " + (FeatureManager.XRAY.isEnabled() ? "§aenabled" : "§cdisabled")),
                        true
                );
            }

            while (clearHighlightKey.wasPressed()) {
                Runner.clearScannedBlocks();
                assert client.player != null;
                client.player.sendMessage(
                        Text.of("§7Block highlights cleared"),
                        true
                );
            }

            if (scanBlocksKey.wasPressed()) {
                FeatureManager.SCANNER.toggle();
                assert client.player != null;
                client.player.sendMessage(
                        Text.of("§7Scanning blocks..."),
                        true
                );
            }
        });

        WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            MatrixStack matrices = context.matrixStack();
            Camera camera = context.camera();
            Vec3d camPos = camera.getPos();

            VertexConsumerProvider.Immediate buffer = client.getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer consumer = buffer.getBuffer(NoDepthRenderLayer.RENDER_LAYER);
            WorldRendererAccessor renderer = (WorldRendererAccessor) client.worldRenderer;

            Queue<BlockPos> blocksToHighlight = Runner.getScannedBlocks();

            for (BlockPos blockPos : blocksToHighlight) {
                BlockState state = client.world.getBlockState(blockPos);

                if (isAffordableBlock(state.getBlock())) {
                    renderer.invokeDrawBlockOutline(
                            matrices,
                            consumer,
                            client.player,
                            camPos.x, camPos.y, camPos.z,
                            blockPos,
                            state,
                            ORE_COLORS.get(state.getBlock())
                    );
                }
            }

            buffer.draw();
        });
    }
}