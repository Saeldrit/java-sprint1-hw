/***
 * Постановка задачи:
 * - Консольный интерфейс для управления программой;
 * - Хранение данных о количестве пройденных шагов за несколько месяцев;
 * - Ввод вашей цели по количеству шагов в день;
 * - Ввод пройденного количества шагов за день;
 * - Вывод статистики за определённый месяц.
 * @author Alexey.Pavlovskiy
 * @version 3.0 от 17.07.2022
 */

public class Main {
    public static void main(String[] args) {
        StepTracker stepTracker = new StepTracker();
        Menu menu = new Menu(stepTracker);

        menu.input();
    }
}
