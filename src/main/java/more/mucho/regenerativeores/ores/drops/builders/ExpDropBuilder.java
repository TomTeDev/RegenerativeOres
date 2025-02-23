package more.mucho.regenerativeores.ores.drops.builders;

import more.mucho.regenerativeores.ores.Range;
import more.mucho.regenerativeores.ores.drops.BaseExpDrop;

public class ExpDropBuilder extends DropBuilder<BaseExpDrop> {

    public static ExpDropBuilder builder() {
        return new ExpDropBuilder();
    }
    @Override
    public BaseExpDrop build() {
        return new BaseExpDrop(
                new Range<>(this.getMinAmount(), this.getMaxAmount()),
                this.getChance(),
                this.isDirect(),
                this.getMessage(),
                this.getSound()

        );
    }
}
