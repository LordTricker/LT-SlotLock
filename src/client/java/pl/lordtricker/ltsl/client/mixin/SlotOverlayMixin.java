package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.config.SlotSettings;

@Mixin(HandledScreen.class)
public abstract class SlotOverlayMixin {

    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!LtslotlockClient.slotSettingsActive) return;

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        if (screen.getScreenHandler() == null) return;

        DefaultedList<Slot> slots = ((ScreenHandlerAccessor) screen.getScreenHandler()).getSlots();
        if (slots == null || slots.size() < 45) return;

        SlotSettings settings = LtslotlockClient.serversConfig.slotSettings;

        for (int i = 9; i < 45; i++) {
            Slot slot = slots.get(i);
            if (slot == null) continue;

            int color = settings.doNotCleanSlots.contains(i)
                    ? 0x8000FF00
                    : 0x80FF0000;

            int realX = this.x + slot.x;
            int realY = this.y + slot.y;

            DrawableHelper.fill(matrices, realX, realY, realX + 16, realY + 16, color);
        }
    }
}
