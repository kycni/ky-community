package com.kycni.community.dto;

import com.kycni.community.model.User;
import lombok.Data;

/**
 * @author Kycni
 * @date 2022/1/22 17:24
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private User user; 
}
