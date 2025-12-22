package pl.lordtricker.ltsl.core;

import pl.lordtricker.ltsl.core.config.ServersConfig;
import pl.lordtricker.ltsl.core.config.SlotSettings;

public final class SlotLockState {
    private static ServersConfig serversConfig = new ServersConfig();
    private static boolean slotSettingsActive = false;
    private static boolean slotLockEnabled = true;
    private static boolean itemFrameLockEnabled = false;

    private SlotLockState() {}

    public static void init(ServersConfig config) {
        if (config == null) {
            serversConfig = new ServersConfig();
            slotLockEnabled = serversConfig.slotLockEnabled;
            itemFrameLockEnabled = serversConfig.itemFrameLockEnabled;
            return;
        }
        serversConfig = config;
        slotLockEnabled = config.slotLockEnabled;
        itemFrameLockEnabled = config.itemFrameLockEnabled;
    }

    public static ServersConfig getConfig() {
        return serversConfig;
    }

    public static SlotSettings getSlotSettings() {
        return serversConfig.slotSettings;
    }

    public static boolean isSlotSettingsActive() {
        return slotSettingsActive;
    }

    public static void setSlotSettingsActive(boolean slotSettingsActive) {
        SlotLockState.slotSettingsActive = slotSettingsActive;
    }

    public static boolean isSlotLockEnabled() {
        return slotLockEnabled;
    }

    public static void setSlotLockEnabled(boolean slotLockEnabled) {
        SlotLockState.slotLockEnabled = slotLockEnabled;
        serversConfig.slotLockEnabled = slotLockEnabled;
    }

    public static boolean isItemFrameLockEnabled() {
        return itemFrameLockEnabled;
    }

    public static void setItemFrameLockEnabled(boolean itemFrameLockEnabled) {
        SlotLockState.itemFrameLockEnabled = itemFrameLockEnabled;
        serversConfig.itemFrameLockEnabled = itemFrameLockEnabled;
    }

    public static boolean isSlotLocked(int slotIndex) {
        return slotLockEnabled && serversConfig.slotSettings.doNotCleanSlots.contains(slotIndex);
    }
}
