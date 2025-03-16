package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;

@Mixin(InGameHud.class)
public class LockedHotbarOverlayMixin {

    private static final Identifier LOCK_ICON = Identifier.of("ltsl", "textures/gui/lock_icon.png");

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void onRenderHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int hotbarWidth = 182;
        int xStart = (screenWidth - hotbarWidth) / 2;
        int y = screenHeight - 22;

        for (int i = 0; i < 9; i++) {
            int eqSlot = 36 + i;
            int slotX = xStart + i * 20;

            if (LtslotlockClient.slotLockEnabled
                    && LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(eqSlot)) {
                context.drawTexture(
                        RenderLayer::getGuiTextured,
                        LOCK_ICON,
                        slotX +2,
                        y +2,
                        0F,
                        0F,
                        18,
                        18,
                        18,
                        18
                );
            }
        }
    }
}
