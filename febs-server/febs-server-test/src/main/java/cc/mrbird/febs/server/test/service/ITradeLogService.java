package cc.mrbird.febs.server.test.service;

import cc.mrbird.febs.common.entity.system.TradeLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITradeLogService extends IService<TradeLog> {
    void packageAndSend(TradeLog tradeLog);
}
