package org.mectron.raax.mixin;

import net.minecraft.client.option.SimpleOption;
import org.mectron.raax.util.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.option.GameOptions;
import org.mectron.raax.manager.FeatureManager;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "getGamma", at = @At("HEAD"), cancellable = true)
    private void onGetGamma(CallbackInfoReturnable<SimpleOption<Double>> cir) {
        if (FeatureManager.XRAY.isEnabled()) {
            cir.setReturnValue(Config.GAMMA);
        }
    }
}
