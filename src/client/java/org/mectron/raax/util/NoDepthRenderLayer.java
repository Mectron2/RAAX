package org.mectron.raax.util;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;

import static net.minecraft.client.render.RenderLayer.of;

import java.util.OptionalDouble;

public class NoDepthRenderLayer {
    public static final RenderLayer.MultiPhase RENDER_LAYER = of("lines", 1536,RenderPipelines.LINES, RenderLayer.MultiPhaseParameters.builder().lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty())).layering(RenderPhase.NO_LAYERING).target(RenderPhase.OUTLINE_TARGET).build(false));
}
