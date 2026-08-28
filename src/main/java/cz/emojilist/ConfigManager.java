package cz.emojilist;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final EmojiList plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public ConfigManager(EmojiList plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    public Component getMessage(String path) {
        String msg = plugin.getConfig().getString("messages." + path, "&cMessage missing: " + path);
        return parseColor(msg);
    }

    public List<Component> getBookPages() {
        List<Component> pages = new ArrayList<>();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection bookSection = config.getConfigurationSection("book-gui");

        if (bookSection == null) {
            pages.add(parseColor("&cChyba: Sekce book-gui nebyla nalezena v config.yml!"));
            return pages;
        }

        for (String key : bookSection.getKeys(false)) {
            List<String> lines = bookSection.getStringList(key);
            Component pageComponent = Component.empty();

            for (int i = 0; i < lines.size(); i++) {
                Component lineComp = parseColor(lines.get(i));
                pageComponent = pageComponent.append(lineComp);
                if (i < lines.size() - 1) {
                    pageComponent = pageComponent.append(Component.newline());
                }
            }
            pages.add(pageComponent);
        }

        return pages;
    }

    public Component parseColor(String text) {
        if (text == null) return Component.empty();
        // Podpora legacy & barev i moderního MiniMessage kódování
        Component legacyParsed = legacySerializer.deserialize(text);
        String miniMessageFormatted = miniMessage.serialize(legacyParsed).replace("\\", "");
        return miniMessage.deserialize(miniMessageFormatted);
    }
}
