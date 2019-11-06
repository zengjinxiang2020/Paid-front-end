package co.yixiang.modules.shop.service;

import co.yixiang.modules.shop.entity.YxStoreCouponIssueUser;
import co.yixiang.common.service.BaseService;
import co.yixiang.modules.shop.web.param.YxStoreCouponIssueUserQueryParam;
import co.yixiang.modules.shop.web.vo.YxStoreCouponIssueUserQueryVo;
import co.yixiang.common.web.vo.Paging;

import java.io.Serializable;

/**
 * <p>
 * 优惠券前台用户领取记录表 服务类
 * </p>
 *
 * @author hupeng
 * @since 2019-10-27
 */
public interface YxStoreCouponIssueUserService extends BaseService<YxStoreCouponIssueUser> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    YxStoreCouponIssueUserQueryVo getYxStoreCouponIssueUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     * @param yxStoreCouponIssueUserQueryParam
     * @return
     */
    Paging<YxStoreCouponIssueUserQueryVo> getYxStoreCouponIssueUserPageList(YxStoreCouponIssueUserQueryParam yxStoreCouponIssueUserQueryParam) throws Exception;

}
