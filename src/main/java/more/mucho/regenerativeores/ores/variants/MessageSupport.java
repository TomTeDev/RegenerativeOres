package more.mucho.regenerativeores.ores.variants;

import more.mucho.regenerativeores.ores.MiningMessage;

import java.util.Optional;

public interface MessageSupport {
    Optional<MiningMessage> getMessage();
}
