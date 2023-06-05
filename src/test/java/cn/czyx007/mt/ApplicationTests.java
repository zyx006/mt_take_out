package cn.czyx007.mt;

import cn.czyx007.mt.utils.SendEmailUtils;
import org.apache.commons.mail.EmailException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() throws EmailException {
        SendEmailUtils.sendAuthCodeEmail("xxx@qq.com", "不要再卷啦");
    }
}
