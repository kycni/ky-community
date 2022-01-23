package com.kycni.community.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "`QUESTION`")
public class Question {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`TITLE`")
    private String title;

    @Column(name = "`GMT_CREATE`")
    private Long gmtCreate;

    @Column(name = "`GMT_MODIFIED`")
    private Long gmtModified;

    @Column(name = "`CREATOR`")
    private Long creator;

    @Column(name = "`COMMENT_COUNT`")
    private Integer commentCount;

    @Column(name = "`VIEW_COUNT`")
    private Integer viewCount;

    @Column(name = "`LIKE_COUNT`")
    private Integer likeCount;

    @Column(name = "`TAG`")
    private String tag;

    @Column(name = "`DESCRIPTION`")
    private String description;
}