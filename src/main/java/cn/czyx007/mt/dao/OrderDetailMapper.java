package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细表(OrderDetail)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
