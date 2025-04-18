package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.RoomInfo;
import com.lz.manage.service.IRoomInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RoomCommentInfoMapper;
import com.lz.manage.model.domain.RoomCommentInfo;
import com.lz.manage.service.IRoomCommentInfoService;
import com.lz.manage.model.dto.roomCommentInfo.RoomCommentInfoQuery;
import com.lz.manage.model.vo.roomCommentInfo.RoomCommentInfoVo;

/**
 * 房间评价Service业务层处理
 *
 * @author YY
 * @date 2025-04-17
 */
@Service
public class RoomCommentInfoServiceImpl extends ServiceImpl<RoomCommentInfoMapper, RoomCommentInfo> implements IRoomCommentInfoService {
    @Resource
    private RoomCommentInfoMapper roomCommentInfoMapper;

    @Resource
    private IRoomInfoService roomInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询房间评价
     *
     * @param id 房间评价主键
     * @return 房间评价
     */
    @Override
    public RoomCommentInfo selectRoomCommentInfoById(Long id) {
        return roomCommentInfoMapper.selectRoomCommentInfoById(id);
    }

    /**
     * 查询房间评价列表
     *
     * @param roomCommentInfo 房间评价
     * @return 房间评价
     */
    @Override
    public List<RoomCommentInfo> selectRoomCommentInfoList(RoomCommentInfo roomCommentInfo) {
        List<RoomCommentInfo> roomCommentInfos = roomCommentInfoMapper.selectRoomCommentInfoList(roomCommentInfo);
        for (RoomCommentInfo info : roomCommentInfos) {
            SysUser user = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(info.getRoomId());
            if (StringUtils.isNotNull(roomInfo)) {
                info.setRoomName(roomInfo.getRoomName());
            }
        }
        return roomCommentInfos;
    }

    /**
     * 新增房间评价
     *
     * @param roomCommentInfo 房间评价
     * @return 结果
     */
    @Override
    public int insertRoomCommentInfo(RoomCommentInfo roomCommentInfo) {
        //查询房间是否存在
        RoomInfo roomInfo = roomInfoService.selectRoomInfoByRoomId(roomCommentInfo.getRoomId());
        if (StringUtils.isNull(roomInfo)) {
            throw new ServiceException("房间不存在！！！");
        }
        roomInfo.setUserId(SecurityUtils.getUserId());
        roomCommentInfo.setCreateTime(DateUtils.getNowDate());
        return roomCommentInfoMapper.insertRoomCommentInfo(roomCommentInfo);
    }

    /**
     * 修改房间评价
     *
     * @param roomCommentInfo 房间评价
     * @return 结果
     */
    @Override
    public int updateRoomCommentInfo(RoomCommentInfo roomCommentInfo) {
        roomCommentInfo.setUpdateTime(DateUtils.getNowDate());
        return roomCommentInfoMapper.updateRoomCommentInfo(roomCommentInfo);
    }

    /**
     * 批量删除房间评价
     *
     * @param ids 需要删除的房间评价主键
     * @return 结果
     */
    @Override
    public int deleteRoomCommentInfoByIds(Long[] ids) {
        return roomCommentInfoMapper.deleteRoomCommentInfoByIds(ids);
    }

    /**
     * 删除房间评价信息
     *
     * @param id 房间评价主键
     * @return 结果
     */
    @Override
    public int deleteRoomCommentInfoById(Long id) {
        return roomCommentInfoMapper.deleteRoomCommentInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<RoomCommentInfo> getQueryWrapper(RoomCommentInfoQuery roomCommentInfoQuery) {
        QueryWrapper<RoomCommentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = roomCommentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = roomCommentInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long roomId = roomCommentInfoQuery.getRoomId();
        queryWrapper.eq(StringUtils.isNotNull(roomId), "room_id", roomId);

        Long userId = roomCommentInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long score = roomCommentInfoQuery.getScore();
        queryWrapper.eq(StringUtils.isNotNull(score), "score", score);

        String content = roomCommentInfoQuery.getContent();
        queryWrapper.eq(StringUtils.isNotEmpty(content), "content", content);

        String imageUrls = roomCommentInfoQuery.getImageUrls();
        queryWrapper.eq(StringUtils.isNotEmpty(imageUrls), "image_urls", imageUrls);

        Date createTime = roomCommentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "createTime", params.get("beginCreateTime"), params.get("endCreateTime"));

        Date updateTime = roomCommentInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "updateTime", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<RoomCommentInfoVo> convertVoList(List<RoomCommentInfo> roomCommentInfoList) {
        if (StringUtils.isEmpty(roomCommentInfoList)) {
            return Collections.emptyList();
        }
        return roomCommentInfoList.stream().map(RoomCommentInfoVo::objToVo).collect(Collectors.toList());
    }

}
