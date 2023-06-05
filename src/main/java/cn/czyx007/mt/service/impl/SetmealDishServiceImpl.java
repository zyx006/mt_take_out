package cn.czyx007.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.SetmealDishService;
import cn.czyx007.mt.dao.SetmealDishMapper;
import cn.czyx007.mt.bean.SetmealDish;
import org.springframework.stereotype.Service;

/**
 * 套餐菜品关系(SetmealDish)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
