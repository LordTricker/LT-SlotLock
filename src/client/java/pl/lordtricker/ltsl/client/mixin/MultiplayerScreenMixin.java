package pl.lordtricker.ltsl.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.option.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lordtricker.ltsl.client.util.ServerListPatcher;
import pl.lordtricker.ltsl.core.config.SlotSettings;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {

    @Unique
    private boolean ltsl$refreshedOnce = false;

    @Inject(method = "init", at = @At("TAIL"))
    private void ltsl$afterInit(CallbackInfo ci) {
        if (!SlotSettings.adsEnabled) return;
        // First immediate try
        ServerList sl = ltsl$findServerList(this);
        if (sl != null) {
            ServerListPatcher.injectOrMove(sl);
        }
        // Schedule one delayed refresh to catch late remote-config fetch
        if (!ltsl$refreshedOnce) {
            ltsl$refreshedOnce = true;
            try {
                Thread t = new Thread(() -> {
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                    MinecraftClient mc = MinecraftClient.getInstance();
                    if (mc != null) {
                        mc.execute(() -> {
                            ServerList s2 = ltsl$findServerList(this);
                            if (s2 != null) {
                                ServerListPatcher.injectOrMove(s2);
                            }
                        });
                    }
                }, "ltsl-servers-refresh");
                t.setDaemon(true);
                t.start();
            } catch (Throwable ignored) {}
        }
    }

    @Unique
    private static ServerList ltsl$findServerList(Object screen) {
        try {
            for (java.lang.reflect.Field f : screen.getClass().getDeclaredFields()) {
                if (ServerList.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    Object v = f.get(screen);
                    if (v instanceof ServerList sl) {
                        return sl;
                    }
                }
            }
        } catch (Throwable ignored) {}
        return null;
    }
}
