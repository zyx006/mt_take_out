package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.*;
import cn.czyx007.mt.common.CustomException;
import cn.czyx007.mt.dto.DishDTO;
import cn.czyx007.mt.dto.SetmealDTO;
import cn.czyx007.mt.dto.SetmealDishDTO;
import cn.czyx007.mt.service.CategoryService;
import cn.czyx007.mt.service.DishService;
import cn.czyx007.mt.service.SetmealDishService;
import cn.czyx007.mt.service.SetmealService;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐(Setmeal)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    /**
     * 服务对象
     */
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private DishService dishService;

    //添加套餐信息
    @PostMapping
    public R<String> saveSetmealDish(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveSetmealDish(setmealDTO);
        return R.success("套餐添加成功！");
    }

    @GetMapping("/page")
    public R<IPage<SetmealDTO>> page(@RequestParam Integer page,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(required = false) String name){
        IPage<SetmealDTO> setmealDTOIPage = new Page<>();
        IPage<Setmeal> iPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(name), Setmeal::getName, name);
        setmealService.page(iPage, lqw);
        BeanUtils.copyProperties(iPage, setmealDTOIPage, "records");
        List<SetmealDTO> setmealDTOList = iPage.getRecords().stream().map(setmeal -> {
            SetmealDTO setmealDTO = new SetmealDTO();
            BeanUtils.copyProperties(setmeal, setmealDTO);
            Category category = categoryService.getById(setmeal.getCategoryId());
            setmealDTO.setCategoryName(category.getName());
            return setmealDTO;
        }).collect(Collectors.toList());
        setmealDTOIPage.setRecords(setmealDTOList);
        return R.success(setmealDTOIPage);
    }

    @PostMapping("/status/{status}")
    public R<String> setStatus(@PathVariable("status") Integer status,
                               @RequestParam List<Long> ids){
        LambdaUpdateWrapper<Setmeal> luw = new LambdaUpdateWrapper<>();
        luw.set(Setmeal::getStatus, status).set(Setmeal::getUpdateTime, LocalDateTime.now()).in(Setmeal::getId, ids);
        setmealService.update(luw);
        return R.success("停售成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        long cnt = setmealService.count(qw);
        if(cnt > 0){
            throw new CustomException("存在起售的套餐，不能删除");
        }
        for (Long id : ids) {
            LambdaUpdateWrapper<SetmealDish> luw = new LambdaUpdateWrapper<>();
            luw.eq(SetmealDish::getSetmealId, id);
            setmealDishService.remove(luw);
        }
        setmealService.removeBatchByIds(ids);
        return R.success("删除成功");
    }

    @GetMapping("/{setmealId}")
    public R<SetmealDTO> getSetmealById(@PathVariable("setmealId") Long setmealId){
        Setmeal setmeal = setmealService.getById(setmealId);
        SetmealDTO setmealDTO = new SetmealDTO();
        BeanUtils.copyProperties(setmeal, setmealDTO);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmealId);
        List<SetmealDish> setmealDishList = setmealDishService.list(lqw);
        setmealDTO.setSetmealDishes(setmealDishList);
        return R.success(setmealDTO);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmealDish(setmealDTO);
        return R.success("套餐保存成功！");
    }

    //根据分类id和状态查询套餐信息
    @GetMapping("/list")
//    public R<List<Setmeal>> list(@RequestParam("categoryId") Long categoryId, @RequestParam("status") Integer status){
//        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Setmeal::getCategoryId, categoryId).eq(Setmeal::getStatus, status);
//        List<Setmeal> list = setmealService.list(lqw);
//        return R.success(list);
//    }
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(setmeal.getStatus()!=null, Setmeal::getStatus, setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmealList = setmealService.list(lqw);
        return R.success(setmealList);
    }

    //查看套餐详情
    @GetMapping("/dish/{id}")
    public R<List<SetmealDishDTO>> getSetmealDetail(@PathVariable("id") Long id){
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, id).orderByDesc(SetmealDish::getUpdateTime);
        List<SetmealDish> setmealDishList = setmealDishService.list(lqw);

        List<SetmealDishDTO> list = setmealDishList.stream().map(setmealDish -> {
            SetmealDishDTO setmealDishDTO = new SetmealDishDTO();
            BeanUtils.copyProperties(setmealDish, setmealDishDTO);
            Dish dish = dishService.getById(setmealDish.getDishId());
            setmealDishDTO.setImage(dish.getImage());
            return setmealDishDTO;
        }).collect(Collectors.toList());
        return R.success(list);

    }
}
