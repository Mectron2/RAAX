package org.mectron.raax.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.mectron.raax.manager.FeatureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static org.mectron.raax.util.Config.checkblocks;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyReturnValue(method = "shouldDrawSide", at = @At("RETURN"))
    private static boolean onShouldDrawSide(boolean original, BlockState state, BlockState otherState, Direction side) {
        if (FeatureManager.XRAY.isEnabled()) {
            for (Block block : checkblocks) {
                if (state.isOf(block)) {
                    return true;
                }
            }
            return false;
        } else {
            return original;
        }
    }
}