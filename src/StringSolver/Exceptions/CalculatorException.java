package StringSolver.Exceptions;

/**
 * Базовый класс ошибки, пробрасываемой калькулятором выражения
 */
public class CalculatorException extends Exception {

    public String message;

    public CalculatorException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
