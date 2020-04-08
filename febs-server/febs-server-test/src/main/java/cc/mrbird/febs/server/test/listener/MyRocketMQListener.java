package cc.mrbird.febs.server.test.listener;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.test.service.ITradeLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "test-group", topic = "pay-success")
public class MyRocketMQListener implements RocketMQListener<TradeLog> {

    @Autowired
    private ITradeLogService tradeLogService;

    @Override
    public void onMessage(TradeLog tradeLog) {
        log.info("接收状态：{}", tradeLog.getStatus());
        log.info("监听到用户已经下单并支付成功ID为{}，名称为{}的商品", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        this.tradeLogService.packageAndSend(tradeLog);
    }
}
