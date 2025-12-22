package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.core.SlotLockState;

@Mixin(HandledScreen.class)
public abstract class LockedSlotOverlayMixin {

    private static final Identifier LOCK_ICON = Identifier.of("ltsl", "textures/gui/lock.png");

    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void drawLockedOverlay(DrawContext context, Slot slot, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen != null && !(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)) {
            return;
        }
        if (slot.id >= 9 && SlotLockState.isSlotLocked(slot.id)) {
            context.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    LOCK_ICON,
                    slot.x,
                    slot.y,
                    0F,
                    0F,
                    16,
                    16,
                    16,
                    16
            );
        }
    }
}
