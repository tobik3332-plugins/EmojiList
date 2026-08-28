package cz.emojilist;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EmojiCommand implements CommandExecutor, TabCompleter {

    private final EmojiList plugin;

    public EmojiCommand(EmojiList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("emojis.admin.reload")) {
                sender.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
                return true;
            }

            plugin.getConfigManager().loadConfig();
            sender.sendMessage(plugin.getConfigManager().getMessage("reload-success"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (!player.hasPermission("emojis.open")) {
            player.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return true;
        }

        List<Component> pages = plugin.getConfigManager().getBookPages();

        Book emojiBook = Book.book(
                Component.text("Emoji List"),
                Component.text("Server"),
                pages
        );

        player.openBook(emojiBook);
        return true;
    }

    // Našeptávač (Tab-Completion)
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("emojis.admin.reload") && "reload".startsWith(args[0].toLowerCase())) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
