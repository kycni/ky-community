package com.kycni.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/24 22:32
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}