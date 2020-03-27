package life.majiang.community.exception;

/**
 * @Created by hfq on 2020/3/28 0:10
 * @used to:
 * @return:
 */
public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message=errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
