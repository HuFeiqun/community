package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.dto.QuestionQueryDto;
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
    private QuestionService questionService;
    @GetMapping({"/","/index"})
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "tag",defaultValue = "null") String tag,
                        @RequestParam(name = "keyword",defaultValue = "null") String serachKeyWord
                        ){
        int offset = (page-1)*size;   //分页查询的偏移量
        QuestionQueryDto questionQueryDto = new QuestionQueryDto(tag,serachKeyWord,offset,size);
//        System.out.println(questionQueryDto.toString());
        List<QuestionDto> questionDtos = questionService.selectByQueryDto(questionQueryDto);
        int pageNum = (int) Math.ceil(questionService.CountByQueryDto(questionQueryDto)*1.0/size);   //分页的页数
        model.addAttribute("questions",questionDtos);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("tag",tag);
        model.addAttribute("keyword",serachKeyWord);
        return "/index";
    }
}
