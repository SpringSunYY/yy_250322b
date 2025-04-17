package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.UserBalanceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户余额Mapper接口
 * 
 * @author YY
 * @date 2025-04-17
 */
public interface UserBalanceInfoMapper extends BaseMapper<UserBalanceInfo>
{
    /**
     * 查询用户余额
     * 
     * @param id 用户余额主键
     * @return 用户余额
     */
    public UserBalanceInfo selectUserBalanceInfoById(Long id);

    /**
     * 查询用户余额列表
     * 
     * @param userBalanceInfo 用户余额
     * @return 用户余额集合
     */
    public List<UserBalanceInfo> selectUserBalanceInfoList(UserBalanceInfo userBalanceInfo);

    /**
     * 新增用户余额
     * 
     * @param userBalanceInfo 用户余额
     * @return 结果
     */
    public int insertUserBalanceInfo(UserBalanceInfo userBalanceInfo);

    /**
     * 修改用户余额
     * 
     * @param userBalanceInfo 用户余额
     * @return 结果
     */
    public int updateUserBalanceInfo(UserBalanceInfo userBalanceInfo);

    /**
     * 删除用户余额
     * 
     * @param id 用户余额主键
     * @return 结果
     */
    public int deleteUserBalanceInfoById(Long id);

    /**
     * 批量删除用户余额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserBalanceInfoByIds(Long[] ids);
}
