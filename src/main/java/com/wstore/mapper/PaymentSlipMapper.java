package com.wstore.mapper;

import com.wstore.pojo.pay.PaymentSlip;
import com.wstore.pojo.pay.PaymentSlipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentSlipMapper {
    long countByExample(PaymentSlipExample example);

    int deleteByExample(PaymentSlipExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PaymentSlip record);

    int insertSelective(PaymentSlip record);

    List<PaymentSlip> selectByExample(PaymentSlipExample example);

    PaymentSlip selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PaymentSlip record, @Param("example") PaymentSlipExample example);

    int updateByExample(@Param("record") PaymentSlip record, @Param("example") PaymentSlipExample example);

    int updateByPrimaryKeySelective(PaymentSlip record);

    int updateByPrimaryKey(PaymentSlip record);
}