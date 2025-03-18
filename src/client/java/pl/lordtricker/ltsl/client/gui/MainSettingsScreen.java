package pl.lordtricker.ltsl.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.config.ConfigLoader;

public class MainSettingsScreen extends Screen {

    private ButtonWidget slotSettingsButton;
    private ButtonWidget toggleSlotLockButton;
    private ButtonWidget toggleItemFrameLockButton;
    private ButtonWidget saveButton;

    public MainSettingsScreen() {
        super(Text.literal("LT-SlotLock Settings"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int btnWidth = 170;
        int btnHeight = 20;
        int spacing = 5;
        int totalHeight = btnHeight * 3 + spacing * 2;
        int startY = (this.height - totalHeight) / 2;

        toggleSlotLockButton = ButtonWidget.builder(
                Text.literal("Slot Locking: " + (LtslotlockClient.serversConfig.slotLockEnabled ? "ON" : "OFF")),
                btn -> {
                    LtslotlockClient.serversConfig.slotLockEnabled = !LtslotlockClient.serversConfig.slotLockEnabled;
                    String newState = LtslotlockClient.serversConfig.slotLockEnabled ? "ON" : "OFF";
                    btn.setMessage(Text.literal("Slot Locking: " + newState));
                }
        ).dimensions(centerX - btnWidth / 2, startY, btnWidth, btnHeight).build();
        addDrawableChild(toggleSlotLockButton);

        toggleItemFrameLockButton = ButtonWidget.builder(
                Text.literal("Item Frame Lock: " + (LtslotlockClient.serversConfig.itemFrameLockEnabled ? "ON" : "OFF")),
                btn -> {
                    LtslotlockClient.serversConfig.itemFrameLockEnabled = !LtslotlockClient.serversConfig.itemFrameLockEnabled;
                    String newState = LtslotlockClient.serversConfig.itemFrameLockEnabled ? "ON" : "OFF";
                    btn.setMessage(Text.literal("Item Frame Lock: " + newState));
                }
        ).dimensions(centerX - btnWidth / 2, startY + btnHeight + spacing, btnWidth, btnHeight).build();
        addDrawableChild(toggleItemFrameLockButton);


        slotSettingsButton = ButtonWidget.builder(
                Text.literal("Slot Settings"),
                btn -> {
                    LtslotlockClient.slotSettingsActive = true;
                    this.client.setScreen(new SlotSettingsInventoryScreen());
                }
        ).dimensions(centerX - btnWidth / 2, startY + 2 * (btnHeight + spacing + 5), btnWidth, btnHeight).build();
        addDrawableChild(slotSettingsButton);

        saveButton = ButtonWidget.builder(
                Text.literal("Save and close"),
                btn -> {
                    ConfigLoader.saveConfig(LtslotlockClient.serversConfig);
                    this.client.setScreen(null);
                }
        ).dimensions(centerX - btnWidth / 2, this.height - btnHeight - 20, btnWidth, btnHeight).build();
        addDrawableChild(saveButton);
    }

    @Override
    public void removed() {
        ConfigLoader.saveConfig(LtslotlockClient.serversConfig);
        super.removed();
    }

    protected void drawCenteredText(DrawContext context, net.minecraft.client.font.TextRenderer textRenderer, Text text, int x, int y, int color) {
        int textWidth = textRenderer.getWidth(text);
        context.drawText(textRenderer, text, x - textWidth / 2, y, color, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawCenteredText(context, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }
}
