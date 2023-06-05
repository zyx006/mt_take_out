package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.DishFlavor;
import cn.czyx007.mt.service.DishFlavorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 菜品口味关系表(DishFlavor)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("dishFlavor")
public class DishFlavorController {
    /**
     * 服务对象
     */
    @Autowired
    private DishFlavorService dishFlavorService;


}
