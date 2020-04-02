package life.majiang.community.mapper;

import life.majiang.community.model.Comment;

/**
 * @Created by hfq on 2020/3/31 18:19
 * @used to:CommentMapper的拓展，完成更新回复数等功能
 * @return:
 */
public interface CommentExtMapper {
    int incCommentCount(Comment comment);

}
