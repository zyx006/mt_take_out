package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.Category;
import cn.czyx007.mt.bean.Orders;
import cn.czyx007.mt.bean.Setmeal;
import cn.czyx007.mt.dto.OrdersDTO;
import cn.czyx007.mt.dto.SetmealDTO;
import cn.czyx007.mt.service.OrdersService;
import cn.czyx007.mt.utils.BaseContext;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单表(Orders)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@Controller
@RequestMapping("order")
public class OrdersController {
    /**
     * 服务对象
     */
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    @ResponseBody
    public R<Page<Orders>> page(@RequestParam Integer page,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(required = false) String number,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(required = false) LocalDateTime beginTime,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                     @RequestParam(required = false) LocalDateTime endTime){
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.like(number!=null, Orders::getNumber, number)
           .ge(beginTime!=null, Orders::getOrderTime, beginTime)
           .le(endTime!=null, Orders::getOrderTime, endTime);
        ordersService.page(ordersPage, lqw);
        return R.success(ordersPage);
    }

    @PutMapping
    @ResponseBody
    public R<String> setStatus(@RequestBody Orders orders){
        LambdaUpdateWrapper<Orders> luw = new LambdaUpdateWrapper<>();
        luw.eq(Orders::getId, orders.getId()).set(Orders::getStatus, orders.getStatus());
        ordersService.update(luw);
        return R.success("状态更改成功！");
    }

    //下单操作
    @PostMapping("/submit")
    @ResponseBody
    public R<Orders> addOrder(@RequestBody Orders orders){
        Orders order = ordersService.addOrder(orders);
        return R.success(order);
    }

    //查看订单
    @GetMapping("/userPage")
    @ResponseBody
    public R<Page<OrdersDTO>> page(@RequestParam Integer page,
                                   @RequestParam Integer pageSize){
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Orders::getUserId, BaseContext.getCurrentId()).orderByDesc(Orders::getOrderTime);
        Page<OrdersDTO> dtoPage = ordersService.getUserPage(page, pageSize, lqw);
        return R.success(dtoPage);
    }

    //支付完成之后的回调方法，处理订单状态等
    @GetMapping("/afterPayOrder")
    public String afterPayOrder(@RequestParam String out_trade_no){
        LambdaUpdateWrapper<Orders> luw = new LambdaUpdateWrapper<>();
        luw.eq(Orders::getNumber, out_trade_no)
                .set(Orders::getStatus, 2)
                .set(Orders::getCheckoutTime, LocalDateTime.now());
        ordersService.update(luw);
        return "redirect:/front/page/pay-success.html";
    }
}
