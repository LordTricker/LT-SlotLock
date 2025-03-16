package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin {

    @Unique
    private static long lastGlobalMessageTime = 0L;

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (LtslotlockClient.itemFrameLockEnabled) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastGlobalMessageTime > 50) {
                String msg = Messages.get("action.itemframe.locked");
                player.sendMessage(ColorUtils.translateColorCodes(msg), false);
                lastGlobalMessageTime = currentTime;
            }
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
