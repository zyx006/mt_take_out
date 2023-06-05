package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.OrderDetail;
import cn.czyx007.mt.service.OrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 订单明细表(OrderDetail)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("orderDetail")
public class OrderDetailController {
    /**
     * 服务对象
     */
    @Autowired
    private OrderDetailService orderDetailService;


}
