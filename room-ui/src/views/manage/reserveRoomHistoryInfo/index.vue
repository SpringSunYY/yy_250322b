<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录编号" prop="historyId">
        <el-input
          v-model="queryParams.historyId"
          placeholder="请输入记录编号"
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
      <el-form-item label="状态" prop="historyStatus">
        <el-select v-model="queryParams.historyStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.r_reverve_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订房时间">
        <el-date-picker
          v-model="daterangeReserveTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
      <!--          v-hasPermi="['manage:reserveRoomHistoryInfo:add']"-->
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
          v-hasPermi="['manage:reserveRoomHistoryInfo:edit']"
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
          v-hasPermi="['manage:reserveRoomHistoryInfo:remove']"
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
          v-hasPermi="['manage:reserveRoomHistoryInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reserveRoomHistoryInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="记录编号" align="center" v-if="columns[0].visible" prop="historyId"/>
      <el-table-column label="房间编号" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="roomName"
      />
      <el-table-column label="用户" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="userName"
      />
      <el-table-column label="订房天数" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="dayNum"
      />
      <el-table-column label="订单总价" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="totalPrice"
      />
      <el-table-column label="状态" align="center" v-if="columns[5].visible" prop="historyStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.r_reverve_status" :value="scope.row.historyStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="订房时间" align="center" v-if="columns[6].visible" prop="reserveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reserveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" v-if="columns[7].visible" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[10].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handlePay(scope.row)"
            v-if="scope.row.historyStatus==='0'"
            v-hasPermi="['manage:payHistoryInfo:add']"
          >支付
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:reserveRoomHistoryInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleReturn(scope.row)"
            v-hasPermi="['manage:reserveRoomHistoryInfo:edit']"
          >退房
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:reserveRoomHistoryInfo:remove']"
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

    <!-- 添加或修改订房记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订房天数" prop="dayNum">
          <el-input readonly v-model="form.dayNum" placeholder="请输入订房天数"/>
        </el-form-item>
        <el-form-item label="订单总价" prop="totalPrice">
          <el-input readonly v-model="form.totalPrice" placeholder="请输入订单总价"/>
        </el-form-item>
        <el-form-item label="状态" prop="historyStatus">
          <el-radio-group disabled v-model="form.historyStatus">
            <el-radio
              v-for="dict in dict.type.r_reverve_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="订房时间" prop="reserveTime">
          <el-date-picker readonly clearable
                          v-model="form.reserveTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订房时间"
          >
          </el-date-picker>
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

    <!-- 添加或修改支付记录对话框 -->
    <el-dialog :title="title" :visible.sync="payOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="支付金额" prop="payPrice">-->
        <!--          <el-input-number :min="0" :precision="2" v-model="form.payPrice" placeholder="请输入实际支付金额"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="支付凭证" prop="payVoucher">-->
        <!--          <image-upload :file-size="10" :limit="5" v-model="form.payVoucher"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPayForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listReserveRoomHistoryInfo,
  getReserveRoomHistoryInfo,
  delReserveRoomHistoryInfo,
  addReserveRoomHistoryInfo,
  updateReserveRoomHistoryInfo
} from '@/api/manage/reserveRoomHistoryInfo'
import { addPayHistoryInfo } from '@/api/manage/payHistoryInfo'

export default {
  name: 'ReserveRoomHistoryInfo',
  dicts: ['r_reverve_status'],
  data() {
    return {
      //打开支付
      payOpen: false,
      //表格展示列
      columns: [
        { key: 0, label: '记录编号', visible: false },
        { key: 1, label: '房间编号', visible: true },
        { key: 2, label: '用户', visible: true },
        { key: 3, label: '订房天数', visible: true },
        { key: 4, label: '订单总价', visible: true },
        { key: 5, label: '状态', visible: true },
        { key: 6, label: '订房时间', visible: true },
        { key: 7, label: '订房时间', visible: true },
        { key: 8, label: '创建时间', visible: false },
        { key: 9, label: '更新人', visible: false },
        { key: 10, label: '更新时间', visible: false },
        { key: 11, label: '备注', visible: false }
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
      // 订房记录表格数据
      reserveRoomHistoryInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeReserveTime: [],
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        historyId: null,
        roomId: null,
        userId: null,
        historyStatus: null,
        reserveTime: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomId: [
          { required: true, message: '房间编号不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '用户不能为空', trigger: 'blur' }
        ],
        dayNum: [
          { required: true, message: '订房天数不能为空', trigger: 'blur' }
        ],
        totalPrice: [
          { required: true, message: '订单总价不能为空', trigger: 'blur' }
        ],
        historyStatus: [
          { required: true, message: '状态不能为空', trigger: 'change' }
        ],
        reserveTime: [
          { required: true, message: '订房时间不能为空', trigger: 'blur' }
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
    //退房
    handleReturn(row) {
      if (row.historyStatus === 2 || row.historyStatus === 3) {
        this.$modal.msgError('当前状态不可退房')
        return
      }
      const that = this
      row.historyStatus = 2
      this.$modal.confirm('是否确认退房？').then(function() {
        updateReserveRoomHistoryInfo(row).then(res => {
          that.$modal.msgSuccess('退房成功')
          that.getList()
        })
      }).catch(function() {
      }).finally(function() {

      })
    },
    handlePay(row) {
      this.reset()
      this.title = '支付订单--' + row.roomName
      this.payOpen = true
      this.form.userId = row.userId
      this.form.reserveId = row.historyId
    },
    submitPayForm() {
      addPayHistoryInfo(this.form).then(res => {
        this.$modal.msgSuccess('支付成功')
        this.payOpen = false
        this.getList()
      })
    },
    /** 查询订房记录列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeReserveTime && '' != this.daterangeReserveTime) {
        this.queryParams.params['beginReserveTime'] = this.daterangeReserveTime[0]
        this.queryParams.params['endReserveTime'] = this.daterangeReserveTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listReserveRoomHistoryInfo(this.queryParams).then(response => {
        this.reserveRoomHistoryInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.payOpen = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        historyId: null,
        roomId: null,
        userId: null,
        dayNum: null,
        totalPrice: null,
        historyStatus: null,
        reserveTime: null,
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
      this.daterangeReserveTime = []
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
      this.title = '添加订房记录'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const historyId = row.historyId || this.ids
      getReserveRoomHistoryInfo(historyId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改订房记录'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.historyId != null) {
            updateReserveRoomHistoryInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addReserveRoomHistoryInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除订房记录编号为"' + historyIds + '"的数据项？').then(function() {
        return delReserveRoomHistoryInfo(historyIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/reserveRoomHistoryInfo/export', {
        ...this.queryParams
      }, `reserveRoomHistoryInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
