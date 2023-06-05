package cn.czyx007.mt.service;

import cn.czyx007.mt.dto.SetmealDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.czyx007.mt.bean.Setmeal;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
public interface SetmealService extends IService<Setmeal> {
    void saveSetmealDish(SetmealDTO setmealDTO);

    void updateSetmealDish(SetmealDTO setmealDTO);
}
