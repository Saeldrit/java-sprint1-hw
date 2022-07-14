public class Converter {

    public double calculateStepsToKm(int steps) {
        return (steps * 75.0) / 10_000;
    }

    public double calculateKkal(int steps) {
        return (steps * 50.0) / 1000;
    }
}
