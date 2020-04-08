package cc.mrbird.febs.server.test.service.impl;

import cc.mrbird.febs.common.entity.system.TradeLog;
import cc.mrbird.febs.server.test.mapper.TradeLogMapper;
import cc.mrbird.febs.server.test.service.ITradeLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service("tradeLogService")
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {
    @Override
    @Transactional
    public void packageAndSend(TradeLog tradeLog) {
        TradeLog tl = new TradeLog();
        tl.setGoodsId(tradeLog.getGoodsId());
        tl.setGoodsName(tradeLog.getGoodsName());
        tl.setStatus("打包完成，开始物流配送");
        tl.setCreateTime(new Date());
        tl.setTransactionId(tradeLog.getTransactionId());
        TradeLog tl2 = this.getOne(new LambdaQueryWrapper<TradeLog>().eq(TradeLog::getTransactionId, tl.getTransactionId()));

        if( tl2 == null) {
            this.save(tl);
            log.info("商品ID为{}，名称为{}, txId为{}的商品打包完毕，开始物流配送", tradeLog.getGoodsId(), tradeLog.getGoodsName(), tradeLog.getTransactionId());
        } else {
            log.info("商品ID为{}，名称为{}的商品已经打包配送");
        }

    }
}
