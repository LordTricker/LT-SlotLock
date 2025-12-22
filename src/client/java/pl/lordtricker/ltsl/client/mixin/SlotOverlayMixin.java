package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.core.SlotLockLogic;
import pl.lordtricker.ltsl.core.SlotLockState;

@Mixin(HandledScreen.class)
public abstract class SlotOverlayMixin {

    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!SlotLockState.isSlotSettingsActive()) return;

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        if (screen.getScreenHandler() == null) return;

        DefaultedList<Slot> slots = ((ScreenHandlerAccessor) screen.getScreenHandler()).getSlots();
        if (slots == null || slots.size() < 45) return;

        for (int i = 9; i < 45; i++) {
            Slot slot = slots.get(i);
            if (slot == null) continue;

            int color = SlotLockLogic.slotSelectionOverlayColor(i);

            int realX = this.x + slot.x;
            int realY = this.y + slot.y;
            context.fill(realX, realY, realX + 16, realY + 16, color);
        }
    }
}



