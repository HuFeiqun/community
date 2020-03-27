package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Created by hfq on 2020/3/20 20:37
 * @used to:
 * @return:
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;




    //点击首页“提问”按钮-新建问题
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    //如果url携带了问题的id，是需要修改问题,首先获取原问题回显到表单，然后提供修改
    @GetMapping("/publish/{id}")
    public String republish(@PathVariable(name = "id") Integer id,
                            Model model){
//        System.out.println(id);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(id);
        List<Question> questions = questionMapper.selectByExample(example);
        Question question = questions.get(0);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);
        return "publish";
    }




    //点击“发布问题”提交表单,如果原页面携带了id参数,说明是修改而不是添加问题
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title")String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag,
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "id", required = false) Integer id
            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title==""){
            model.addAttribute("error","问题标题不能为空!");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","问题补充不能为空!");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空!");
            return "publish";
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        if(id!=null)
        {
            question.setId(id);
        }
        questionService.createOrUpdate(question);
        return "redirect:profile/questions";
    }

}
