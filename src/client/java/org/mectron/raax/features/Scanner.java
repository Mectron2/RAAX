package org.mectron.raax.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.mectron.raax.api.Toggleable;

public class Scanner implements Toggleable {
    private boolean enabled = false;
    boolean flag3 = false;
    int keyBind;

    public Scanner(int keyBind) {
        this.keyBind = keyBind;
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
        boolean flag2 = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyBind);
        if (!flag2) {
            flag3 = false;
            return false;
        }
        if (flag3) {
            return false;
        }
        flag3 = true;
        return true;
    }
}