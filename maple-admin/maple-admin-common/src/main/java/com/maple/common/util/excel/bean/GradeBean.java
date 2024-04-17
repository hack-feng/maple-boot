package com.maple.common.util.excel.bean;

import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/27
 */
@Data
public class GradeBean {

    private String className;

    private String userName;
    
    private Integer mathGrade;
    
    private Integer chinaGrade;
    
    private Integer englishGrade;
}
