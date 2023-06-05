package cn.czyx007.mt.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.czyx007.mt.bean.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址管理(AddressBook)表数据库访问层
 *
 * @author 张宇轩
 * @since 2023-05-29 11:08:43
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
