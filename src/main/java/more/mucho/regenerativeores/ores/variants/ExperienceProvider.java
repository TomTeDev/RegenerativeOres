package more.mucho.regenerativeores.ores.variants;

import more.mucho.regenerativeores.ores.MiningExp;

import java.util.Optional;

public interface ExperienceProvider {
    Optional<MiningExp> getMiningExp();
}