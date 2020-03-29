package life.majiang.community.dto;

import lombok.Data;

/**
 * @Created by hfq on 2020/3/29 1:09
 * @used to: 回复内容要从前端接受到的数据结构
 * {
 *   "parentId": 16,
 *   "content":"回复内容测试",
 *   "type":1
 * }
 * @return:
 */
@Data
public class CommentDto {
    private Long parentId;
    private String content;
    private int type;
}
