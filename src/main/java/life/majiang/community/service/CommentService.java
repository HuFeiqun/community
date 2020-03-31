package life.majiang.community.service;

import life.majiang.community.dto.CommentDto;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by hfq on 2020/3/29 15:09
 * @used to:
 * @return:
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insertSelective(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }



        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){ //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
        else { //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);    //更新回复数
        }
    }

    /**
     * 获取指定问题或者评论的所有回复记录
     * @param id
     * @param type
     * @return
     */
    public List<CommentDto> listByParentId(Long id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_modified desc");
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(example);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //Java8新语法 lanbda
//        comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        List <CommentDto> commentDtoList = new ArrayList<CommentDto>();
        for (Comment comment:comments) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            User user = userMapper.selectByPrimaryKey(commentDto.getCommentator());
            commentDto.setUser(user);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;

    }
}
