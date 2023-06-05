package cn.czyx007.mt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.EmployeeService;
import cn.czyx007.mt.dao.EmployeeMapper;
import cn.czyx007.mt.bean.Employee;
import org.springframework.stereotype.Service;

/**
 * 员工信息(Employee)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:44
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
