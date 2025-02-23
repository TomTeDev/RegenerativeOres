package more.mucho.regenerativeores.commands;

import more.mucho.regenerativeores.utils.Colors;
import org.bukkit.command.CommandSender;

public interface MessagesHandler {
    default void sendAlert(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(Colors.RED_INFO + message);
    }

    default void sendConfirm(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(Colors.GREEN_INFO + message);
    }

}
