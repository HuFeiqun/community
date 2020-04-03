package life.majiang.community.dto;

import lombok.Data;

/**
 * @Created by hfq on 2020/4/3 15:55
 * @used to: 封装主页查询问题的参数，如标签
 * @return:
 */
@Data
public class QuestionQueryDto {
    private String tag;
    private Integer offset;
    private Integer size;
}
