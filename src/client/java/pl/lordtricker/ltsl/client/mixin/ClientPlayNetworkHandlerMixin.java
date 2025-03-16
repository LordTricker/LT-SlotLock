package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;

@Mixin(ClientConnection.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ClickSlotC2SPacket clickSlotPacket) {
            if (clickSlotPacket.getActionType() == SlotActionType.THROW) {
                int slotIndex = clickSlotPacket.getSlot();
                boolean isLocked = LtslotlockClient.slotLockEnabled &&
                        LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(slotIndex);
                if (isLocked) {
                    ci.cancel();
                }
            }
        }
    }
}
