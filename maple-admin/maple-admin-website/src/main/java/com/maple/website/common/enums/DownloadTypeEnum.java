package com.maple.website.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangfuzeng
 * @date 2024/7/17
 */
@AllArgsConstructor
@Getter
public enum DownloadTypeEnum {

    /**
     * 下载类型
     */
    LOCAL(1, "本地"),
    LINK(2, "链接"),
    NET(3, "网络"),
    ;

    private final int code;

    private final String msg;
}
