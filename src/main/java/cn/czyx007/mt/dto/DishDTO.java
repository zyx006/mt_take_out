package cn.czyx007.mt.dto;

import cn.czyx007.mt.bean.Dish;
import cn.czyx007.mt.bean.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/31 - 14:30
 */
@Data
public class DishDTO extends Dish {
    //口味的集合
    private List<DishFlavor> flavors = new ArrayList<>();

    //分类的名称 与前端elementUI的prop属性一致
    private String categoryName;
}
