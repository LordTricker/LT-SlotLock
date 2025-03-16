package pl.lordtricker.ltsl.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.LtslotlockClient;

@Mixin(HandledScreen.class)
public abstract class LockedSlotOverlayMixin {
    private static final Identifier LOCK_ICON = new Identifier("ltsl", "textures/gui/lock_icon.png");

    @Shadow protected int x;
    @Shadow protected int y;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderLocks(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)) {
            return;
        }

        if (!LtslotlockClient.slotLockEnabled) {
            return;
        }

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        if (screen.getScreenHandler() == null) return;

        DefaultedList<Slot> slots = ((ScreenHandlerAccessor) screen.getScreenHandler()).getSlots();
        if (slots.size() < 45) return;

        RenderSystem.setShaderTexture(0, LOCK_ICON);

        for (int i = 9; i < 45; i++) {
            Slot slot = slots.get(i);
            if (slot == null) continue;

            if (LtslotlockClient.serversConfig.slotSettings.doNotCleanSlots.contains(slot.id)) {
                int realX = this.x + slot.x;
                int realY = this.y + slot.y;

                DrawableHelper.drawTexture(
                        matrices,
                        realX,
                        realY,
                        0f,
                        0f,
                        16,
                        16,
                        16,
                        16
                );
            }
        }
    }
}
