package life.majiang.community.service;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by hfq on 2020/3/22 11:13
 * @used to:获取问题+用户信息（如头像url）
 * @return:
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //获取问题dto（除了问题表的字段外还包含了用户表的信息）集合
    public List<QuestionDto> list(Integer page,Integer size){
        int offset = (page-1)*size;
        List<QuestionDto> questionDtos = new ArrayList<>();
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        for (Question question:questions){
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

    public List<QuestionDto> listMyQuestion(Integer id,Integer page,Integer size){
        int offset = (page-1)*size;
        List<QuestionDto> questionDtos = new ArrayList<>();
        QuestionExample example = new QuestionExample();
        //获取
        example.createCriteria().
                andCreatorEqualTo(id);
        example.setOrderByClause("gmt_modified desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        for (Question question:questions){
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

    public void createOrUpdate(Question question){
        if(question.getId()!=null){ //说明数据库存在这条记录，更新问题内容和修改时间
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            List<Question> questions = questionMapper.selectByExample(example);
            Question dbQuestion = questions.get(0);
            dbQuestion.setGmtModified(System.currentTimeMillis());
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setDescription(question.getDescription());
            dbQuestion.setGmtCreate(dbQuestion.getGmtCreate());
            dbQuestion.setViewCount(dbQuestion.getViewCount());
            dbQuestion.setLikeCount(dbQuestion.getLikeCount());
            dbQuestion.setCommentCount(dbQuestion.getCommentCount());
            dbQuestion.setTag(question.getTag());
            questionMapper.updateByPrimaryKey(dbQuestion);
        }else { //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }

    }
}