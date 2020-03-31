package life.majiang.community.exception;

/**
 * @Created by hfq on 2020/3/28 0:37
 * @used to:
 * @return:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"此问题不存在，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"回复的问题或评论不存在！"),
    NOT_LOGIN(2003,"未登录,无法操作!"),
    SYSTEM_ERROR(2004,"服务端异常,请稍后再试!!!"),
    TYPE_PARAM_NOT_FOUND(2005,"回复类型有误！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在!"),
    COMMENT_IS_EMPTY(2006,"回复内容不能为空！"),



    NOT_MY_QUESTION(2999,"这不是你发布的问题，无法编辑！");



    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
