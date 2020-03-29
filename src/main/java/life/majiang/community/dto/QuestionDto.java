package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @Created by hfq on 2020/3/22 11:08
 * @used to:
 * @return:
 */
@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String tag;
    private User user;
}
