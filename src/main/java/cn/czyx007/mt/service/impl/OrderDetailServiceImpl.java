package cn.czyx007.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.OrderDetailService;
import cn.czyx007.mt.dao.OrderDetailMapper;
import cn.czyx007.mt.bean.OrderDetail;
import org.springframework.stereotype.Service;

/**
 * 订单明细表(OrderDetail)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
