package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品及套餐分类(Category)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:43
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
