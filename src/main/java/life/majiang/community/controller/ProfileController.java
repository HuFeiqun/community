package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDto;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Created by hfq on 2020/3/25 15:19
 * @used to:
 * @return:
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{section}")
    public String profile(HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model,
                          @PathVariable(name = "section") String section){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
            return "redirect:/";
        model.addAttribute("sectionName",section);

        if("questions".equals(section)){  //我的问题页
            model.addAttribute("sectionTitle","我的提问");
            List<QuestionDto> questionDtos = questionService.listMyQuestion(user.getId(), page, size);
            model.addAttribute("questions",questionDtos);
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andCreatorEqualTo(user.getId());
            int myQuestionCount = (int) questionMapper.countByExample(questionExample);
            double pageNum =  Math.ceil(myQuestionCount*1.0/size); //根据questionNum算出页面数
            model.addAttribute("pageNum",pageNum);

        }
        else if("replies".equals(section)){  //查数据库跟自己相关的通知
            model.addAttribute("sectionTitle","最新回复");
            List<NotificationDto> notificationDtos = notificationService.listMyNotifications(user.getId(), page, size);
            model.addAttribute("notifications",notificationDtos);
            int myNotificationsCount = notificationService.countMyNotifications(user.getId());
            double pageNum =  Math.ceil(myNotificationsCount*1.0/size); //算出页面数
            model.addAttribute("pageNum",pageNum);
        }
        return "profile";

    }
}
