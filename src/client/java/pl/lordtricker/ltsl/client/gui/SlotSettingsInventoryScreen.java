package pl.lordtricker.ltsl.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import pl.lordtricker.ltsl.client.config.ConfigLoader;
import pl.lordtricker.ltsl.core.SlotLockState;

public class SlotSettingsInventoryScreen extends InventoryScreen {
    private static final Text HELP_LINE_EN = buildHelpLine(
            "Use ",
            "Left Click",
            " or ",
            "Scroll Mouse Button",
            " to select slots"
    );
    private static final Text HELP_LINE_PL = buildHelpLine(
            "Uzyj ",
            "lewego",
            " lub ",
            "srodkowego przycisku myszy",
            ", aby zaznaczyc sloty"
    );
    private static final Text LEGEND_LINE_GREEN = buildLegendLine("Green", Formatting.GREEN, " = Locked from dropping");
    private static final Text LEGEND_LINE_RED = buildLegendLine("Red", Formatting.RED, " = Can be dropped");
    private static final int HELP_COLOR = 0xFFFFFF;
    private static final float HELP_SCALE = 0.8f;

    public SlotSettingsInventoryScreen() {
        super(MinecraftClient.getInstance().player != null ? MinecraftClient.getInstance().player : null);
    }

    @Override
    public void removed() {
        ConfigLoader.saveConfig(SlotLockState.getConfig());
        SlotLockState.setSlotSettingsActive(false);
        super.removed();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int centerX = this.width / 2;
        int guiTop = (this.height - this.backgroundHeight) / 2;
        int guiBottom = guiTop + this.backgroundHeight;
        int line1Y = guiTop - (this.textRenderer.fontHeight * 2) - 12;
        if (line1Y < 6) {
            line1Y = 6;
        }
        int line2Y = line1Y + this.textRenderer.fontHeight + 2;
        drawCenteredTextScaled(context, HELP_LINE_EN, centerX, line1Y, HELP_COLOR, HELP_SCALE);
        drawCenteredTextScaled(context, HELP_LINE_PL, centerX, line2Y, HELP_COLOR, HELP_SCALE);

        int legendLine1Y = guiBottom + 8;
        int legendLine2Y = legendLine1Y + this.textRenderer.fontHeight + 2;
        drawCenteredTextScaled(context, LEGEND_LINE_GREEN, centerX, legendLine1Y, HELP_COLOR, HELP_SCALE);
        drawCenteredTextScaled(context, LEGEND_LINE_RED, centerX, legendLine2Y, HELP_COLOR, HELP_SCALE);
    }

    private void drawCenteredTextScaled(DrawContext context, Text text, int x, int y, int color, float scale) {
        var matrices = context.getMatrices();
        matrices.push();
        matrices.scale(scale, scale, 1.0f);
        int scaledX = Math.round(x / scale);
        int scaledY = Math.round(y / scale);
        int textWidth = this.textRenderer.getWidth(text);
        context.drawText(this.textRenderer, text, scaledX - textWidth / 2, scaledY, color, false);
        matrices.pop();
    }

    private static Text buildHelpLine(String prefix, String highlight1, String mid, String highlight2, String suffix) {
        return Text.empty()
                .append(Text.literal(prefix).formatted(Formatting.WHITE))
                .append(Text.literal(highlight1).formatted(Formatting.GOLD, Formatting.BOLD))
                .append(Text.literal(mid).formatted(Formatting.WHITE))
                .append(Text.literal(highlight2).formatted(Formatting.GOLD, Formatting.BOLD))
                .append(Text.literal(suffix).formatted(Formatting.WHITE));
    }

    private static Text buildLegendLine(String label, Formatting labelColor, String suffix) {
        return Text.empty()
                .append(Text.literal(label).formatted(labelColor, Formatting.BOLD))
                .append(Text.literal(suffix).formatted(Formatting.WHITE));
    }
}
