import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final StepTracker stepTracker;

    public Menu(StepTracker stepTracker) {
        this.stepTracker = stepTracker;
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);
        printMenu().forEach(System.out::println);

        int userInput = scanner.nextInt();

        while (userInput != 0) {
            processingMenuItems(userInput);
            printMenu().forEach(System.out::println);
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");
    }

    private List<String> printMenu() {
        List<String> menu = new ArrayList<>();
        menu.add("1 - Общее количество пройденных шагов за месяц");
        menu.add("2 - Максимальное количество пройденных шагов за месяц");
        menu.add("3 - Результат за день");
        menu.add("4 - Ввод цели по количеству шагов в день");
        menu.add("5 - Вывод статистики за месяц");
        menu.add("6 - Сохранить результаты");
        menu.add("7 - Лучшая серия за период");
        menu.add("8 - Ккал за день");
        menu.add("9 - Сколько км я прошел");
        menu.add("0 - Выход");

        return menu;
    }

    private void processingMenuItems(int item) {
        if (!checkCorrectOfTheInput(item)) {
            System.err.println("Incorrect Input");
        }

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
                outputStatisticsByMonth();
                break;
            case 6:
                saveResultSteps();
                break;
            case 7:
                findOutTheBestSeries();
                break;
            case 8:
                calculateCalories();
                break;
            case 9:
                calculateToKm();
                break;
            default:
                break;
        }
    }

    private boolean checkCorrectOfTheInput(int item) {
        int sizeOfMenu = printMenu().size();
        return item <= sizeOfMenu - 1 && item >= 0;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВыберите месяц\n");

        for (int i = 1; i <= Month.values().length; i++) {
            System.out.print(i + " - " + Month.values()[i - 1].getTitle() + " ");
            if (i % 3 == 0) {
                System.out.println();
            }
        }

        return scanner.nextInt() - 1;
    }

    private int returnItemOfDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите день от 1 до 30");
        return scanner.nextInt();
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
        int userGoal = checkNumber();

        stepTracker.setTargetNumberOfSteps(userGoal);
        System.out.println("Целевое количество шагов - " +
                stepTracker.getTargetNumberOfSteps() + "\n");
    }

    private void outputStatisticsByMonth() {
        int month = returnItemOfMonth();
        int[] statisticsByMonth = stepTracker.getStatisticsByMonth(month);

        System.out.println("\nРезультат за месяц " +
                Month.values()[month].getTitle() + "\n");

        for (int i = 1; i <= statisticsByMonth.length; i++) {
            System.out.print(i + " день - " + statisticsByMonth[i - 1] + "; ");
            if (i % 5 == 0) {
                System.out.println();
            }
        }
    }

    private void saveResultSteps() {
        int month = returnItemOfMonth();
        int day = returnItemOfDay();

        System.out.println("Укажите результат:");
        int result = checkNumber();

        stepTracker.saveResult(month, day, result);
    }

    private int checkNumber() {
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();

        if (userInput % 2 != 0 || userInput < 0) {
            System.out.println("Введите четное количество шагов");
            userInput = scanner.nextInt();
        }

        return userInput;
    }

    private void findOutTheBestSeries() {
        int month = returnItemOfMonth();
        Map<Integer, Integer> result = stepTracker.findOutTheBestResult(month);

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
        double result = stepTracker.calculateStepsToKm(month, day);

        System.out.println("Всего пройдено " + result);
    }
}
