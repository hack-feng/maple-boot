package com.maple.website.vo.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangfuzeng
 * @date 2023/12/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeData {

    /**
     * 文章数量
     */
    private Long articleNum;

    /**
     * 链接数量
     */
    private Long linksNum;

    /**
     * 资源数量
     */
    private Long resourceNum;

    /**
     * 类目列表
     */
    private List<HomeCategory> homeCategoryList;
}
