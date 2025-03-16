package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;

@Mixin(HandledScreen.class)
public abstract class LockedSlotOverlayMixin {

    private static final Identifier LOCK_ICON = Identifier.of("ltsl", "textures/gui/lock_icon.png");

    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void drawLockedOverlay(DrawContext context, Slot slot, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen != null && !(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)) {
            return;
        }
        if (LtslotlockClient.slotLockEnabled
                && slot.id >= 9
                && LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(slot.id)) {
            context.drawTexture(
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
