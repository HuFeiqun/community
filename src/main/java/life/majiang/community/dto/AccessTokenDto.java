package life.majiang.community.dto;

import lombok.Data;

/**
 * @Created by hfq on 2020/3/17 17:53
 * @used to: 将参数封装成对象
 * @return:
 */
@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}


