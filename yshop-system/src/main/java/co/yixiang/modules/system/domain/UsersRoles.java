/**
* Copyright (C) 2018-2020
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/
package co.yixiang.modules.system.domain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-16
*/
@Data
@TableName("users_roles")
public class UsersRoles implements Serializable {

    /** 用户ID */
    private Long userId;


    /** 角色ID */
    private Long roleId;

    public void copy(UsersRoles source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
