package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;
import pl.lordtricker.ltsl.core.SlotLockLogic;

@Mixin(ScreenHandler.class)
public abstract class PreventEqDropMixin {


    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.currentScreen != null && !(client.currentScreen instanceof InventoryScreen)) {
            return;
        }

        if (actionType == SlotActionType.THROW) {
            if (SlotLockLogic.shouldBlockThrowAction(slotIndex)) {
                if (SlotLockLogic.shouldSendEqDropMessage()) {
                    player.sendMessage(ColorUtils.translateColorCodes(Messages.get("action.throw.denied")), false);
                }
                ci.cancel();
            }
        }
    }
}
