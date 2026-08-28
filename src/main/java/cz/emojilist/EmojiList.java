package cz.emojilist;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class EmojiList extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfig();

        PluginCommand command = getCommand("emojis");
        if (command != null) {
            EmojiCommand emojiCommand = new EmojiCommand(this);
            command.setExecutor(emojiCommand);
            command.setTabCompleter(emojiCommand); // Registrace našeptávače
        }

        getLogger().info("Plugin EmojiList byl úspěšně aktivován!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
