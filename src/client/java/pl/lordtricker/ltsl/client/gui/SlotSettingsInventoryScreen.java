package pl.lordtricker.ltsl.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.config.ConfigLoader;

public class SlotSettingsInventoryScreen extends InventoryScreen {

    public SlotSettingsInventoryScreen() {
        super(MinecraftClient.getInstance().player);
    }

    @Override
    public void removed() {
        ConfigLoader.saveConfig(LtslotlockClient.serversConfig);
        LtslotlockClient.slotSettingsActive = false;
        super.removed();
    }
}
