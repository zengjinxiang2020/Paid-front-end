package co.yixiang.modules.shop.service;

import co.yixiang.modules.shop.entity.YxStoreProductAttr;
import co.yixiang.common.service.BaseService;
import co.yixiang.modules.shop.entity.YxStoreProductAttrValue;
import co.yixiang.modules.shop.web.param.YxStoreProductAttrQueryParam;
import co.yixiang.modules.shop.web.vo.YxStoreProductAttrQueryVo;
import co.yixiang.common.web.vo.Paging;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 商品属性表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2019-10-23
 */
public interface YxStoreProductAttrService extends BaseService<YxStoreProductAttr> {

    void decProductAttrStock(int num, int productId, String unique);

    Map<String,Object> getProductAttrDetail(int productId,int uid,int type);

    int uniqueByStock(String unique);

    Boolean issetProductUnique(int productId,String unique);

    YxStoreProductAttrValue uniqueByAttrInfo(String unique);
}
