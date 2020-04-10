package cc.mrbird.febs.server.system.service;

import cc.mrbird.febs.common.entity.system.TradeLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author JOE
 * @create 2020/4/10 14:52
 */
public interface ITradeLogService extends IService<TradeLog> {
    void orderAndPay(TradeLog tradeLog);
}
