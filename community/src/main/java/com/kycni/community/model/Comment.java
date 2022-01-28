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
@Table(name = "`COMMENT`")
public class Comment {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`PARENT_ID`")
    private Long parentId;

    @Column(name = "`TYPE`")
    private Integer type;

    @Column(name = "`CONTENT`")
    private String content;

    @Column(name = "`COMMENTATOR`")
    private Long commentator;

    @Column(name = "`GMT_CREATE`")
    private Long gmtCreate;

    @Column(name = "`GMT_MODIFIED`")
    private Long gmtModified;

    @Column(name = "`LIKE_COUNT`")
    private Long likeCount;

    @Column(name = "`COMMENT_COUNT`")
    private Integer commentCount;
}