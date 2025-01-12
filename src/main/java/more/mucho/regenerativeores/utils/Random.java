package more.mucho.regenerativeores.utils;

public class Random {
        private static final java.util.Random RANDOM = new java.util.Random();

        // Generates a random integer in the range [0, bound)
        public static int randomInt(int bound) {
            if (bound <= 0) {
                throw new IllegalArgumentException("Bound must be greater than 0");
            }
            return RANDOM.nextInt(bound);
        }

        // Generates a random integer in the range [min, max] (inclusive)
        public static int randomInt(int min, int max) {
            if (min > max) {
                throw new IllegalArgumentException("Min must be less than or equal to max");
            }
            return min + RANDOM.nextInt((max - min) + 1);
        }

        // Other utility methods can be added here
    }
}
