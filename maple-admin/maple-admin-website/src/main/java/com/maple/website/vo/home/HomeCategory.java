package com.maple.website.vo.home;

import lombok.Data;

import java.util.List;

/**
 * @author zhangfuzeng
 * @date 2023/12/26
 */
@Data
public class HomeCategory {

    private String heading;

    private String description;

    private List<HomeCategoryItem> items;
}
