package cc.mrbird.febs.common.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_trade_log")
public class TradeLog implements Serializable {
    private static final long serialVersionUID = -7339162420374701219L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long od;

    @TableField("GOODS_ID")
    private int goodsId;

    @TableField("GOODS_NAME")
    private String goodsName;

    @TableField("STATUS")
    private String status;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("TRANSACTION_ID")
    private String transactionId;
}
