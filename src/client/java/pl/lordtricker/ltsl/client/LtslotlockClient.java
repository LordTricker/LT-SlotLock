package pl.lordtricker.ltsl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import pl.lordtricker.ltsl.client.command.ClientCommandRegistration;
import pl.lordtricker.ltsl.client.config.ConfigLoader;
import pl.lordtricker.ltsl.client.config.ServersConfig;
import pl.lordtricker.ltsl.client.keybinding.Toggling;
import pl.lordtricker.ltsl.client.util.ColorUtils;
import pl.lordtricker.ltsl.client.util.Messages;

public class LtslotlockClient implements ClientModInitializer {
	public static ServersConfig serversConfig;
	public static boolean slotSettingsActive = false;

	@Override
	public void onInitializeClient() {
		Toggling.init();

		serversConfig = ConfigLoader.loadConfig();

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			if (client.player != null) {
				String welcomeMsg = Messages.get("player.join");
				client.player.sendMessage(ColorUtils.translateColorCodes(welcomeMsg), false);
			}
		});

		ClientCommandRegistration.registerCommands();
	}
}
