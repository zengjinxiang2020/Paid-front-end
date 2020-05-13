package co.yixiang.modules.shop.service.impl;

import co.yixiang.modules.shop.domain.YxStoreProductAttrResult;
import co.yixiang.common.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import co.yixiang.modules.shop.service.YxStoreProductAttrResultService;
import co.yixiang.modules.shop.service.mapper.StoreProductAttrResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;


/**
* @author hupeng
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxStoreProductAttrResult")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreProductAttrResultServiceImpl extends BaseServiceImpl<StoreProductAttrResultMapper, YxStoreProductAttrResult> implements YxStoreProductAttrResultService {



}
