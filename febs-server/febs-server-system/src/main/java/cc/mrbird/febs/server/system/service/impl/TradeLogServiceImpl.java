package cc.mrbird.febs.server.system.service.impl;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.common.entity.system.TransactionLog;
import cc.mrbird.febs.server.system.mapper.TradeLogMapper;
import cc.mrbird.febs.server.system.mapper.TransactionLogMapper;
import cc.mrbird.febs.server.system.service.ITradeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service("tradeLogService")
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Override
    public void orderAndPay(TradeLog tradeLog) {
        // 检测库存
        log.info("检测商品Id为{}，名称为{}的商品库存，库存充足",tradeLog.getGoodsId(),tradeLog.getGoodsName());

        String transactionId = UUID.randomUUID().toString();
        tradeLog.setTransactionId(transactionId);
        this.rocketMQTemplate.sendMessageInTransaction(
                "pay-success-group", // 事务消息分组,组名
                "pay-success", // 事务消息topic
                MessageBuilder.withPayload(tradeLog)
                              .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                              .build(), // 消息
                tradeLog // 额外参数，供后续回调使用
        );
    }

    @Override
    @Transactional
    public void pay(TradeLog tradeLog, String transactionId) {
        tradeLog.setCreateTime(new Date());
        tradeLog.setStatus("下单并支付成功");
        // 保存日志
        tradeLog.setTransactionId("");
        this.save(tradeLog);

        log.info("用户已经下单并支付成功商品id{}，商品名称{}", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        // 记录事务日志
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setTransactionId(transactionId);
        String remark = String.format("事务Id为%s的本地事务执行成功", transactionId);
        transactionLog.setRemark(remark);
        int txId = transactionLogMapper.insert(transactionLog);
        log.info("事务ID为{}的本地事务执行成功",transactionId);

    }

}
