package com.maple.system.vo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单信息
 *
 * @author 笑小枫
 * @date 2022/1/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaModel {

    private String title;

    private String icon;

    private String isLink;

    private Boolean isHide;

    private Boolean isKeepAlive;

    private Boolean isIframe;

    private Boolean isAffix;
}
