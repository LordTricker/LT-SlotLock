package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.keybinding.Toggling;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

@Mixin(ScreenHandler.class)
public abstract class PreventEqDropMixin {

    private static long lastEqDropMessageTime = 0;

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if(MinecraftClient.getInstance().currentScreen == null) return;
        if (Toggling.slotsEnabled
                && actionType == SlotActionType.THROW
                && slotIndex >= 9 && slotIndex < 45) {
            if (LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(slotIndex)) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastEqDropMessageTime > 5) {
                    player.sendMessage(ColorUtils.translateColorCodes(Messages.get("action.throw.denied")), false);
                    lastEqDropMessageTime = currentTime;
                }
                ci.cancel();
            }
        }
    }
}
