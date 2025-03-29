package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.ReserveRoomHistoryInfo;
import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.model.enums.RPayStatus;
import com.lz.manage.service.IReserveRoomHistoryInfoService;
import com.lz.manage.service.IRoomInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PayHistoryInfoMapper;
import com.lz.manage.model.domain.PayHistoryInfo;
import com.lz.manage.service.IPayHistoryInfoService;
import com.lz.manage.model.dto.payHistoryInfo.PayHistoryInfoQuery;
import com.lz.manage.model.vo.payHistoryInfo.PayHistoryInfoVo;

/**
 * 支付记录Service业务层处理
 *
 * @author YY
 * @date 2025-03-29
 */
@Service
public class PayHistoryInfoServiceImpl extends ServiceImpl<PayHistoryInfoMapper, PayHistoryInfo> implements IPayHistoryInfoService {
    @Resource
    private PayHistoryInfoMapper payHistoryInfoMapper;

    @Resource
    private IRoomInfoService roomInfoService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IReserveRoomHistoryInfoService reserveRoomHistoryInfoService;

    //region mybatis代码

    /**
     * 查询支付记录
     *
     * @param historyId 支付记录主键
     * @return 支付记录
     */
    @Override
    public PayHistoryInfo selectPayHistoryInfoByHistoryId(Long historyId) {
        return payHistoryInfoMapper.selectPayHistoryInfoByHistoryId(historyId);
    }

    /**
     * 查询支付记录列表
     *
     * @param payHistoryInfo 支付记录
     * @return 支付记录
     */
    @Override
    public List<PayHistoryInfo> selectPayHistoryInfoList(PayHistoryInfo payHistoryInfo) {
        List<PayHistoryInfo> payHistoryInfos = payHistoryInfoMapper.selectPayHistoryInfoList(payHistoryInfo);
        for (PayHistoryInfo info : payHistoryInfos) {
            SysUser user = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(info.getRoomId());
            if (StringUtils.isNotNull(roomInfo)) {
                info.setRoomName(roomInfo.getRoomName());
            }
        }
        return payHistoryInfos;
    }

    /**
     * 新增支付记录
     *
     * @param payHistoryInfo 支付记录
     * @return 结果
     */
    @Override
    public int insertPayHistoryInfo(PayHistoryInfo payHistoryInfo) {
        //查询订单信息
        ReserveRoomHistoryInfo reserveRoomHistoryInfo = reserveRoomHistoryInfoService.selectReserveRoomHistoryInfoByHistoryId(payHistoryInfo.getReserveId());
        if (StringUtils.isNull(reserveRoomHistoryInfo)) {
            throw new ServiceException("订房信息不存在！！！");
        }
        //查询是否已经支付
        List<PayHistoryInfo> list = this.list(new LambdaQueryWrapper<>(PayHistoryInfo.class).eq(PayHistoryInfo::getReserveId, payHistoryInfo.getReserveId()));
        if (StringUtils.isNotEmpty(list)) {
            throw new ServiceException("该订单已经支付！！！");
        }
        payHistoryInfo.setUserId(reserveRoomHistoryInfo.getUserId());
        payHistoryInfo.setRoomId(reserveRoomHistoryInfo.getRoomId());
        payHistoryInfo.setAuditStatus(Long.parseLong(RPayStatus.PAY_STATUS_0.getValue()));
        payHistoryInfo.setCreateTime(DateUtils.getNowDate());
        return payHistoryInfoMapper.insertPayHistoryInfo(payHistoryInfo);
    }

    /**
     * 修改支付记录
     *
     * @param payHistoryInfo 支付记录
     * @return 结果
     */
    @Override
    public int updatePayHistoryInfo(PayHistoryInfo payHistoryInfo) {
        payHistoryInfo.setUpdateTime(DateUtils.getNowDate());
        return payHistoryInfoMapper.updatePayHistoryInfo(payHistoryInfo);
    }

    /**
     * 批量删除支付记录
     *
     * @param historyIds 需要删除的支付记录主键
     * @return 结果
     */
    @Override
    public int deletePayHistoryInfoByHistoryIds(Long[] historyIds) {
        return payHistoryInfoMapper.deletePayHistoryInfoByHistoryIds(historyIds);
    }

    /**
     * 删除支付记录信息
     *
     * @param historyId 支付记录主键
     * @return 结果
     */
    @Override
    public int deletePayHistoryInfoByHistoryId(Long historyId) {
        return payHistoryInfoMapper.deletePayHistoryInfoByHistoryId(historyId);
    }

    //endregion
    @Override
    public QueryWrapper<PayHistoryInfo> getQueryWrapper(PayHistoryInfoQuery payHistoryInfoQuery) {
        QueryWrapper<PayHistoryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = payHistoryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long historyId = payHistoryInfoQuery.getHistoryId();
        queryWrapper.eq(StringUtils.isNotNull(historyId), "history_id", historyId);

        Long reserveId = payHistoryInfoQuery.getReserveId();
        queryWrapper.eq(StringUtils.isNotNull(reserveId), "reserve_id", reserveId);

        Long roomId = payHistoryInfoQuery.getRoomId();
        queryWrapper.eq(StringUtils.isNotNull(roomId), "room_id", roomId);

        Long userId = payHistoryInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long auditStatus = payHistoryInfoQuery.getAuditStatus();
        queryWrapper.eq(StringUtils.isNotNull(auditStatus), "audit_status", auditStatus);

        Date createTime = payHistoryInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = payHistoryInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = payHistoryInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<PayHistoryInfoVo> convertVoList(List<PayHistoryInfo> payHistoryInfoList) {
        if (StringUtils.isEmpty(payHistoryInfoList)) {
            return Collections.emptyList();
        }
        return payHistoryInfoList.stream().map(PayHistoryInfoVo::objToVo).collect(Collectors.toList());
    }

}
