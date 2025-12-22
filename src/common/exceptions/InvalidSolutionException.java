package common.exceptions;

public class InvalidSolutionException extends Exception{
    public InvalidSolutionException(String message) {
        super(message);
    }
    public InvalidSolutionException(String message, Throwable cause) {
        super(message, cause);
    }

}
