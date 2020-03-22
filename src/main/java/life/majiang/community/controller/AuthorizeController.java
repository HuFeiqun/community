package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDto;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Created by hfq on 2020/3/17 17:38
 * @used to:
 * @return:
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

//    @Value("${github.client.id}")  //到配置文件中读取
    private String clientId="2c2a7501d5c6e9d1fbeb";
//    @Value("${github.client.secret}")
    private String clientSecret="ee0990ffb7926c74a32291b8ccb9041027fb1e05";
//    @Value("${github.redirect.uri}")
    private String clientUri="http://localhost:8887/callback";

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDto accessTokenDTO = new AccessTokenDto();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(user.getId());
        if(githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());  //系统当前时间毫秒数
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));

            //登录成功，写cookie和session
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";  //重定向
        }
        else{//登录失败，重新登录
            return "redirect:/";
        }
    }
}
