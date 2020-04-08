package cc.mrbird.febs.server.system.service;

import cc.mrbird.febs.common.entity.system.TradeLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITradeLogService extends IService<TradeLog> {
    void orderAndPay(TradeLog tradeLog);

    void pay(TradeLog tradeLog, String transactionId);
}
