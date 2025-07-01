package org.mectron.raax.commands;

import org.mectron.raax.util.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Help extends Base {
    public Help() {
        super("Help", new String[]{"help", "?", "h", ""}, "Lists all commands");
    }

    @Override
    public void run(String[] args) {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] All commands you can use:"), false);
        Config.cmdmanager.get().forEach(base -> MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX]  - " + base.name + " (" + String.join(", ", base.aliases) + "): " + base.description), false));
        super.run(args);
    }
}
