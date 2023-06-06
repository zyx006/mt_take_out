package cn.czyx007.mt.controller;

import cn.czyx007.mt.config.AlipayConfig;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author : 张宇轩
 * @createTime : 2023/6/6 - 9:30
 */
@RestController
@RequestMapping("pay")
@Slf4j
public class AlipayController {
    //实现对接支付宝沙箱支付
    @GetMapping("/orderPay")
    public String orderPay(@RequestParam("orderNum") Long out_trade_no,
                           @RequestParam("amount") BigDecimal total_amount) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.URL,
                AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.FORMAT,
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(AlipayConfig.notify_url);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(AlipayConfig.return_url);
        /* *****必传参数***** */
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", out_trade_no);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", total_amount);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", "美食元素订单支付");

        /* ****可选参数***** */
        //手机网站支付默认传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "QUICK_WAP_WAY");

        request.setBizContent(bizContent.toString());
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);

        String result = response.getBody();
        log.info("result: {}", result);
        return result;
    }
}
