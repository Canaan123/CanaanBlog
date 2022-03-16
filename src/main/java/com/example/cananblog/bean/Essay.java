package com.example.cananblog.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.lang.annotation.Documented;

@ApiModel("文章实体类")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "esssay",shards = 3,replicas = 1)
public class Essay implements Serializable {
    @Id
    private long essayid;
    @Field(type = FieldType.Text)
    private String essaytitle;
    @Field(type = FieldType.Text,searchAnalyzer = "ik_max_word")
    private String essaycontent;
    @Field(type = FieldType.Text,searchAnalyzer = "ik_max_word")
    private String essayauthor;
    @Field(type = FieldType.Text)
    private String label;
    @Field(type = FieldType.Text)
    private String updatetime;
    @Field(type = FieldType.Text)
    private String original;
    @Field(type = FieldType.Long)
    private long visits;
    @Field(type = FieldType.Text)
    private String essaypicture;
    @Field(type = FieldType.Text)
    private String essaydescribe;
    private long likenumber;

}
