package life.majiang.commuity.commuity.controller;

import life.majiang.commuity.commuity.dto.AccessTokenDTO;
import life.majiang.commuity.commuity.dto.GithubUser;
import life.majiang.commuity.commuity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getId());
        return "index";
    }
}
