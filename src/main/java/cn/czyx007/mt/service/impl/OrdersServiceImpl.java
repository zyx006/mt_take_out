package cn.czyx007.mt.service.impl;

import cn.czyx007.mt.bean.*;
import cn.czyx007.mt.common.CustomException;
import cn.czyx007.mt.dto.OrdersDTO;
import cn.czyx007.mt.service.*;
import cn.czyx007.mt.utils.BaseContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.dao.OrdersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:45
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public void addOrder(Orders orders) {
        //1.查询用户信息
        User user = userService.getById(BaseContext.getCurrentId());
        //2.查询地址信息
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        //3.保存订单明细的数据
        //3-1.生成订单id
        long orderId = IdWorker.getId();
        //3-2.查询购物车数据
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lqw);
        if (CollectionUtils.isEmpty(shoppingCartList)) {
            throw new CustomException("当前购物车信息为空，无法下单");
        }
        //3-3.计算总金额-考虑多线程并发安全的问题
        //AtomInteger：提供了原子性的计算操作
        AtomicInteger amount = new AtomicInteger();
        //3-4.遍历购物车集合，构建订单明细数据
        List<OrderDetail> orderDetailList = shoppingCartList.stream().map(cart -> {
            //总金额 += 数量*单价
            amount.addAndGet(cart.getAmount()
                    .multiply(new BigDecimal(cart.getNumber())).intValue());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(cart, orderDetail);
            return orderDetail;
        }).collect(Collectors.toList());
        //4.封装订单表的数据
        Orders order = new Orders();
        order.setNumber(String.valueOf(orderId));
        order.setStatus(1);
        order.setUserId(user.getId());
        order.setAddressBookId(orders.getAddressBookId());
        order.setOrderTime(LocalDateTime.now());
        order.setPayMethod(2);
        order.setAmount(new BigDecimal(amount.get()));
        order.setRemark(orders.getRemark());
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setUserName(user.getName());
        order.setConsignee(addressBook.getConsignee());
        //5.保存订单数据
        this.save(order);
        //6.保存订单明细苏剧
        orderDetailService.saveBatch(orderDetailList);
        //7.清空购物车数据
        shoppingCartService.remove(lqw);
    }

    @Override
    public Page<OrdersDTO> getUserPage(Integer page, Integer pageSize, LambdaQueryWrapper<Orders> lqw) {
        Page<Orders> ordersPage = this.page(new Page<>(page, pageSize), lqw);
        List<OrdersDTO> dtoList = ordersPage.getRecords().stream().map(orders -> {
            OrdersDTO ordersDTO = new OrdersDTO();
            LambdaQueryWrapper<OrderDetail> qw = new LambdaQueryWrapper<>();
            qw.eq(OrderDetail::getOrderId, orders.getNumber());
            List<OrderDetail> detailList = orderDetailService.list(qw);

            BeanUtils.copyProperties(orders, ordersDTO);
            ordersDTO.setOrderDetails(detailList);
            return ordersDTO;
        }).collect(Collectors.toList());

        Page<OrdersDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(ordersPage, dtoPage, "records");
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }


}
