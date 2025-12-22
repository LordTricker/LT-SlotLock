package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.gui.SlotSettingsInventoryScreen;
import pl.lordtricker.ltsl.core.SlotLockLogic;
import pl.lordtricker.ltsl.core.SlotLockState;

@Mixin(HandledScreen.class)
public abstract class SlotOverlayMixin {
    @Inject(
            method = "drawSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/item/ItemStack;III)V",
                    shift = At.Shift.BEFORE
            ),
            require = 0
    )
    private void onDrawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        if (!SlotLockState.isSlotSettingsActive()) {
            return;
        }
        if (!((Object)this instanceof SlotSettingsInventoryScreen)) {
            return;
        }
        if (slot.id < 9 || slot.id >= 45) {
            return;
        }
        int color = SlotLockLogic.slotSelectionOverlayColor(slot.id);
        context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, color);
    }
}
