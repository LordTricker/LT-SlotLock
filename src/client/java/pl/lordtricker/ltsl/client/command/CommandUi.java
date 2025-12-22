package pl.lordtricker.ltsl.client.command;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import pl.lordtricker.ltsl.client.util.ColorUtils;

import java.net.URI;

public final class CommandUi {
    private CommandUi() {}

    public static MutableText colored(String text) {
        MutableText out = Text.empty();
        out.append(ColorUtils.translateColorCodes(text));
        return out;
    }

    public static MutableText clickable(String text, ClickEvent.Action action, String command, String hoverText) {
        MutableText out = colored(text);
        ClickEvent clickEvent;
        if (action == ClickEvent.Action.OPEN_URL) {
            clickEvent = new ClickEvent(action, URI.create(command).toString());
        } else if (action == ClickEvent.Action.CHANGE_PAGE) {
            clickEvent = new ClickEvent(action, Integer.toString(Integer.parseInt(command)));
        } else {
            clickEvent = new ClickEvent(action, command);
        }
        Style style = Style.EMPTY.withClickEvent(clickEvent);
        if (hoverText != null && !hoverText.isEmpty()) {
            style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(hoverText)));
        }
        out.setStyle(style);
        return out;
    }
}
