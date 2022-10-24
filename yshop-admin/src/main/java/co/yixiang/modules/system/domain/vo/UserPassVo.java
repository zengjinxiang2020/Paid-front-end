/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.modules.system.domain.vo;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 * @author Shuo Xing
 * @date 2019年7月11日13:59:49
 */
@Data
public class UserPassVo {

    private String oldPass;

    private String newPass;
}
