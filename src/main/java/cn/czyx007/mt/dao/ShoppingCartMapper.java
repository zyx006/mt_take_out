package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车(ShoppingCart)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
