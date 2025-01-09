package more.mucho.regenerativeores.commands;

public abstract class AbstractCommand implements CustomCommand {

    private String[] commandNames;

    public AbstractCommand(MessagesHandler messagesHandler, String... commandNames) {
        this.commandNames = commandNames;
    }

    public String[] getCommands() {
        return commandNames;
    }

}
