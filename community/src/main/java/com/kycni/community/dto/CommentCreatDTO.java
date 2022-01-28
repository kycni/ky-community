package com.kycni.community.dto;

import lombok.Data;

/**
 * @author Kycni
 * @date 2022/1/22 17:24
 */
@Data
public class CommentCreatDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
}
