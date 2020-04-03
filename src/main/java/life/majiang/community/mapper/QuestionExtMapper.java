package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionQueryDto;
import life.majiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);

    int incCommentCount(Question question);

    List<Question> selectRelatedQuestions(Question question);

    List<Question> selectByTag(QuestionQueryDto questionQueryDto);


    int selectByTagCount(String tag);  //查出包含此标签的记录数，分页时需要此参数
}