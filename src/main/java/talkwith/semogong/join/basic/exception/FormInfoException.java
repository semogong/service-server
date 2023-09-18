package talkwith.semogong.join.basic.exception;

public class FormInfoException extends Exception{
    private String errorCode;

    public FormInfoException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
