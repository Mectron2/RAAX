package org.mectron.raax.mixin;

import org.mectron.raax.commands.Base;
import org.mectron.raax.util.Config;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class CommandMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, CallbackInfo ci) {
        if (message.toLowerCase().startsWith(":")) {
            ci.cancel();
            String[] args = message.substring(1).trim().split(" +");
            String cmd = args[0].toLowerCase();
            Base cmd2r = Config.cmdmanager.getByName(cmd);
            if (cmd2r != null) cmd2r.run(args);
        }
    }
}
