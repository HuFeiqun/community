package life.majiang.community.enums;

/**
 * @Created by hfq on 2020/4/3 1:17
 * @used to:枚举，用于标识通知的状态，已读1还是未读0
 * @return:
 */
public enum NotificationStatusEnum {
    NOT_READ(0,"未读"),
    READ(1,"已读");

    private int status;
    private String desc;

    NotificationStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}