package org.mectron.raax.gui;

import org.mectron.raax.util.Config;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.util.Identifier;

public class ProgressBar implements Toast {
    private static final Identifier TEXTURE = Identifier.ofVanilla("toast/system");
    public boolean done = false;
    public int progress = 1;
    public double todo = Math.pow(Config.rad * 2 + 1, 3);

    private static double round(double value) {
        int scale = (int) Math.pow(10, 2);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public Visibility getVisibility() {
        return done ? Visibility.HIDE : Visibility.SHOW;
    }

    @Override
    public void update(ToastManager manager, long delta) {
    }

    @Override
    public void draw(DrawContext context, TextRenderer textRenderer, long startTime) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, this.getWidth(), this.getHeight());
        context.drawText(textRenderer, "Scanning Blocks...", 18, 7, 0xFF_FF_FF_FF, false);

        double percent = Math.min((progress / todo) * 100.0, 100.0);
        String percentText = "Progress: " + round(percent) + "%";
        context.drawText(textRenderer, percentText, 18, 20, 0xFF_FF_FF_FF, false);

        int barWidth = 150;
        int barHeight = 8;
        int filled = (int) ((progress / todo) * barWidth);
        int barX = 5, barY = 35;

        context.fill(barX, barY, barX + barWidth, barY + barHeight, 0xFF555555);
        context.fill(barX, barY, barX + filled, barY + barHeight, 0xFF00FF00);

        if (done) {
            context.drawText(textRenderer, "Done!", 18, 48, 0xFF00FF00, false);
        }
    }
}
