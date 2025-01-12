package more.mucho.regenerativeores.ores.variants;

import more.mucho.regenerativeores.ores.MiningCommand;

import java.util.List;

public interface CommandExecutable {
    List<MiningCommand> getCommands();
}