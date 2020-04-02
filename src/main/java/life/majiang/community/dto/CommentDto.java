package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @Created by hfq on 2020/3/30 3:20
 * @used to:查询问题时返回的Dto
 * @return:
 */
@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer commentCount;
    private String content;

    private User user;
}
