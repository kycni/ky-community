package com.kycni.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kycni
 * @date 2022/1/15 18:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private String name;
    private String uuid;
    private String remark;
    private String source;
}
