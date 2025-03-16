package pl.lordtricker.ltsl.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigLoader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String MAIN_CONFIG_FILE_NAME = "ltslotlock-config.json";
    private static final Path MOD_CONFIG_DIR;

    static {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        MOD_CONFIG_DIR = configDir.resolve("LT-Mods").resolve("LT-SlotLock");
        try {
            if (!Files.exists(MOD_CONFIG_DIR)) {
                Files.createDirectories(MOD_CONFIG_DIR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SettingsConfig loadConfig() {
        Path configFile = MOD_CONFIG_DIR.resolve(MAIN_CONFIG_FILE_NAME);
        if (!Files.exists(configFile)) {
            SettingsConfig defaultConfig = createDefaultConfig();
            saveConfig(defaultConfig);
            return defaultConfig;
        }
        try (Reader reader = Files.newBufferedReader(configFile)) {
            SettingsConfig loadedConfig = GSON.fromJson(reader, SettingsConfig.class);
            return loadedConfig;
        } catch (IOException e) {
            e.printStackTrace();
            return new SettingsConfig();
        }
    }

    public static void saveConfig(SettingsConfig config) {
        Path configFile = MOD_CONFIG_DIR.resolve(MAIN_CONFIG_FILE_NAME);
        try (Writer writer = Files.newBufferedWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SettingsConfig createDefaultConfig() {
        SettingsConfig cfg = new SettingsConfig();
        cfg.slotSettings.doNotCleanSlots = List.of();
        cfg.slotSettings.activeSlotHex = "#80ccff";
        cfg.slotSettings.blockedSlotHex = "#80cc00";
        cfg.slotLockEnabled = true;
        cfg.itemFrameLockEnabled = true;
        return cfg;
    }
}
