package life.majiang.community.exception;

/**
 * @Created by hfq on 2020/3/28 0:37
 * @used to:
 * @return:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("此问题不存在，要不要换个试试？"),
    NOT_MY_QUESTION("这不是你发布的问题，无法编辑！");


    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }

}
