package life.majiang.commuity.commuity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Created by hfq on 2020/3/7 23:16
 * @used to: 接受前端请求
 * @return:
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String Hello(@RequestParam(name = "name",defaultValue = "hfq",required = false) String name, Model model){
        model.addAttribute("name",name);
        return "hello";

    }
}
