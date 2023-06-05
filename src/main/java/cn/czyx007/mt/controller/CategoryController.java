package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.Category;
import cn.czyx007.mt.bean.Employee;
import cn.czyx007.mt.service.CategoryService;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜品及套餐分类(Category)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    //添加菜品套餐的分类信息
    @PostMapping
    public R<String> add(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功！");
    }

    //分页查询员工信息
    @GetMapping("/page")
    public R<IPage<Category>> page(@RequestParam Integer page,
                                   @RequestParam Integer pageSize){
        IPage<Category> iPage = new Page<>(page, pageSize);
        QueryWrapper<Category> lqw = new QueryWrapper<>();
        lqw.lambda().orderByDesc(Category::getUpdateTime);
        categoryService.page(iPage, lqw);
        return R.success(iPage);
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功！");
    }

    @DeleteMapping
    public R<String> removeById(@RequestParam("id") Long cateId){
        //删除分类信息
        categoryService.removeCateById(cateId);
        return R.success("删除分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam(required = false) Integer type){
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type!=null, Category::getType, type).orderByDesc(Category::getUpdateTime);
        List<Category> categoryList = categoryService.list(lqw);
        return R.success(categoryList);
    }
}
