package com.example.cananblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface InformationMapper {
    // 查询博主名字
    String queryBloggerName();
    // 查询博客描述
    String queryBlogDescribe();
    // 查询文章临时内容
    String queryEssayContentTemp();
    // 查询文章临时标题
    String queryEssayTitleTemp();
    // 查询文章临时描述
    String queryEssayDescribeTemp();

    // 修改博主名称
    void updateBloggerName(String bloggername);
    // 修改博客描述
    void updateBlogDescribe(String blogdescribe);
    // 修改临时文章内容
    void modifyEssayContentTemp(String essaycontent);
    // 修改临时文章标题
    void modifyEssayTitleTemp(String essaytitle);
    // 修改临时文章描述
    void modifyEssayDescribeTemp(String essaydescribe);

    // 删除临时文章记录
    void deleteEssayTemp();
}
