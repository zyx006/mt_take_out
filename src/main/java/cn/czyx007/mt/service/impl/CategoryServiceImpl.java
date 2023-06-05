package cn.czyx007.mt.service.impl;

import cn.czyx007.mt.bean.Dish;
import cn.czyx007.mt.bean.Setmeal;
import cn.czyx007.mt.common.CustomException;
import cn.czyx007.mt.service.DishService;
import cn.czyx007.mt.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.CategoryService;
import cn.czyx007.mt.dao.CategoryMapper;
import cn.czyx007.mt.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜品及套餐分类(Category)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void removeCateById(Long cateId) {
        //判断当前分类下，是否有菜品信息，如果有则不能删除
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId, cateId);
        long dishCount = dishService.count(lqw);
        if (dishCount > 0) {
            throw new CustomException("当前分类下有菜品信息，不能删除！");
        }
        //判断当前分类下，是否有套餐信息，如果有则不能删除
        LambdaQueryWrapper<Setmeal> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Setmeal::getCategoryId, cateId);
        long setmealCount = setmealService.count(lqw1);
        if(setmealCount > 0){
            throw new CustomException("当前分类下有套餐信息，不能删除！");
        }
        //删除当前分类信息
        this.removeById(cateId);
    }
}
