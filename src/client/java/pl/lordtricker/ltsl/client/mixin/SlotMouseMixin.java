package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.config.SlotSettings;

@Mixin(HandledScreen.class)
public abstract class SlotMouseMixin {

    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (!LtslotlockClient.slotSettingsActive) return;
        if (button != 2) return;

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        if (screen.getScreenHandler() == null) return;

        DefaultedList<Slot> slots = ((ScreenHandlerAccessor) screen.getScreenHandler()).getSlots();
        if (slots == null || slots.size() < 45) return;

        SlotSettings settings = LtslotlockClient.serversConfig.slotSettings;

        for (int i = 9; i < 45; i++) {
            Slot slot = slots.get(i);
            if (slot == null) continue;

            int realX = this.x + slot.x;
            int realY = this.y + slot.y;

            if (mouseX >= realX && mouseX < realX + 16 &&
                    mouseY >= realY && mouseY < realY + 16) {
                int index = i;
                if (settings.doNotCleanSlots.contains(index)) {
                    settings.doNotCleanSlots.remove(Integer.valueOf(index));
                } else {
                    settings.doNotCleanSlots.add(index);
                }
                cir.setReturnValue(true);
                return;
            }
        }
    }
}


