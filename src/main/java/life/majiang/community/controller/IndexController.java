package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Created by hfq on 2020/3/7 23:16
 * @used to: 接受前端请求
 * @return:
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping({"/","/index"})
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "tag",required = false) String tag){
        List<QuestionDto> list;
        double pageNum;   //分页的页面数
        if(tag!=null && !"null".equals(tag)){
            model.addAttribute("tag",tag);
            list = questionService.listByTag(tag,page,size);
            pageNum =  Math.ceil(questionExtMapper.selectByTagCount(tag)*1.0/size);
        }
        else {
            list = questionService.list(page, size);
            pageNum =  Math.ceil(questionMapper.countByExample(new QuestionExample())*1.0/size);
        }
        model.addAttribute("questions",list);
        model.addAttribute("pageNum",pageNum);
        return "/index";
    }


}
