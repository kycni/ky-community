package com.kycni.community.dto;

import lombok.Data;

/**
 * @author Kycni
 * @date 2022/1/23 14:17
 */
@Data
public class CommentCreateDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
}
