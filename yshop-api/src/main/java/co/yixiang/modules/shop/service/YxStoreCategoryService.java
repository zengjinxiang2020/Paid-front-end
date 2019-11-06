package co.yixiang.modules.shop.service;

import co.yixiang.modules.shop.entity.YxStoreCategory;
import co.yixiang.common.service.BaseService;
import co.yixiang.modules.shop.web.param.YxStoreCategoryQueryParam;
import co.yixiang.modules.shop.web.vo.YxStoreCategoryQueryVo;
import co.yixiang.common.web.vo.Paging;
import co.yixiang.utils.CateDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2019-10-22
 */
public interface YxStoreCategoryService extends BaseService<YxStoreCategory> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    YxStoreCategoryQueryVo getYxStoreCategoryById(Serializable id) throws Exception;

    public List<CateDTO> getList();

    public List<String> getAllChilds(int catid);



}
