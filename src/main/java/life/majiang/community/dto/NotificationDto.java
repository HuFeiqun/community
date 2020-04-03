package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

/**
 * @Created by hfq on 2020/4/3 1:39
 * @used to: 通知示例 ： 然后就 回复了问题 怎么学SprintBoot?
 * @return:
 */
@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long outerId;

    private User notifier;
    private String notifiyType;
    private String outerTitle;    //要跳转到的问题的链接

}
