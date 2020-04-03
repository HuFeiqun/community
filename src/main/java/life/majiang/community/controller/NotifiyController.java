package life.majiang.community.controller;

import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.exception.ICustomizeErrorCode;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created by hfq on 2020/4/3 2:50
 * @used to:
 * @return:
 */
@Controller
public class NotifiyController {
    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping("/notification/{notificationId}/{questionId}")
    public String notifiy(@PathVariable(name = "notificationId") Long notificationId,
                          @PathVariable(name = "questionId") Long questionId,
                          HttpServletRequest request
                          ){
        //更新通知的读取状态
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new CustomizeException(CustomizeErrorCode.NOT_LOGIN);
        }
        Notification dbNotification = notificationMapper.selectByPrimaryKey(notificationId);
        if(dbNotification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.NOT_YOUR_NOTIFICATION);
        }
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        return "redirect:/question/"+questionId;
    }
}
