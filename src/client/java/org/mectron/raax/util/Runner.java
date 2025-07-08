package org.mectron.raax.util;

import org.mectron.raax.gui.ProgressBar;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class Runner implements Runnable {
    boolean isRunning = true;
    long delay;
    int rad;
    ProgressBar progressBar;

    private static final Queue<BlockPos> blocksToHighlight = new ConcurrentLinkedQueue<>();

    public static Queue<BlockPos> getBlocksToHighlight() {
        return blocksToHighlight;
    }

    public static void clearHighlightedBlocks() {
        blocksToHighlight.clear();
    }

    public Runner(int rad, long delay, ProgressBar progressBar) {
        this.rad = rad;
        this.delay = delay;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        ClientPlayNetworkHandler conn = MinecraftClient.getInstance().getNetworkHandler();
        if (conn == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        BlockPos pos = client.player.getBlockPos();
        Block[] checkBlocks = Config.checkblocks;

        blocksToHighlight.clear();

        for (int cx = -rad; cx <= rad; cx++) {
            for (int cy = -rad; cy <= rad; cy++) {
                for (int cz = -rad; cz <= rad; cz++) {
                    if (!isRunning) break;
                    progressBar.progress++;

                    BlockPos currentBlock = new BlockPos(pos.getX() + cx, pos.getY() + cy, pos.getZ() + cz);

                    Block block = client.player.clientWorld.getBlockState(currentBlock).getBlock();

                    boolean good = Config.scanAll;
                    for (Block checkblock : checkBlocks) {
                        if (block.equals(checkblock)) {
                            good = true;
                            break;
                        }
                    }

                    if (!good) {
                        continue;
                    }

                    PlayerActionC2SPacket packet = new PlayerActionC2SPacket(
                            Config.SAFE_PACKETS ? PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK : PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                            currentBlock,
                            Direction.DOWN
                    );
                    conn.sendPacket(packet);

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                if (!isRunning) break;
            }
            if (!isRunning) break;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (int cx = -rad; cx <= rad; cx++) {
            for (int cy = -rad; cy <= rad; cy++) {
                for (int cz = -rad; cz <= rad; cz++) {
                    BlockPos currentBlock = new BlockPos(pos.getX() + cx, pos.getY() + cy, pos.getZ() + cz);
                    Block block = client.player.clientWorld.getBlockState(currentBlock).getBlock();

                    for (Block checkblock : checkBlocks) {
                        if (block.equals(checkblock)) {
                            blocksToHighlight.offer(currentBlock);
                            break;
                        }
                    }
                }
            }
        }
        progressBar.done = true;
    }
}