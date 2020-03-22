package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title",required = false)String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "tag") String tag,
            HttpServletRequest request,
            Model model){
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

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        User user=new User();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                }
            }
        }
//        User user = (User) request.getSession().getAttribute("user"); 只有进入index后才会设置session，如果重启服务器用户直接进入publish就获得不到session，所以还是从cookies获取用户信息比较合理
        if(user==null){
            model.addAttribute("error","用户未登录");
        }
        else {
            System.out.println(user.toString());
        }
        question.setCreator(user.getId());
        questionMapper.insert(question);
        return "publish";
    }
}
