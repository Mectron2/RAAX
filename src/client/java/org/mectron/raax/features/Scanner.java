package org.mectron.raax.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.mectron.raax.api.Toggleable;

public class Scanner implements Toggleable {
    private boolean enabled = false;
    private boolean wasPressedLastTick = false;
    private final int keyCode;

    public Scanner(int keyCode) {
        this.keyCode = keyCode;
    }

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

        boolean isCurrentlyPressed = InputUtil.isKeyPressed(
                MinecraftClient.getInstance().getWindow().getHandle(),
                keyCode
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
