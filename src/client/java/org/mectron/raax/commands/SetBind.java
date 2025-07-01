package org.mectron.raax.commands;

import org.mectron.raax.ReAntiAntiXray;
import org.mectron.raax.util.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.IOException;

public class SetBind extends Base {
    public SetBind() {
        super("setbind", new String[]{"setbind", "sb", "bind"}, "Sets custom binds for either scanning or removing blocks");
    }

    @Override
    public void run(String[] args) {
        assert MinecraftClient.getInstance().player != null;
        if (args.length < 3) {

            MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Please provide the property you want to chage as 2nd argument and the key to change it to as 3rd argument."), false);
            return;
        }
        int kc = args[2].toUpperCase().charAt(0);
        switch (args[1].toLowerCase()) {
            case "scan":
                ReAntiAntiXray.rvn.setKeyCode(kc);
                try {
                    ConfigHelper.setScanKBToFile(kc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Set scanning keybind to " + ((char) kc)), false);
                break;
            case "remove":
                ReAntiAntiXray.removeBlockBeta.setKeyCode(kc);
                try {
                    ConfigHelper.setRemoveKBToFile(kc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Set removing keybind to " + ((char) kc)), false);
                break;
            default:
                MinecraftClient.getInstance().player.sendMessage(Text.of("[RAAX] Invalid property. Please choose either \"scan\" or \"remove\"."), false);
                return;
        }
        super.run(args);
    }
}
