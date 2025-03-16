package pl.lordtricker.ltsl.client;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import pl.lordtricker.ltsl.client.command.ClientCommandRegistration;
import pl.lordtricker.ltsl.client.config.ConfigLoader;
import pl.lordtricker.ltsl.client.config.SettingsConfig;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

public class LtslotlockClient implements ClientModInitializer {
	public static SettingsConfig serversConfig;
	public static boolean slotSettingsActive = false;
	public static boolean slotLockEnabled = true;
	public static boolean itemFrameLockEnabled = false;

	@Override
	public void onInitializeClient() {

		serversConfig = ConfigLoader.loadConfig();

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			if (client.player != null) {
				String welcomeMsg = Messages.get("player.join");
				client.player.sendMessage(ColorUtils.translateColorCodes(welcomeMsg), false);
			}
		});

		CommandDispatcher<FabricClientCommandSource> dispatcher = ClientCommandManager.DISPATCHER;
		ClientCommandRegistration.registerCommands(dispatcher);
	}
}
