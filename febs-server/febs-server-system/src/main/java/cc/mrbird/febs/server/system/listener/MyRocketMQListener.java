package cc.mrbird.febs.server.system.listener;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.common.entity.system.TransactionLog;
import cc.mrbird.febs.server.system.mapper.TransactionLogMapper;
import cc.mrbird.febs.server.system.service.ITradeLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "pay-success-group")
public class MyRocketMQListener implements RocketMQLocalTransactionListener {

    @Autowired
    private ITradeLogService tradeLogService;

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("接收到MQ Server的回复");
        try {
            this.tradeLogService.pay((TradeLog) o, transactionId);
            log.info("本地事务执行成功, 往RocketMQ发送COMMIT");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.info("本地事务回滚，往RocketMQ发送ROLLBACK", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    /**
    * RocketMQ回查本地事务状态
    * @param message 消息
    * @return RocketMQ事务状态
    * */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transactionId = (String)message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        log.info("RocketMQ事务状态回查询");

        // 从数据库中根据事务Id查询事务日志，对应图中的第6步
        TransactionLog transactionLog = transactionLogMapper.selectOne(
                new LambdaQueryWrapper<TransactionLog>().eq(TransactionLog::getTransactionId, transactionId));
        // 对应图中的步骤7
        return transactionLog != null ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
    }
}