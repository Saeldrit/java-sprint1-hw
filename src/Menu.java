import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner;
    private static final List<String> menu;
    private final StepTracker stepTracker;

    static {
        scanner = new Scanner(System.in);
        menu = new ArrayList<>();
        initializationMenu();
    }

    public Menu(StepTracker stepTracker) {
        this.stepTracker = stepTracker;
    }

    public List<String> getMenu() {
        return menu;
    }

    public static void setMenu(String menuItem) {
        menu.add(menuItem);
    }

    private static void initializationMenu() {
        setMenu("1 - Общее количество пройденных шагов за месяц");
        setMenu("2 - Максимальное количество пройденных шагов за месяц");
        setMenu("3 - Результат за день");
        setMenu("4 - Ввод цели по количеству шагов в день");
        setMenu("5 - Сохранить результаты");
        setMenu("6 - Лучшая серия за период");
        setMenu("7 - Ккал за день");
        setMenu("8 - Сколько км я прошел");
        setMenu("9 - Вывод статистики за месяц");
        setMenu("0 - Выход");
    }

    public void input() {
        getMenu().forEach(System.out::println);

        int userInput = scanner.nextInt();

        while (userInput != 0) {
            processingMenuItems(userInput);
            getMenu().forEach(System.out::println);
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");
    }

    private void processingMenuItems(int item) {
        switch (item) {
            case 1:
                getResultByMonth();
                break;
            case 2:
                lookForMaximumSteps();
                break;
            case 3:
                getResultByDay();
                break;
            case 4:
                enterGoalForDay();
                break;
            case 5:
                saveResultSteps();
                break;
            case 6:
                findOutTheBestSeries();
                break;
            case 7:
                calculateCalories();
                break;
            case 8:
                calculateToKm();
                break;
            case 9:
                outputStatisticsByMonth();
                break;
            default:
                System.err.println("Мы только познакомились, " +
                        "а ты уже вводишь некорретные данные? Ай-я-яй...");
        }
    }

    private void getResultByMonth() {
        int month = returnItemOfMonth();
        int result = stepTracker.getTotalStepsByMonth(month);

        System.out.println("Общий результат за месяц "
                + Month.values()[month].getTitle()
                + ": " + result + " шагов\n");
    }

    private void lookForMaximumSteps() {
        int month = returnItemOfMonth();
        int result = stepTracker.lookForMaximumStepsByMonth(month);

        System.out.println("Максимальное количество шагов за месяц "
                + Month.values()[month].getTitle()
                + ": " + result + " шагов\n");
    }

    private int returnItemOfMonth() {
        System.out.println("\nВыберите месяц\n");

        for (int i = 1; i <= Month.values().length; i++) {
            System.out.print(i + " - " + Month.values()[i - 1].getTitle() + " ");
            if (i % 3 == 0) {
                System.out.println();
            }
        }

        return checkInputMonth() - 1;
    }

    private int returnItemOfDay() {
        System.out.println("Введите день от 1 до 30");
        return checkInputDay();
    }

    private void getResultByDay() {
        int month = returnItemOfMonth();
        int day = returnItemOfDay();
        int result = stepTracker.lookForResultByDay(month, day);

        System.out.println("Результат за месяц " +
                Month.values()[month].getTitle() +
                "\n" + day + " день: " + result + " шагов");
    }

    private void enterGoalForDay() {
        System.out.print("Задайте новую цель по количеству шагов: ");
        int userGoal = checkInputSteps();

        stepTracker.setTargetNumberOfSteps(userGoal);
        System.out.println("Целевое количество шагов - " +
                stepTracker.getTargetNumberOfSteps() + "\n");
    }

    private void saveResultSteps() {
        int month = returnItemOfMonth();
        int day = returnItemOfDay();

        System.out.println("Укажите результат:");
        int result = checkInputSteps();

        stepTracker.saveResult(month, day, result);
    }

    private void findOutTheBestSeries() {
        int month = returnItemOfMonth();
        var result = stepTracker.findOutTheBestResult(month);

        System.out.println("Лучшая серия за месяц " + Month.values()[month].getTitle());
        for (var key : result.keySet()) {
            System.out.println("День " + key +
                    ", шагов " + result.get(key));
        }
    }

    private void calculateCalories() {
        int month = returnItemOfMonth();
        int day = returnItemOfDay();
        double result = stepTracker.calculateStepsToCalories(month, day);

        System.out.println("Калорий потрачено " + result);
    }

    private void calculateToKm() {
        int month = returnItemOfMonth();
        int day = returnItemOfDay();
        double result = stepTracker.calculateStepsToKmForDay(month, day);

        System.out.println("Всего пройдено " + result);
    }

    private void outputStatisticsByMonth() {
        int month = returnItemOfMonth();
        int[] statisticsByMonth = stepTracker.getStatisticsByMonth(month);
        var monthValue = Month.values()[month].getTitle();
        var result = stepTracker.findOutTheBestResult(month);

        System.out.println("\nРезультат за месяц " +
                monthValue + " по дням\n");

        for (int i = 1; i <= statisticsByMonth.length; i++) {
            System.out.print(i + " день - " + statisticsByMonth[i - 1] + "; ");
            if (i % 5 == 0) {
                System.out.println();
            }
        }

        System.out.println("Общее количество шагов за " + monthValue +
                ": " + stepTracker.getTotalStepsByMonth(month));

        System.out.println("Максимально пройденное количество шагов за " + monthValue +
                ": " + stepTracker.lookForMaximumStepsByMonth(month));

        System.out.println("Среднее количество шагов за " + monthValue +
                ": " + stepTracker.calculateAverage(month));

        System.out.println("Пройденная дистанция в км за " + monthValue +
                ": " + stepTracker.calculateStepsToKmForMonth(month));

        System.out.println("Количество соженных калорий за " + monthValue +
                ": " + stepTracker.calculateStepsToCalForMonth(month));

        System.out.println("Лучшая серия за месяц " + monthValue);
        for (var key : result.keySet()) {
            System.out.println("День " + key +
                    ", шагов " + result.get(key));
        }
    }

    /**
     * Я не стал выбрасывать исключение, пока остановлюсь на простом выводе сообщения
     */
    private int checkInputSteps() {
        int userInput = scanner.nextInt();

        while (userInput <= 0) {
            if (userInput == 0) {
                System.err.println("Нулям, чтоб обрести силу, надо оказаться позади другого числа.");
            } else {
                System.err.println("«Оставайся в трудном положении, когда должен». — Йода");
            }
            userInput = scanner.nextInt();
        }

        return userInput;
    }

    private int checkInputDay() {
        int day = scanner.nextInt();
        int dayLength = stepTracker.getDayLength();

        while (day > dayLength ||
                day <= 0) {
            System.err.println("Мы тут все расстроились, " +
                    "но в жизни робота только 30 дней, от 1 до 30");
            day = scanner.nextInt();
        }
        return day;
    }

    private int checkInputMonth() {
        int idMonth = scanner.nextInt();
        int monthLength = Month.values().length;

        while (idMonth > monthLength ||
                idMonth <= 0) {
            System.err.println("Уппсс...но на планете Земля пока 12 месяцев, от 1 до 12");
            idMonth = scanner.nextInt();
        }
        return idMonth;
    }
}
