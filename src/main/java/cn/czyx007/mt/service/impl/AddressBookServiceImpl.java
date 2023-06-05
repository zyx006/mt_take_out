package cn.czyx007.mt.service.impl;

import cn.czyx007.mt.utils.BaseContext;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.czyx007.mt.service.AddressBookService;
import cn.czyx007.mt.dao.AddressBookMapper;
import cn.czyx007.mt.bean.AddressBook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 地址管理(AddressBook)表服务实现类
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:43
 */
@Service
@Transactional
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public void defaultAddress(AddressBook addressBook) {
        //把当前登录用户的所有地址的默认值改为0
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();
        luw.eq(AddressBook::getUserId, BaseContext.getCurrentId()).set(AddressBook::getIsDefault, 0);
        this.update(luw);
        //把当前登录用户的当前地址的默认值改为1
        LambdaUpdateWrapper<AddressBook> luw1 = new LambdaUpdateWrapper<>();
        luw1.eq(AddressBook::getId, addressBook.getId()).set(AddressBook::getIsDefault, 1);
        this.update(luw1);
    }
}
