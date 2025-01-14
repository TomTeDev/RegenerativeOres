package more.mucho.regenerativeores.ores;

public class Range<T, V> {
    public T min;
    public V max;

    public Range(T min, V max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public V getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "{" + min + " " + max + "}";
    }

    public static <T, V> Range<T, V> of(T min, V max) {
        return new Range<>(min, max);
    }
}
