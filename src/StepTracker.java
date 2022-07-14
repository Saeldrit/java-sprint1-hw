import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StepTracker {

    private final MonthData[] monthData = new MonthData[12];
    private static final Converter converter = new Converter();
    private int targetNumberOfSteps;

    public StepTracker() {
        Arrays.fill(monthData, new MonthData());
        targetNumberOfSteps = 10_000;
    }

    public int getTargetNumberOfSteps() {
        return targetNumberOfSteps;
    }

    public void setTargetNumberOfSteps(int userGoal) {
        this.targetNumberOfSteps = userGoal;
    }

    public int getTotalStepsByMonth(int month) {
        int totalSteps = 0;
        int length = monthData[month].getArray().length;

        for (int i = 0; i < length; i++) {
            totalSteps += monthData[month].getArray()[i];
        }

        return totalSteps;
    }

    public int lookForMaximumStepsByMonth(int month) {
        int maximum = 0;
        int length = monthData[month].getArray().length;

        for (int i = 0; i < length; i++) {
            int steps = monthData[month].getArray()[i];
            if (maximum < steps) {
                maximum = steps;
            }
        }

        return maximum;
    }

    public void saveResult(int month, int day, int stepsResult) {
        monthData[month].getArray()[day] = stepsResult;
    }

    public int[] getStatisticsByMonth(int month) {
        return monthData[month].getArray();
    }

    public int lookForResultByDay(int month, int day) {
        return monthData[month].getArray()[day];
    }

    public Map<Integer, Integer> findOutTheBestResult(int month) {
        int[] chain = monthData[month].getArray();
        int tempIndex = 0;
        int firstIndex = 0;
        int sequence = 0;
        int maxSequence = 0;

        for (int i = 0; i < chain.length; i++) {
            if (chain[i] >= targetNumberOfSteps) {
                if (sequence == 0) {
                    tempIndex = i;
                }
                sequence++;
            } else if (chain[i] < targetNumberOfSteps) {
                if (maxSequence < sequence) {
                    firstIndex = tempIndex;
                }
                maxSequence = Math.max(maxSequence, sequence);
                sequence = 0;
            }
            if (i == chain.length - 1) {
                maxSequence = Math.max(maxSequence, sequence);
            }
        }

        return copyOfRangeFromTo(chain, firstIndex, maxSequence);
    }

    private Map<Integer, Integer> copyOfRangeFromTo(int[] array, int start, int length) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = start; i < start + length; i++) {
            map.put(i, array[i]);
        }

        return map;
    }

    public double calculateStepsToCalories(int month, int day) {
        int steps = monthData[month].getArray()[day];

        return converter.calculateKkal(steps);
    }

    public double calculateStepsToKm(int month, int day) {
        int steps = monthData[month].getArray()[day];

        return converter.calculateStepsToKm(steps);
    }

    private static class MonthData {
        private final int[] array = new int[30];

        private int[] getArray() {
            return array;
        }

        @Override
        public String toString() {
            return "MonthData{" +
                    "array=" + Arrays.toString(array) +
                    '}';
        }
    }
}
