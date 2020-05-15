/**
* Copyright (C) 2018-2019
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/
package co.yixiang.modules.system.domain;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-14
*/
@Data
@TableName("dict_detail")
public class DictDetail implements Serializable {

    /** 字典详细 */
    @TableId
    private Long id;


    /** 字典标签 */
    //@NotBlank
    private String label;


    /** 字典值 */
    //@NotBlank
    private String value;


    /** 排序 */
    private String sort;


    /** 字典id */
    private Long dictId;


    /** 创建日期 */
    //@TableField(fill= FieldFill.INSERT)
    private Timestamp createTime;


    public void copy(DictDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
