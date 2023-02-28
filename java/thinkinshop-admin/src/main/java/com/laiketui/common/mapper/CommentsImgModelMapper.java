package com.laiketui.common.mapper;

import com.laiketui.domain.product.CommentsImgModel;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评论图片记录表
 *
 * @author Trick
 * @date 2021/10/21 17:47
 */
public interface CommentsImgModelMapper extends BaseMapper<CommentsImgModel> {

    @Select("select comments_url from lkt_comments_img where comments_id=#{commentsId}")
    List<String> getCommentsImages(int commentsId);

}