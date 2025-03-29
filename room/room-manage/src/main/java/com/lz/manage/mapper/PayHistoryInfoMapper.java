package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PayHistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 支付记录Mapper接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface PayHistoryInfoMapper extends BaseMapper<PayHistoryInfo>
{
    /**
     * 查询支付记录
     * 
     * @param historyId 支付记录主键
     * @return 支付记录
     */
    public PayHistoryInfo selectPayHistoryInfoByHistoryId(Long historyId);

    /**
     * 查询支付记录列表
     * 
     * @param payHistoryInfo 支付记录
     * @return 支付记录集合
     */
    public List<PayHistoryInfo> selectPayHistoryInfoList(PayHistoryInfo payHistoryInfo);

    /**
     * 新增支付记录
     * 
     * @param payHistoryInfo 支付记录
     * @return 结果
     */
    public int insertPayHistoryInfo(PayHistoryInfo payHistoryInfo);

    /**
     * 修改支付记录
     * 
     * @param payHistoryInfo 支付记录
     * @return 结果
     */
    public int updatePayHistoryInfo(PayHistoryInfo payHistoryInfo);

    /**
     * 删除支付记录
     * 
     * @param historyId 支付记录主键
     * @return 结果
     */
    public int deletePayHistoryInfoByHistoryId(Long historyId);

    /**
     * 批量删除支付记录
     * 
     * @param historyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayHistoryInfoByHistoryIds(Long[] historyIds);
}
