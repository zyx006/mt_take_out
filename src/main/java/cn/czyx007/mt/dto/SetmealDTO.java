package cn.czyx007.mt.dto;

import cn.czyx007.mt.bean.Setmeal;
import cn.czyx007.mt.bean.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2023/6/1 - 11:17
 */
@Data
public class SetmealDTO extends Setmeal {
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    private String categoryName;
}
