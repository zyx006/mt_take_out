package cn.czyx007.mt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.czyx007.mt.bean.ShoppingCart;

/**
 * 购物车(ShoppingCart)表服务接口
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart sub(ShoppingCart cart);
}
