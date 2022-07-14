public enum Day {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String title;

    Day(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
