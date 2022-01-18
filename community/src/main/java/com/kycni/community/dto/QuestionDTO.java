package com.kycni.community.dto;

import com.kycni.community.model.User;
import lombok.Data;

/**
 * @author Kycni
 * @date 2022/1/18 8:15
 */
@Data
public class QuestionDTO {
    
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
    
}
