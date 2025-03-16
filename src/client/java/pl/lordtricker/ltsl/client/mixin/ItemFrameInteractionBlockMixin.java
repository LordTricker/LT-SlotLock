package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;

@Mixin(ClientConnection.class)
public abstract class ItemFrameInteractionBlockMixin {

    /**
     * Wstrzykujemy się w metodę "send" (oficjalne Mojang 1.21+),
     * która wysyła pakiety do serwera.
     * Jeśli pakiet to PlayerInteractEntityC2SPacket i dotyczy ItemFrameEntity,
     * anulujemy wysyłkę, aby uniemożliwić umieszczenie przedmiotu w ramce.
     */
    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof PlayerInteractEntityC2SPacket interactPacket) {
            int entityId = ((PlayerInteractEntityC2SPacketAccessor) interactPacket).getEntityId();

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null) {
                Entity target = client.world.getEntityById(entityId);
                if (target instanceof ItemFrameEntity && LtslotlockClient.itemFrameLockEnabled) {
                    ci.cancel();
                }
            }
        }
    }
}
