package pl.lordtricker.ltsl.client.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.gui.SlotSettingsInventoryScreen;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;


public class ClientCommandRegistration {

    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistration::registerLtFilterCommand);
    }

    private static void registerLtFilterCommand(
            CommandDispatcher<FabricClientCommandSource> dispatcher,
            CommandRegistryAccess registryAccess
    ) {
        dispatcher.register(
                ClientCommandManager.literal("lts")
                        // /ltf – podstawowe info
                        .executes(ctx -> {
                            String message = Messages.get("mod.info");
                            ctx.getSource().sendFeedback(ColorUtils.translateColorCodes(message));
                            return 1;
                        })
                        // /ltb settings – otwarcie GUI ustawień
                        .then(ClientCommandManager.literal("settings")
                                .executes(ctx -> {
                                    LtslotlockClient.slotSettingsActive = true;
                                    MinecraftClient client = MinecraftClient.getInstance();
                                    client.setScreen(null);
                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        client.execute(() -> client.setScreen(new SlotSettingsInventoryScreen()));
                                    }).start();
                                    return 1;
                                })
                        )
                        // /ltf pomoc
                        .then(ClientCommandManager.literal("pomoc")
                                .executes(ctx -> {
                                    String msg = Messages.get("command.help");
                                    ctx.getSource().sendFeedback(ColorUtils.translateColorCodes(msg));
                                    return 1;
                                })
                        )
        );
    }
}