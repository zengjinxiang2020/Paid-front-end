/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.modules.activity.service.dto;

import co.yixiang.annotation.Query;
import lombok.Data;

/**
* @author Shuo Xing
* @date 2020-05-13
*/
@Data
public class YxStoreCouponIssueQueryCriteria{

    @Query
    private Integer isDel;

}
