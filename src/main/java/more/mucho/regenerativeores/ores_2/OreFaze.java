package more.mucho.regenerativeores.ores_2;

import more.mucho.regenerativeores.ores.mining_blocks.MiningBlock;
import more.mucho.regenerativeores.ores_2.actions.ActionChain;

public interface OreFaze {
    MiningBlock getOreBlock();
    MiningBlock getReplacement();
    ActionChain[] getActionChains();
}
