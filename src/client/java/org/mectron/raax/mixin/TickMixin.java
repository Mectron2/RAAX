package org.mectron.raax.mixin;

import org.mectron.raax.ReAntiAntiXray;
import org.mectron.raax.util.Config;
import org.mectron.raax.util.Logger;
import org.mectron.raax.util.RefreshingJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientPlayerEntity.class)
public class TickMixin {

    public BlockPos old;
    public int movedBlocks;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        List<RefreshingJob> nl = new ArrayList<>();
        ReAntiAntiXray.jobs.forEach(refreshingJob -> {
            if (!refreshingJob.progress.done) {
                nl.add(refreshingJob);
            }
        });
        ReAntiAntiXray.jobs = nl;
        if (ReAntiAntiXray.rvn.checkPressed()) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("Refreshing blocks..."), true);
            ReAntiAntiXray.revealNewBlocks(Config.rad, Config.delay);
        }
        if (ReAntiAntiXray.removeBlockBeta.checkPressed()) {
            for (int cx = -10; cx <= 10; cx++) {
                for (int cy = -10; cy <= 10; cy++) {
                    for (int cz = -10; cz <= 10; cz++) {
                        assert MinecraftClient.getInstance().crosshairTarget != null;
                        BlockPos b2r = ((BlockHitResult) MinecraftClient.getInstance().crosshairTarget).getBlockPos();

                        assert MinecraftClient.getInstance().player != null;
                        //BlockState a = MinecraftClient.getInstance().player.world.getBlockState(b2r.add(cx,cy,cz));

                        Block s = Block.getBlockFromItem(MinecraftClient.getInstance().player.getInventory().getSelectedStack().getItem());
                        BlockState b = Blocks.AIR.getDefaultState();
                        if (s != null) b = s.getDefaultState();

                        MinecraftClient.getInstance().player.clientWorld.setBlockState(b2r.add(cx, cy, cz), b);
                        //MinecraftClient.getInstance().player.world.removeBlock(b2r.add(cx, cy, cz), false);
                    }
                }
            }
        }

        if (Config.auto) {
            try {
                assert MinecraftClient.getInstance().player != null;
                BlockPos pos = MinecraftClient.getInstance().player.getBlockPos();

                if (pos != old) {
                    movedBlocks++;

                    if (movedBlocks > Config.movethreshhold && ReAntiAntiXray.jobs.isEmpty()) {
                        ReAntiAntiXray.revealNewBlocks(Config.rad, Config.delay);
                        Logger.info("Scanning new pos: " + pos.toShortString());
                        movedBlocks = 0;
                    }
                }
                old = pos;

            } catch (NullPointerException e) {
                Logger.info("Null Error");
            }
        }
    }
}
