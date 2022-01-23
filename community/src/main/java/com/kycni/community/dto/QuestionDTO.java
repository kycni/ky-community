package com.kycni.community.dto;

import com.kycni.community.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问题dto
 *
 * @author Kycni
 * @date 2022/1/18 8:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuestionDTO {
    /**
     * 前后端交流用，前端获取不到值检查这里!!
     * 将id Integer类型替换为Long类型，后前端获取不到值
     */
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
    
}
