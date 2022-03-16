package com.example.cananblog.mapper;

import com.example.cananblog.bean.Resource;
import com.example.cananblog.bean.Review;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceMapper {
    // 查询所有的资源
    List<Resource> queryResourceList();
}
