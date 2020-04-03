package life.majiang.community.enums;

/**
 * @Created by hfq on 2020/4/3 0:59
 * @used to: 枚举，用于标识通知的类型，包含点赞、回复问题、评论回复
 * @return:
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    LIKE(3,"点赞了");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }


}
