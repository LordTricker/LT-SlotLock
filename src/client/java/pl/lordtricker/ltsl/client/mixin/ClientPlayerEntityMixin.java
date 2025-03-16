package pl.lordtricker.ltsl.client.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends net.minecraft.client.network.AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile, null);
    }

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void dropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen != null && !(client.currentScreen instanceof InventoryScreen)) {
            return;
        }

        int selected = this.getInventory().selectedSlot;
        int eqSlot = selected + 36;
        boolean isLocked = LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(eqSlot);

        if (LtslotlockClient.slotLockEnabled && isLocked && !this.getInventory().getStack(selected).isEmpty()) {
            String msg = Messages.get("action.throw.denied");
            this.sendMessage(ColorUtils.translateColorCodes(msg), false);
            cir.setReturnValue(false);
        }
    }
}
