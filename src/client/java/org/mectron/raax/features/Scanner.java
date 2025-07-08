package org.mectron.raax.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.mectron.raax.api.Toggleable;
import org.mectron.raax.util.Config;

public class Scanner implements Toggleable {
    private final KeyBinding keyBind = Config.scanBlocksKey;
    private boolean enabled = false;
    private boolean wasPressedLastTick = false;

    @Override
    public String getName() {
        return "Scanner";
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void toggle() {
        enabled = !enabled;
    }

    public boolean checkPressed() {
        if (MinecraftClient.getInstance().currentScreen != null) return false;

        String tk = keyBind.getBoundKeyTranslationKey();
        InputUtil.Key mcKey = InputUtil.fromTranslationKey(tk);

        boolean isCurrentlyPressed = InputUtil.isKeyPressed(
                MinecraftClient.getInstance().getWindow().getHandle(),
                mcKey.getCode()
        );

        if (!isCurrentlyPressed) {
            wasPressedLastTick = false;
            return false;
        }

        if (wasPressedLastTick) {
            return false;
        }

        wasPressedLastTick = true;
        return true;
    }
}
