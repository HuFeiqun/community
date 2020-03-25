package life.majiang.community.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Created by hfq on 2020/3/17 22:33
 * @used to:
 * @return:
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }
}
