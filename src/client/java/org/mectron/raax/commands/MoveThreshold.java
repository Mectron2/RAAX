package org.mectron.raax.commands;

import org.mectron.raax.util.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class MoveThreshold extends Base {
    public MoveThreshold() {
        super("MoveThreshold", new String[]{"movethreshold", "move", "m"}, "Set the radius");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 2) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Please provide a number as argument."), false);
            return;
        }
        String newmovethreshold = args[1];
        int newmovethresholdI;
        try {
            newmovethresholdI = Integer.parseInt(newmovethreshold);
        } catch (Exception ex) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Please provide a VALID number as argument."), false);
            return;
        }
        Config.movethreshhold = newmovethresholdI;
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Set new move threshold to " + newmovethresholdI), false);
        super.run(args);
    }
}
