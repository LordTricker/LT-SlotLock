package pl.lordtricker.ltsl.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static pl.lordtricker.ltsl.core.config.SlotSettings.adsEnabled;

public final class CoreConfigLoader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String MAIN_CONFIG_FILE_NAME = "ltslotlock-config.json";

    private CoreConfigLoader() {}

    public static ServersConfig loadConfig(Path modConfigDir) {
        Path configDir = ensureConfigDir(modConfigDir);
        Path configFile = configDir.resolve(MAIN_CONFIG_FILE_NAME);
        if (!Files.exists(configFile)) {
            ServersConfig defaultConfig = createDefaultConfig();
            saveConfig(defaultConfig, configDir);
            return defaultConfig;
        }
        try (Reader reader = Files.newBufferedReader(configFile)) {
            ServersConfig loadedConfig = GSON.fromJson(reader, ServersConfig.class);
            return loadedConfig == null ? new ServersConfig() : loadedConfig;
        } catch (IOException e) {
            e.printStackTrace();
            return new ServersConfig();
        }
    }

    public static void saveConfig(ServersConfig config, Path modConfigDir) {
        Path configDir = ensureConfigDir(modConfigDir);
        Path configFile = configDir.resolve(MAIN_CONFIG_FILE_NAME);
        try (Writer writer = Files.newBufferedWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path ensureConfigDir(Path modConfigDir) {
        if (modConfigDir == null) {
            throw new IllegalArgumentException("modConfigDir is null");
        }
        try {
            if (!Files.exists(modConfigDir)) {
                Files.createDirectories(modConfigDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modConfigDir;
    }

    private static ServersConfig createDefaultConfig() {
        ServersConfig cfg = new ServersConfig();
        cfg.slotSettings.doNotCleanSlots = List.of();
        cfg.adsEnabled = adsEnabled;
        cfg.slotSettings.activeSlotHex = "#80ccff";
        cfg.slotSettings.blockedSlotHex = "#80cc00";
        cfg.slotLockEnabled = true;
        cfg.itemFrameLockEnabled = true;
        return cfg;
    }
}
