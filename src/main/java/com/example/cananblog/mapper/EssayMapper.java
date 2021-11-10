package com.example.cananblog.mapper;

import com.example.cananblog.bean.Essay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    // 查询整个文章
    List<Essay> queryEssayList();
    // 添加一个文章
    void addEssay(Essay essay);
    // 通过id删除对应文章
    void deleteEssayById(long id);
    // 查询对应id的文章
    Essay queryEssayById(long id);
    // 修改对应id的文章
    void modifyEssayById(Essay essay);
    // 查询文章的数量
    long queryEssayNumber();
    // 查找最后一个id
    long queryLastId();
    // 自增访问量
    void increaseVisits(long id);
    // 查询文章总阅读量
    long queryVisits();
    // 找到含有对应标签的文章
    List<Essay> queryEssayContainLabel(String label);
    // 查询访客量前6的文章
    List<Essay> queryhotEssay();
    // 查询文章原创数量
    long queryOriginalNumber();
    // 对查询的结果进行匹配
    List<Essay> queryMatchingEssay(String text);
}
