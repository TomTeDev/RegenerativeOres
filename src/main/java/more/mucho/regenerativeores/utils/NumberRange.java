package more.mucho.regenerativeores.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberRange {
    private final double min;
    private final double max;

    public NumberRange(double x, double y) {
        this.min = Math.min(x, y);
        this.max = Math.max(x, y);
    }

    // Core methods
    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double roll() {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    // Utility methods
    public boolean contains(double value) {
        return value >= min && value <= max;
    }

    public double clamp(double value) {
        return Math.max(min, Math.min(max, value));
    }

    public double size() {
        return max - min;
    }

    @Override
    public String toString() {
        return "[" + min + ".." + max + "]";
    }
}