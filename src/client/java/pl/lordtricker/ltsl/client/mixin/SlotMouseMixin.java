package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.lordtricker.ltsl.core.SlotLockLogic;
import pl.lordtricker.ltsl.core.SlotLockState;

@Mixin(HandledScreen.class)
public abstract class SlotMouseMixin {

    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(Click click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        if (!SlotLockState.isSlotSettingsActive()) return;
        int button = click.button();
        if (button != 0 && button != 2) return;

        double mouseX = click.x();
        double mouseY = click.y();

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        if (screen.getScreenHandler() == null) return;

        DefaultedList<Slot> slots = ((ScreenHandlerAccessor) screen.getScreenHandler()).getSlots();
        if (slots == null || slots.size() < 45) return;

        for (int i = 9; i < 45; i++) {
            Slot slot = slots.get(i);
            if (slot == null) continue;

            int realX = this.x + slot.x;
            int realY = this.y + slot.y;

            if (mouseX >= realX && mouseX < realX + 16 &&
                    mouseY >= realY && mouseY < realY + 16) {
                SlotLockLogic.toggleSlotSetting(i);
                cir.setReturnValue(true);
                return;
            }
        }
    }
}


