package cc.mrbird.febs.server.system.service.impl;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.system.mapper.TradeLogMapper;
import cc.mrbird.febs.server.system.service.IRemoteTradeService;
import cc.mrbird.febs.server.system.service.ITradeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author JOE
 * @create 2020/4/10 14:53
 */
@Slf4j
@Service("tradeLogService")
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {

    @Autowired
    private IRemoteTradeService remoteTradeService;

    @Override
    @LcnTransaction
    public void orderAndPay(TradeLog tradeLog) {
        tradeLog.setCreateTime(new Date());
        tradeLog.setStatus("下单并支付成功");

        this.save(tradeLog);
        log.info("用户已经下单并支付成功商品ID为{}，名称为{}的商品", tradeLog.getGoodsId(), tradeLog.getGoodsName());
        remoteTradeService.packageAndSend(tradeLog);

        throw new RuntimeException("抛个异常");
    }
}
