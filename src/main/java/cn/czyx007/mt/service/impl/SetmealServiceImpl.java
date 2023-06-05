package cn.czyx007.mt.service.impl;

import cn.czyx007.mt.bean.SetmealDish;
import cn.czyx007.mt.dto.SetmealDTO;
import cn.czyx007.mt.service.SetmealDishService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.SetmealService;
import cn.czyx007.mt.dao.SetmealMapper;
import cn.czyx007.mt.bean.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐(Setmeal)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveSetmealDish(SetmealDTO setmealDTO) {
        //保存套餐信息
        this.save(setmealDTO);
        //保存套餐菜品信息
        List<SetmealDish> list = setmealDTO.getSetmealDishes().stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDTO.getId());
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);
    }

    @Override
    public void updateSetmealDish(SetmealDTO setmealDTO) {
        //保存套餐信息
        this.updateById(setmealDTO);
        //清空原有菜品信息
        LambdaUpdateWrapper<SetmealDish> luw = new LambdaUpdateWrapper<>();
        luw.eq(SetmealDish::getSetmealId, setmealDTO.getId());
        setmealDishService.remove(luw);
        //保存套餐新菜品信息
        List<SetmealDish> list = setmealDTO.getSetmealDishes().stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDTO.getId());
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);
    }
}
