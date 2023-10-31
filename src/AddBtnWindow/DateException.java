package AddBtnWindow;

public class DateException extends Exception{
    String message;
    DateException(String exception){
        message=exception;
    }

    @Override
    public String toString() {
        return message;
    }
}
