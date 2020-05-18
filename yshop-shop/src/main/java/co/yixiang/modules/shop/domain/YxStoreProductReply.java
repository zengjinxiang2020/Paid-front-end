/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.modules.shop.domain;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import java.io.Serializable;

/**
* @author hupeng
* @date 2020-05-12
*/

@Data
@TableName("yx_store_product_reply")
public class YxStoreProductReply implements Serializable {

    /** 评论ID */
    @TableId
    private Integer id;


    /** 用户ID */
    private Integer uid;


    /** 订单ID */
    private Integer oid;


    /** 唯一id */
    @TableField(value = "`unique`")
    private String unique;


    /** 产品id */
    private Integer productId;


    /** 某种商品类型(普通商品、秒杀商品） */
    private String replyType;


    /** 商品分数 */
    private Integer productScore;


    /** 服务分数 */
    private Integer serviceScore;


    /** 评论内容 */
    private String comment;


    /** 评论图片 */
    private String pics;


    /** 评论时间 */
    private Integer addTime;


    /** 管理员回复内容 */
    private String merchantReplyContent;


    /** 管理员回复时间 */
    private Integer merchantReplyTime;


    /** 0未删除1已删除 */
    private Integer isDel;


    /** 0未回复1已回复 */
    private Integer isReply;

    @TableField(exist = false)
    private YxStoreProduct storeProduct;

    @TableField(exist = false)
    private YxUser user;

    public void copy(YxStoreProductReply source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
