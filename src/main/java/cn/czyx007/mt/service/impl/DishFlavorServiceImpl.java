package cn.czyx007.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.DishFlavorService;
import cn.czyx007.mt.dao.DishFlavorMapper;
import cn.czyx007.mt.bean.DishFlavor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜品口味关系表(DishFlavor)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Service
@Transactional
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
