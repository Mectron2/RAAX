package org.mectron.raax.features;

import net.minecraft.client.MinecraftClient;
import org.mectron.raax.api.Toggleable;

public class XRay implements Toggleable {
    private boolean enabled = false;

    @Override
    public String getName() {
        return "X-Ray";
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void toggle() {
        enabled = !enabled;
        MinecraftClient.getInstance().worldRenderer.reload();
    }
}
