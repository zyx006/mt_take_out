package cn.czyx007.mt.service.impl;

import cn.czyx007.mt.bean.DishFlavor;
import cn.czyx007.mt.dto.DishDTO;
import cn.czyx007.mt.service.DishFlavorService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.DishService;
import cn.czyx007.mt.dao.DishMapper;
import cn.czyx007.mt.bean.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(Dish)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Service
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public void saveDishFlavor(DishDTO dishDTO) {
        //封装菜品信息，保存
        this.save(dishDTO);
        //封装口味信息
        List<DishFlavor> flavorList = dishDTO.getFlavors().stream().map(dishFlavor -> {
            dishFlavor.setDishId(dishDTO.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        //批量保存数据
        dishFlavorService.saveBatch(flavorList);
    }

    @Override
    public void updateDishFlavor(DishDTO dishDTO){
        //保存修改的菜品信息
        this.updateById(dishDTO);
        //删除旧口味数据
        LambdaUpdateWrapper<DishFlavor> luw = new LambdaUpdateWrapper<>();
        luw.eq(DishFlavor::getDishId, dishDTO.getId());
        dishFlavorService.remove(luw);
        //保存新口味
        List<DishFlavor> flavorList = dishDTO.getFlavors().stream().map(dishFlavor -> {
            dishFlavor.setDishId(dishDTO.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavorList);
    }
}
