package exceptions;


public class InvalidMenuItem extends Exception{

    public InvalidMenuItem(String message) {
        super(message);
    }
}