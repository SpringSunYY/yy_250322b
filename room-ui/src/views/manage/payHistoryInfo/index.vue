<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="支付记录编号" prop="historyId">
        <el-input
          v-model="queryParams.historyId"
          placeholder="请输入支付记录编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预定编号" prop="reserveId">
        <el-input
          v-model="queryParams.reserveId"
          placeholder="请输入预定编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房间编号" prop="roomId">
        <el-input
          v-model="queryParams.roomId"
          placeholder="请输入房间编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in dict.type.r_pay_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['manage:payHistoryInfo:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:payHistoryInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:payHistoryInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:payHistoryInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payHistoryInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="支付记录编号" align="center" v-if="columns[0].visible" prop="historyId"/>
      <el-table-column label="预定编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="reserveId"
      />
      <el-table-column label="房间编号" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="roomName"
      />
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="userName"
      />
      <el-table-column label="实际支付金额" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="payPrice"
      />
      <el-table-column label="支付凭证" align="center" v-if="columns[5].visible" prop="payVoucher" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.payVoucher" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" v-if="columns[6].visible" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.r_pay_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" v-if="columns[7].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[9].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:payHistoryInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:payHistoryInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改支付记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预定编号" prop="reserveId">
          <el-input readonly v-model="form.reserveId" placeholder="请输入预定编号"/>
        </el-form-item>
        <!--        <el-form-item label="房间编号" prop="roomId">-->
        <!--          <el-input v-model="form.roomId" placeholder="请输入房间编号"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="用户" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入用户"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="实际支付金额" prop="payPrice">
          <el-input v-model="form.payPrice" placeholder="请输入实际支付金额"/>
        </el-form-item>
        <el-form-item label="支付凭证" prop="payVoucher">
          <image-upload v-model="form.payVoucher"/>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-radio-group :disabled="!checkPermi(['manage:payhistory:audit'])" v-model="form.auditStatus">
            <el-radio
              v-for="dict in dict.type.r_pay_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPayHistoryInfo,
  getPayHistoryInfo,
  delPayHistoryInfo,
  addPayHistoryInfo,
  updatePayHistoryInfo
} from '@/api/manage/payHistoryInfo'
import { checkPermi } from '@/utils/permission'

export default {
  name: 'PayHistoryInfo',
  dicts: ['r_pay_status'],
  data() {
    return {
      //表格展示列
      columns: [
        { key: 0, label: '支付记录编号', visible: false },
        { key: 1, label: '预定编号', visible: true },
        { key: 2, label: '房间编号', visible: true },
        { key: 3, label: '用户', visible: true },
        { key: 4, label: '实际支付金额', visible: true },
        { key: 5, label: '支付凭证', visible: true },
        { key: 6, label: '审核状态', visible: true },
        { key: 7, label: '创建时间', visible: false },
        { key: 8, label: '更新人', visible: false },
        { key: 9, label: '更新时间', visible: false },
        { key: 10, label: '备注', visible: false }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 支付记录表格数据
      payHistoryInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        historyId: null,
        reserveId: null,
        roomId: null,
        userId: null,
        auditStatus: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        reserveId: [
          { required: true, message: '预定编号不能为空', trigger: 'blur' }
        ],
        roomId: [
          { required: true, message: '房间编号不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '用户不能为空', trigger: 'blur' }
        ],
        payPrice: [
          { required: true, message: '实际支付金额不能为空', trigger: 'blur' }
        ],
        payVoucher: [
          { required: true, message: '支付凭证不能为空', trigger: 'blur' }
        ],
        auditStatus: [
          { required: true, message: '审核状态不能为空', trigger: 'change' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    checkPermi,
    /** 查询支付记录列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listPayHistoryInfo(this.queryParams).then(response => {
        this.payHistoryInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        historyId: null,
        reserveId: null,
        roomId: null,
        userId: null,
        payPrice: null,
        payVoucher: null,
        auditStatus: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.historyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加支付记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const historyId = row.historyId || this.ids
      getPayHistoryInfo(historyId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改支付记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.historyId != null) {
            updatePayHistoryInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPayHistoryInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const historyIds = row.historyId || this.ids
      this.$modal.confirm('是否确认删除支付记录编号为"' + historyIds + '"的数据项？').then(function() {
        return delPayHistoryInfo(historyIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/payHistoryInfo/export', {
        ...this.queryParams
      }, `payHistoryInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
