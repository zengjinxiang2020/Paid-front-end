package co.yixiang.modules.system.service.mapper;

import co.yixiang.mapper.EntityMapper;
import co.yixiang.modules.system.domain.Dept;
import co.yixiang.modules.system.service.dto.DeptDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends EntityMapper<DeptDTO, Dept> {

}