package com.kycni.community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kycni
 * @date 2022/1/17 18:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    
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
    
}
