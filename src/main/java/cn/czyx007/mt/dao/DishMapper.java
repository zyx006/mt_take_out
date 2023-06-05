package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品管理(Dish)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
