package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.Dish;
import cn.czyx007.mt.bean.SetmealDish;
import cn.czyx007.mt.dto.SetmealDishDTO;
import cn.czyx007.mt.service.DishService;
import cn.czyx007.mt.service.SetmealDishService;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐菜品关系(SetmealDish)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("setmealDish")
public class SetmealDishController {
    /**
     * 服务对象
     */
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishService dishService;

    //查询套餐详情信息
    @GetMapping("/dish/{id}")
    public R<List<SetmealDishDTO>> setmealDetail(@PathVariable Long id) {
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, id)
                .orderByDesc(SetmealDish::getUpdateTime);
        List<SetmealDish> setmealDishList = setmealDishService.list(lqw);
        List<SetmealDishDTO> setmealDishDTOList = setmealDishList.stream().map(setmealDish -> {
            SetmealDishDTO setmealDishDTO = new SetmealDishDTO();
            BeanUtils.copyProperties(setmealDish, setmealDishDTO);
            Dish dish = dishService.getById(setmealDish.getDishId());
            setmealDishDTO.setImage(dish.getImage());
            return setmealDishDTO;
        }).collect(Collectors.toList());
        return R.success(setmealDishDTOList);
    }

}
