package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDto;
import life.majiang.community.dto.CommentDto;
import life.majiang.community.dto.ResultDto;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Created by hfq on 2020/3/29 1:02
 * @used to:e
 * @return:
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //用于提交回复（包含一级回复和二级回复）

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            return ResultDto.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(StringUtils.isAllBlank(commentCreateDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDto.getContent());
        comment.setParentId(commentCreateDto.getParentId());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        commentService.insertSelective(comment);
        return ResultDto.okOf();
    }

    //在问题页面的回复详情页 点击“评论”图标（查询二级回复）
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Long commentId){
        List<CommentDto> commentDtos = commentService.listByParentId(commentId, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }
}
