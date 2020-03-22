package life.majiang.community.model;

import lombok.Data;

/**
 * @Created by hfq on 2020/3/19 23:48
 * @used to:
 * @return:
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}