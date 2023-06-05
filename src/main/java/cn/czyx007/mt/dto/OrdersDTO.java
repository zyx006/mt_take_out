package cn.czyx007.mt.dto;

import cn.czyx007.mt.bean.OrderDetail;
import cn.czyx007.mt.bean.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2023/6/5 - 16:15
 */
@Data
public class OrdersDTO extends Orders {
    private List<OrderDetail> orderDetails;
}
