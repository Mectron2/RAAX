package org.mectron.raax.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
    @Invoker("drawBlockOutline")
    void invokeDrawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity,
                                double cameraX, double cameraY, double cameraZ,
                                BlockPos pos, BlockState state, int color);
}
