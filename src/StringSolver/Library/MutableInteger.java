package StringSolver.Library;

/**
 * Обертка над базовым целочисленным типом,
 * значение которого может меняться в ходе выполнения программы
 * TODO: отрефакторить до объекта Integer
 */
public class MutableInteger {
    private int value;
    public MutableInteger(int value) {
        this.value = value;
    }
    public void set(int value) {
        this.value = value;
    }
    public int intValue() {
        return value;
    }
    public void increment() {
        this.value = this.value + 1;
    }
    public void decrement() {
        this.value = this.value - 1;
    }
}
