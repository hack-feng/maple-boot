package com.maple.website.vo.home;

import lombok.Data;

/**
 * @author zhangfuzeng
 * @date 2023/12/26
 */
@Data
public class HomeCategoryItem {
    
    private Long id;

    private String image;

    private String title;

    private String subtitle;

    private String route;

    private String description;
}
