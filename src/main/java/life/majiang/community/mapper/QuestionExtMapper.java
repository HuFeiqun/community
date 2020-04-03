package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionQueryDto;
import life.majiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);

    int incCommentCount(Question question);

    List<Question> selectRelatedQuestions(Question question);

    List<Question> selectByQueryDto(QuestionQueryDto questionQueryDto);

    int countByQueryDto(QuestionQueryDto questionQueryDto);
}