package pl.lordtricker.ltsl.core;

import pl.lordtricker.ltsl.core.config.SlotSettings;

public final class SlotLockLogic {
    private static long lastEqDropMessageTime = 0L;
    private static long lastItemFrameMessageTime = 0L;

    private SlotLockLogic() {}

    public static boolean shouldBlockThrowAction(int slotIndex) {
        return SlotLockState.isSlotLocked(slotIndex);
    }

    public static boolean shouldBlockItemFrameInteraction() {
        return SlotLockState.isItemFrameLockEnabled();
    }

    public static int slotSelectionOverlayColor(int slotIndex) {
        SlotSettings settings = SlotLockState.getSlotSettings();
        return settings.doNotCleanSlots.contains(slotIndex)
                ? 0x8000FF00
                : 0x80FF0000;
    }

    public static boolean toggleSlotSetting(int slotIndex) {
        SlotSettings settings = SlotLockState.getSlotSettings();
        if (settings.doNotCleanSlots.contains(slotIndex)) {
            settings.doNotCleanSlots.remove(Integer.valueOf(slotIndex));
            return false;
        }
        settings.doNotCleanSlots.add(slotIndex);
        return true;
    }

    public static boolean shouldSendEqDropMessage() {
        long now = System.currentTimeMillis();
        if (now - lastEqDropMessageTime > 50) {
            lastEqDropMessageTime = now;
            return true;
        }
        return false;
    }

    public static boolean shouldSendItemFrameLockedMessage() {
        long now = System.currentTimeMillis();
        if (now - lastItemFrameMessageTime > 50) {
            lastItemFrameMessageTime = now;
            return true;
        }
        return false;
    }
}
