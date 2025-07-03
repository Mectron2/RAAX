package org.mectron.raax.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.mectron.raax.util.Config;

public class SafeMode extends Base {
    public SafeMode() {
        super("SafeMode", new String[]{"safemode", "safe", "sm"}, "Switches packets to be more invisible to anti-cheats. Possible lower scanning accuracy");
    }

    @Override
    public void run(String[] args) {
        Config.SAFE_PACKETS = !Config.SAFE_PACKETS;
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] " + "Safe mode " + (Config.SAFE_PACKETS ? "en" : "dis") + "abled!"), false);
    }
}
