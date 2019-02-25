package StringSolver.Exceptions;

public class CalculatorException extends Exception {
    public String message;

    public CalculatorException(String message){
        this.message = message;
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage(){
        return message;
    }
}
