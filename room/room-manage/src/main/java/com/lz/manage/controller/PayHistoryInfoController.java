package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.PayHistoryInfo;
import com.lz.manage.model.vo.payHistoryInfo.PayHistoryInfoVo;
import com.lz.manage.model.dto.payHistoryInfo.PayHistoryInfoQuery;
import com.lz.manage.model.dto.payHistoryInfo.PayHistoryInfoInsert;
import com.lz.manage.model.dto.payHistoryInfo.PayHistoryInfoEdit;
import com.lz.manage.service.IPayHistoryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 支付记录Controller
 *
 * @author YY
 * @date 2025-03-29
 */
@RestController
@RequestMapping("/manage/payHistoryInfo")
public class PayHistoryInfoController extends BaseController
{
    @Resource
    private IPayHistoryInfoService payHistoryInfoService;

    /**
     * 查询支付记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PayHistoryInfoQuery payHistoryInfoQuery)
    {
        PayHistoryInfo payHistoryInfo = PayHistoryInfoQuery.queryToObj(payHistoryInfoQuery);
        startPage();
        List<PayHistoryInfo> list = payHistoryInfoService.selectPayHistoryInfoList(payHistoryInfo);
        List<PayHistoryInfoVo> listVo= list.stream().map(PayHistoryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出支付记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:export')")
    @Log(title = "支付记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PayHistoryInfoQuery payHistoryInfoQuery)
    {
        PayHistoryInfo payHistoryInfo = PayHistoryInfoQuery.queryToObj(payHistoryInfoQuery);
        List<PayHistoryInfo> list = payHistoryInfoService.selectPayHistoryInfoList(payHistoryInfo);
        ExcelUtil<PayHistoryInfo> util = new ExcelUtil<PayHistoryInfo>(PayHistoryInfo.class);
        util.exportExcel(response, list, "支付记录数据");
    }

    /**
     * 获取支付记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:query')")
    @GetMapping(value = "/{historyId}")
    public AjaxResult getInfo(@PathVariable("historyId") Long historyId)
    {
        PayHistoryInfo payHistoryInfo = payHistoryInfoService.selectPayHistoryInfoByHistoryId(historyId);
        return success(PayHistoryInfoVo.objToVo(payHistoryInfo));
    }

    /**
     * 新增支付记录
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:add')")
    @Log(title = "支付记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PayHistoryInfoInsert payHistoryInfoInsert)
    {
        PayHistoryInfo payHistoryInfo = PayHistoryInfoInsert.insertToObj(payHistoryInfoInsert);
        return toAjax(payHistoryInfoService.insertPayHistoryInfo(payHistoryInfo));
    }

    /**
     * 修改支付记录
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:edit')")
    @Log(title = "支付记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PayHistoryInfoEdit payHistoryInfoEdit)
    {
        PayHistoryInfo payHistoryInfo = PayHistoryInfoEdit.editToObj(payHistoryInfoEdit);
        return toAjax(payHistoryInfoService.updatePayHistoryInfo(payHistoryInfo));
    }

    /**
     * 删除支付记录
     */
    @PreAuthorize("@ss.hasPermi('manage:payHistoryInfo:remove')")
    @Log(title = "支付记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{historyIds}")
    public AjaxResult remove(@PathVariable Long[] historyIds)
    {
        return toAjax(payHistoryInfoService.deletePayHistoryInfoByHistoryIds(historyIds));
    }
}
