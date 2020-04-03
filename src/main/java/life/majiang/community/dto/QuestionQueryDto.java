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
    private String keyword;
    private Integer offset;
    private Integer size;

    public QuestionQueryDto(String tag, String keyword, Integer offset, Integer size) {
        this.tag = tag;
        this.keyword = keyword;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public String toString() {
        return "QuestionQueryDto{" +
                "tag='" + tag + '\'' +
                ", keyword='" + keyword + '\'' +
                ", offset=" + offset +
                ", size=" + size +
                '}';
    }
}
