package life.majiang.community.controller;

import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created by hfq on 2020/3/25 19:10
 * @used to:
 * @return:
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") int id,
                           Model model,
                           HttpServletRequest request){
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        if(question.getCreator()==user.getId()){
            model.addAttribute("question",question);
        }
        return "question";
    }
}
