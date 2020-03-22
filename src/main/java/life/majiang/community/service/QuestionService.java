package life.majiang.community.service;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public List<QuestionDto> list(HttpServletRequest request){
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> list = questionMapper.list();
        for (Question question:list){
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser((User) request.getSession().getAttribute("user"));
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }
}
