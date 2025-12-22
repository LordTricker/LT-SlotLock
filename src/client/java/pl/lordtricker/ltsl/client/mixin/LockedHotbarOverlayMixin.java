package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.core.SlotLockState;

@Mixin(InGameHud.class)
public class LockedHotbarOverlayMixin {

    private static final Identifier LOCK_ICON = Identifier.of("ltsl", "textures/gui/lock.png");

    @Inject(
            method = "renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
            at = @At("HEAD")
    )
    private void onRenderHotbarItem(DrawContext context,
                                    int x,
                                    int y,
                                    RenderTickCounter tickCounter,
                                    PlayerEntity player,
                                    ItemStack stack,
                                    int seed,
                                    CallbackInfo ci) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int startX = (screenWidth / 2) - 88;
        int index = (x - startX) / 20;
        if (index < 0 || index > 8) {
            return;
        }
        if (!SlotLockState.isSlotLocked(36 + index)) {
            return;
        }
        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                LOCK_ICON,
                x - 1,
                y - 1,
                0F,
                0F,
                18,
                18,
                18,
                18
        );
    }
}
