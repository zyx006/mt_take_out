package cn.czyx007.mt.service;

import cn.czyx007.mt.common.CustomException;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.czyx007.mt.bean.Category;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
public interface CategoryService extends IService<Category> {
    void removeCateById(Long cateId);
}
