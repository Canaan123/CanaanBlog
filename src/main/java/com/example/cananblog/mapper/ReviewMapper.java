package com.example.cananblog.mapper;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Message;
import com.example.cananblog.bean.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewMapper {
    // 查询全部评论
    List<Review> queryReviewList();
    // 通过id删除对应的评论
    void deleteReviewById(long id);
    // 通过id修改对应的评论内容
    void modifyReviewContentById(@Param("id") long id, @Param("content") String content);
    // 查询评论数量
    long queryReviewNumber();

    // 添加一条留言
    void addMessage(Message message);
    // 查询全部留言
    List<Message> queryMessageList();
    // 添加一条私信
    void addPrivateMessage(Message message);
}
