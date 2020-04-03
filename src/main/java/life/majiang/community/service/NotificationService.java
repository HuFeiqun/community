package life.majiang.community.service;

import life.majiang.community.dto.NotificationDto;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by hfq on 2020/4/3 1:54
 * @used to:
 * @return:
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    public List<NotificationDto> listMyNotifications(Long receiver, Integer page, Integer size) {
        int offset = (page-1)*size;
        List <NotificationDto> notificationDtos = new ArrayList<>();
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiver);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            User notifier = userMapper.selectByPrimaryKey(notification.getNotifier());
            notificationDto.setNotifier(notifier);
            if(notification.getType()==NotificationTypeEnum.REPLY_QUESTION.getType()){  //回复的是问题，数据库中的outId就是问题ID，无需处理
                notificationDto.setNotifiyType(NotificationTypeEnum.REPLY_QUESTION.getName());
                Question outerQuestion = questionMapper.selectByPrimaryKey(notification.getOuterId());
                notificationDto.setOuterTitle(outerQuestion.getTitle());
            }
            else if(notification.getType()==NotificationTypeEnum.REPLY_COMMENT.getType()){
                notificationDto.setNotifiyType(NotificationTypeEnum.REPLY_COMMENT.getName());
                Comment comment = commentMapper.selectByPrimaryKey(notification.getOuterId());  //得到的是所评论的一级回复
                Question outerQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
                notificationDto.setOuterTitle(outerQuestion.getTitle());
                notificationDto.setOuterId(outerQuestion.getId());                //回复的是评论，数据库中的outId就是一级回复的ID，需获得其parentId才是问题ID
            }
            else if(notification.getType()==NotificationTypeEnum.LIKE.getType()){
                notificationDto.setNotifiyType(NotificationTypeEnum.LIKE.getName());
                //待完成的业务
            }
            else
            {
                //抛异常
            }
            notificationDtos.add(notificationDto);
        }


        return notificationDtos;
    }

    public int countMyNotifications(Long receiverId) {//获取某用户的所有通知的数目（用于分页）
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiverId);
        return (int) notificationMapper.countByExample(example);
    }

    public int NotReadCount(Long receiverId) { //获取某用户未读消息数目
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(receiverId)
                .andStatusEqualTo(NotificationStatusEnum.NOT_READ.getStatus());
        return (int) notificationMapper.countByExample(example);
    }

}
