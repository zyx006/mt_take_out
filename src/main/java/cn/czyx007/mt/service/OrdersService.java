package cn.czyx007.mt.service;

import cn.czyx007.mt.dto.OrdersDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.czyx007.mt.bean.Orders;

/**
 * 订单表(Orders)表服务接口
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
public interface OrdersService extends IService<Orders> {
    void addOrder(Orders orders);

    Page<OrdersDTO> getUserPage(Integer page, Integer pageSize, LambdaQueryWrapper<Orders> lqw);
}
