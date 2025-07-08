package org.mectron.raax;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.mectron.raax.mixin.WorldRendererAccessor;
import org.mectron.raax.util.*;
import org.mectron.raax.gui.ProgressBar;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.mectron.raax.manager.FeatureManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ReAntiAntiXray implements ClientModInitializer {
    public static KeyBind rvn = new KeyBind(Config.kcScan);
    public static KeyBind removeBlockBeta = new KeyBind(Config.kcRemove);
    private static KeyBinding toggleXrayKey;
    private static KeyBinding clearHighlightKey;
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

        clearHighlightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.raax.clear_highlight",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.raax"
        ));

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
                Runner.clearHighlightedBlocks();
                assert client.player != null;
                client.player.sendMessage(
                        Text.of("§7Block highlights cleared"),
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

            Queue<BlockPos> blocksToHighlight = Runner.getBlocksToHighlight();

            for (BlockPos blockPos : blocksToHighlight) {
                BlockState state = client.world.getBlockState(blockPos);

                if (!state.isAir()) {
                    renderer.invokeDrawBlockOutline(
                            matrices,
                            consumer,
                            client.player,
                            camPos.x, camPos.y, camPos.z,
                            blockPos,
                            state,
                            0xFF00FFFF
                    );
                }
            }

            buffer.draw();
        });
    }
}