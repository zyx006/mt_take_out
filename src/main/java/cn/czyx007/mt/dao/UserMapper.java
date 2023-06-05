package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息(User)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:46
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
