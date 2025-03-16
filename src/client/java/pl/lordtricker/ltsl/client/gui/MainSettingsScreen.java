package pl.lordtricker.ltsl.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import pl.lordtricker.ltsl.client.LtslotlockClient;
import pl.lordtricker.ltsl.client.config.ConfigLoader;

public class MainSettingsScreen extends Screen {

    private ButtonWidget slotSettingsButton;
    private ButtonWidget toggleSlotLockButton;
    private ButtonWidget toggleItemFrameLockButton;
    private ButtonWidget saveButton;

    public MainSettingsScreen() {
        super(Text.of("LT-SlotLock Settings"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int btnWidth = 170;
        int btnHeight = 20;
        int spacing = 5;
        int totalHeight = btnHeight * 3 + spacing * 2;
        int startY = (this.height - totalHeight) / 2;

        ButtonWidget toggleSlotLockButton = new ButtonWidget(
                centerX - btnWidth / 2,
                startY,
                btnWidth,
                btnHeight,
                Text.of("Slot Locking: " + (LtslotlockClient.slotLockEnabled ? "ON" : "OFF")),
                btn -> {
                    LtslotlockClient.slotLockEnabled = !LtslotlockClient.slotLockEnabled;
                    String newState = LtslotlockClient.slotLockEnabled ? "ON" : "OFF";
                    btn.setMessage(Text.of("Slot Locking: " + newState));
                }
        );
        addDrawableChild(toggleSlotLockButton);

        ButtonWidget toggleItemFrameLockButton = new ButtonWidget(
                centerX - btnWidth / 2,
                startY + btnHeight + spacing,
                btnWidth,
                btnHeight,
                Text.of("Item Frame Lock: " + (LtslotlockClient.itemFrameLockEnabled ? "ON" : "OFF")),
                btn -> {
                    LtslotlockClient.itemFrameLockEnabled = !LtslotlockClient.itemFrameLockEnabled;
                    String newState = LtslotlockClient.itemFrameLockEnabled ? "ON" : "OFF";
                    btn.setMessage(Text.of("Item Frame Lock: " + newState));
                }
        );
        addDrawableChild(toggleItemFrameLockButton);


        ButtonWidget slotSettingsButton = new ButtonWidget(
                centerX - btnWidth / 2,
                startY + 2 * (btnHeight + spacing + 5),
                btnWidth,
                btnHeight,
                Text.of("Slot Settings"),
                btn -> {
                    LtslotlockClient.slotSettingsActive = true;
                    this.client.setScreen(new SlotSettingsInventoryScreen());
                }
        );
        addDrawableChild(slotSettingsButton);

        ButtonWidget saveButton = new ButtonWidget(
                centerX - (btnWidth / 2),
                this.height - btnHeight - 20,
                btnWidth,
                btnHeight,
                Text.of("Save and close"),
                btn -> {
                    ConfigLoader.saveConfig(LtslotlockClient.serversConfig);
                    this.client.setScreen(null);
                }
        );
        addDrawableChild(saveButton);
    }

    @Override
    public void removed() {
        ConfigLoader.saveConfig(LtslotlockClient.serversConfig);
        super.removed();
    }

    protected void renderCenteredText(MatrixStack matrices, net.minecraft.client.font.TextRenderer textRenderer, Text text, int x, int y, int color) {
        int textWidth = textRenderer.getWidth(text);
        textRenderer.draw(matrices, text, x - textWidth / 2, y, color);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        renderCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }
}
