package more.mucho.regenerativeores.commands;

public abstract class BaseCommand implements CustomCommand,MessagesHandler {

    private String[] commandNames;

    public BaseCommand(String... commandNames) {
        this.commandNames = commandNames;
    }

    public String[] getCommands() {
        return commandNames;
    }

}
