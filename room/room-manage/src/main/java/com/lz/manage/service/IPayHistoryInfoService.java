package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.PayHistoryInfo;
import com.lz.manage.model.vo.payHistoryInfo.PayHistoryInfoVo;
import com.lz.manage.model.dto.payHistoryInfo.PayHistoryInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 支付记录Service接口
 * 
 * @author YY
 * @date 2025-03-29
 */
public interface IPayHistoryInfoService extends IService<PayHistoryInfo>
{
    //region mybatis代码
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
     * 批量删除支付记录
     * 
     * @param historyIds 需要删除的支付记录主键集合
     * @return 结果
     */
    public int deletePayHistoryInfoByHistoryIds(Long[] historyIds);

    /**
     * 删除支付记录信息
     * 
     * @param historyId 支付记录主键
     * @return 结果
     */
    public int deletePayHistoryInfoByHistoryId(Long historyId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param payHistoryInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PayHistoryInfo> getQueryWrapper(PayHistoryInfoQuery payHistoryInfoQuery);

    /**
     * 转换vo
     *
     * @param payHistoryInfoList PayHistoryInfo集合
     * @return PayHistoryInfoVO集合
     */
    List<PayHistoryInfoVo> convertVoList(List<PayHistoryInfo> payHistoryInfoList);
}
