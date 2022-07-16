public class Converter {

    private static final Double STRIDE_LENGTH;
    private static final Integer GOAL_STEPS_FOR_DAY;
    private static final Double CALORIES;
    private static final Integer ONE_THOUSAND;

    static {
        STRIDE_LENGTH = 75.0;
        GOAL_STEPS_FOR_DAY = 10_000;
        CALORIES = 50.0;
        ONE_THOUSAND = 1_000;
    }

    public double calculateStepsToKm(int steps) {
        return (steps * STRIDE_LENGTH) / GOAL_STEPS_FOR_DAY;
    }

    public double calculateKkal(int steps) {
        return (steps * CALORIES) / ONE_THOUSAND;
    }
}
