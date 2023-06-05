package cn.czyx007.mt.controller;

import cn.czyx007.mt.bean.AddressBook;
import cn.czyx007.mt.service.AddressBookService;
import cn.czyx007.mt.utils.BaseContext;
import cn.czyx007.mt.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址管理(AddressBook)表控制层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:09:40
 */
@RestController
@RequestMapping("addressBook")
public class AddressBookController {
    /**
     * 服务对象
     */
    @Autowired
    private AddressBookService addressBookService;

    //查询当前登录用户的所有地址信息
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        List<AddressBook> bookList = addressBookService.list(lqw);
        return R.success(bookList);
    }

    //添加收货地址
    @PostMapping
    public R<String> add(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success("地址保存成功");
    }

    //设置默认地址
    @PutMapping("/default")
    public R<String> defaultAddress(@RequestBody AddressBook addressBook){
        addressBookService.defaultAddress(addressBook);
        return R.success("设置默认地址成功！");
    }

    //查询默认地址
    @GetMapping("/default")
    public R<AddressBook> getDefaultAddress(){
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId, BaseContext.getCurrentId()).eq(AddressBook::getIsDefault, 1);
        return R.success(addressBookService.getOne(lqw));
    }

    //根据id获取地址信息
    @GetMapping("/{id}")
    public R<AddressBook> getAddressById(@PathVariable("id") Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    //修改地址
    @PutMapping
    public R<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("地址修改成功！");
    }

    @DeleteMapping
    public R<String> deleteAddress(@RequestParam("ids") Long ids){
        addressBookService.removeById(ids);
        return R.success("地址删除成功");
    }
}
