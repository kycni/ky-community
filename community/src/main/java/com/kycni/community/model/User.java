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
@Table(name = "`USER`")
public class User {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`ACCOUNT_ID`")
    private String accountId;

    @Column(name = "`NAME`")
    private String name;

    @Column(name = "`BIO`")
    private String bio;

    @Column(name = "`AVATAR_URL`")
    private String avatarUrl;

    @Column(name = "`TOKEN`")
    private String token;

    @Column(name = "`GMT_CREATE`")
    private Long gmtCreate;

    @Column(name = "`GMT_MODIFIED`")
    private Long gmtModified;

    @Column(name = "`SOURCE`")
    private String source;
}