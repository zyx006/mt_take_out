package cn.czyx007.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.UserService;
import cn.czyx007.mt.dao.UserMapper;
import cn.czyx007.mt.bean.User;
import org.springframework.stereotype.Service;

/**
 * 用户信息(User)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:46
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
