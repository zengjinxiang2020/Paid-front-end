package co.yixiang.modules.user.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * 商城商品分类CateVO
 * @author Shuo Xing
 * @since 2020-05-07
 */
@Getter
@Setter
@ToString
public class CityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer v; //城市id

    private String n; //名称

    private Integer pid;//父级id

    private List<CityVo> c; //子集


}
