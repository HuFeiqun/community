package life.majiang.community.dto;

import lombok.Data;

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
}
