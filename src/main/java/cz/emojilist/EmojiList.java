package cz.emojilist;

import org.bukkit.plugin.java.JavaPlugin;

public final class EmojiList extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfig();

        if (getCommand("emojis") != null) {
            getCommand("emojis").setExecutor(new EmojiCommand(this));
        }

        getLogger().info("Plugin EmojiList byl úspěšně aktivován!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
