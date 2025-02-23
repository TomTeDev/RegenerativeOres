package more.mucho.regenerativeores.commands;

import org.bukkit.command.CommandSender;

public class BasicConfigMessages implements ConfigMessages{
    //TODO implement gui an use it as path;
    @Override
    public void senDirectMessage(CommandSender target, String message) {
        target.sendMessage(message);
    }

    @Override
    public void sendPathMessage(CommandSender target, String path) {
        target.sendMessage(path);
    }


}
