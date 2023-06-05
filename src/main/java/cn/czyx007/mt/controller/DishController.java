package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.*;
import cn.czyx007.mt.common.CustomException;
import cn.czyx007.mt.dto.DishDTO;
import cn.czyx007.mt.service.*;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(Dish)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("dish")
public class DishController {
    /**
     * 服务对象
     */
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    //保存菜品和口味信息
    @PostMapping
    public R<String> saveDishFlavor(@RequestBody DishDTO dishDTO){
        dishService.saveDishFlavor(dishDTO);
        return R.success("菜品保存成功！");
    }

    //分页查询员工信息
    @GetMapping("/page")
    public R<IPage<DishDTO>> page(@RequestParam Integer page,
                                   @RequestParam Integer pageSize,
                                  @RequestParam(required = false) String name){
        //这个iPageDto才是最后封装返回的分页数据
        Page<DishDTO> iPageDto = new Page<>(page, pageSize);
        Page<Dish> iPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(name), Dish::getName, name).orderByDesc(Dish::getUpdateTime);
        dishService.page(iPage, lqw);
        //对象复制操作，把iPage中的分页数据(页数，总条数等)复制给iPageDto，具体的菜品数据不复制。
        BeanUtils.copyProperties(iPage, iPageDto, "records");
        //构建菜品数据，封装到PageDto中
        List<Dish> dishList = iPage.getRecords();
        //把dishList转换为dishDtoList
        List<DishDTO> dishDTOList = dishList.stream().map(dish -> {
            //构建新的DishDTO对象
            DishDTO dishDTO = new DishDTO();
            //把dish复制给dishDIO
            BeanUtils.copyProperties(dish, dishDTO);
            //获取categoryName，设置到DishDTo中
            Category category = categoryService.getById(dish.getCategoryId());
            dishDTO.setCategoryName( category.getName());
            return dishDTO;
        }).collect(Collectors.toList());
        //设置dishDTOList到iPageDto
        iPageDto.setRecords(dishDTOList);
        return R.success(iPageDto);
    }

    @PostMapping("/status/{status}")
    public R<String> setStatus(@PathVariable("status") Integer status,
                               @RequestParam List<Long> ids){
        LambdaUpdateWrapper<Dish> luw = new LambdaUpdateWrapper<>();
        luw.set(Dish::getStatus, status).set(Dish::getUpdateTime, LocalDateTime.now()).in(Dish::getId, ids);
        dishService.update(luw);
        return R.success("停售成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.in(Dish::getId, ids).eq(Dish::getStatus, 1);
        long cnt = dishService.count(qw);
        if(cnt > 0){
            throw new CustomException("存在起售的菜品，不能删除");
        }
        for (Long id : ids) {
            LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
            lqw.eq(SetmealDish::getDishId, id);
            long count = setmealDishService.count(lqw);
            if (count > 0) {
                throw new CustomException("存在包含该菜品的套餐，无法删除");
            }
        }
        for(Long id: ids) {
            LambdaUpdateWrapper<DishFlavor> luw = new LambdaUpdateWrapper<>();
            luw.eq(DishFlavor::getDishId, id);
            dishFlavorService.remove(luw);
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }

    //根据菜品id查询菜品和口味的信息
    @GetMapping("/{dishId}")
    public R<DishDTO> getDIshFlavorById(@PathVariable("dishId") Long dishId){
        DishDTO dishDTO = new DishDTO();
        //查询菜品信息
        Dish dish = dishService.getById(dishId);
        BeanUtils.copyProperties(dish, dishDTO);
        //查询当前菜品下的口味信息
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishId);
        List<DishFlavor> dishFlavorList = dishFlavorService.list(lqw);
        //把口味的集合放入DishDTO
        dishDTO.setFlavors(dishFlavorList);
        return R.success(dishDTO);
    }

    @PutMapping
    public R<String> updateDishFlavor(@RequestBody DishDTO dishDTO){
        dishService.updateDishFlavor(dishDTO);
        return R.success("修改菜品信息成功");
    }

    //根据分类的id，查询当前分类下的所有菜品信息
//    @GetMapping("/list")
//    private R<List<Dish>> list(@RequestParam("categoryId") Long categoryId){
//        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(categoryId!=null, Dish::getCategoryId, categoryId)
//                .eq(Dish::getStatus, 1)
//                .orderByDesc(Dish::getUpdateTime);
//        List<Dish> dishList = dishService.list(lqw);
//        return R.success(dishList);
//    }
    @GetMapping("/list")
    private R<List<DishDTO>> list(Dish dish){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId, dish.getCategoryId())
                //status为空时对应后台页面，非空则对应客户端页面
                .eq(dish.getStatus()!=null, Dish::getStatus, dish.getStatus())
                .orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(lqw);
        List<DishDTO> dishDTOList = dishList.stream().map(item -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO);
            //封装口味数据
            LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
            qw.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(qw);
            dishDTO.setFlavors(dishFlavorList);
            return dishDTO;
        }).collect(Collectors.toList());
        return R.success(dishDTOList);
    }
}
