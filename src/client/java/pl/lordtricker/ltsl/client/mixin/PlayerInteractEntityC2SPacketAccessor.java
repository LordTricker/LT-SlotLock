package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor Mixin do prywatnego pola "entityId" w PlayerInteractEntityC2SPacket.
 */
@Mixin(PlayerInteractEntityC2SPacket.class)
public interface PlayerInteractEntityC2SPacketAccessor {

    @Accessor("entityId")
    int getEntityId();
}
