package life.majiang.community.enums;

/**
 * @Created by hfq on 2020/3/29 15:05
 * @used to: 区分是一级回复（回复【问题】）还是二级回复（回复【回复】）
 * @return:
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }}
