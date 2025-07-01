package org.mectron.raax.util;

import org.mectron.raax.gui.ProgressBar;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class Runner implements Runnable {
    boolean isRunning = true;
    long delay;
    int rad;
    ProgressBar pbar;

    public Runner(int rad, long delay, ProgressBar pbar) {
        this.rad = rad;
        this.delay = delay;
        this.pbar = pbar;
    }

    @Override
    public void run() {
        ClientPlayNetworkHandler conn = MinecraftClient.getInstance().getNetworkHandler();
        if (conn == null) return;
        assert MinecraftClient.getInstance().player != null;
        BlockPos pos = MinecraftClient.getInstance().player.getBlockPos();


        // Blocks that aren't ores but still needs to be checked
        Block[] checkBlocks = Config.checkblocks;

        for (int cx = -rad; cx <= rad; cx++) {
            for (int cy = -rad; cy <= rad; cy++) {
                for (int cz = -rad; cz <= rad; cz++) {
                    if (!isRunning) break;
                    pbar.progress++;
                    BlockPos currentBlock = new BlockPos(pos.getX() + cx, pos.getY() + cy, pos.getZ() + cz);

                    Block block = MinecraftClient.getInstance().player.clientWorld.getBlockState(currentBlock).getBlock();

                    boolean good = Config.scanAll; // cool for else man

                    // only check if block is a ore or in checkblocks (obsidian for example)
                    for (Block checkblock : checkBlocks) {
                        if (block.equals(checkblock)) {
                            //Logger.info(block.toString() + " Is in checkbloks or a ore");
                            good = true;
                            break;
                        }
                    }

                    if (!good) {
                        continue;
                    }


                    //Logger.info("Checking " + block.toString() + " at " + currblock.toShortString());


                    PlayerActionC2SPacket packet = new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK,
                            currentBlock,
                            Direction.DOWN
                    );
                    conn.sendPacket(packet);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
        pbar.done = true;
    }
}
