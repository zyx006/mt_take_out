package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐(Setmeal)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
