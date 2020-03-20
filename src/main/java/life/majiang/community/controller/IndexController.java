package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Created by hfq on 2020/3/7 23:16
 * @used to: 接受前端请求
 * @return:
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String Hello(){
        return "index";

    }
}
