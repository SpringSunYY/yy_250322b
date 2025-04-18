<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="房间编号" prop="roomId">
        <el-input
          v-model="queryParams.roomId"
          placeholder="请输入房间编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房间名称" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          placeholder="请输入房间名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房间状态" prop="roomStatus">
        <el-select v-model="queryParams.roomStatus" placeholder="请选择房间状态" clearable>
          <el-option
            v-for="dict in dict.type.r_room_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
<!--      <el-form-item label="更新人" prop="updateBy">-->
<!--        <el-input-->
<!--          v-model="queryParams.updateBy"-->
<!--          placeholder="请输入更新人"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="更新时间">-->
<!--        <el-date-picker-->
<!--          v-model="daterangeUpdateTime"-->
<!--          style="width: 240px"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:roomInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:roomInfo:edit']"
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
          v-hasPermi="['manage:roomInfo:remove']"
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
          v-hasPermi="['manage:roomInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roomInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房间编号" align="center" v-if="columns[0].visible" prop="roomId"/>
      <el-table-column label="房间名称" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="roomName"
      />
      <el-table-column label="房间价格" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="roomPrice"
      />
      <el-table-column label="房间描述" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="roomDesc"
      />
      <el-table-column label="房间图片" align="center" v-if="columns[4].visible" prop="roomImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.roomImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="房间状态" align="center" v-if="columns[5].visible" prop="roomStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.r_room_status" :value="scope.row.roomStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="订房次数" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="reserveNum"
      />
      <el-table-column label="创建人" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="userName"
      />
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
            @click="handleReserve(scope.row)"
            v-hasPermi="['manage:reserveRoomHistoryInfo:add']"
          >预定
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:roomInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:roomInfo:remove']"
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

    <!-- 添加或修改酒店房间信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="房间名称" prop="roomName">
          <el-input v-model="form.roomName" placeholder="请输入房间名称"/>
        </el-form-item>
        <el-form-item label="房间价格" prop="roomPrice">
          <el-input-number :min="0" :precision="2" v-model="form.roomPrice" placeholder="请输入房间价格"/>
        </el-form-item>
        <el-form-item label="房间描述" prop="roomDesc">
          <el-input v-model="form.roomDesc" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="房间图片" prop="roomImage">
          <image-upload v-model="form.roomImage"/>
        </el-form-item>
        <el-form-item label="房间状态" prop="roomStatus">
          <el-radio-group v-model="form.roomStatus">
            <el-radio
              v-for="dict in dict.type.r_room_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="订房次数" prop="reserveNum">-->
        <!--          <el-input v-model="form.reserveNum" placeholder="请输入订房次数" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="创建人" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入创建人" />-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改订房记录对话框 -->
    <el-dialog :title="title" :visible.sync="openReserve" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订房天数" prop="dayNum">
          <el-input-number :min="1" v-model="form.dayNum" placeholder="请输入订房天数"/>
        </el-form-item>
        <el-form-item label="订房时间" prop="reserveTime">
          <el-date-picker clearable
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
        <el-button type="primary" @click="submitReserveForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRoomInfo, getRoomInfo, delRoomInfo, addRoomInfo, updateRoomInfo } from '@/api/manage/roomInfo'
import { addReserveRoomHistoryInfo } from '@/api/manage/reserveRoomHistoryInfo'

export default {
  name: 'RoomInfo',
  dicts: ['r_room_status'],
  data() {
    return {
      openReserve: false,
      //表格展示列
      columns: [
        { key: 0, label: '房间编号', visible: false },
        { key: 1, label: '房间名称', visible: true },
        { key: 2, label: '房间价格', visible: true },
        { key: 3, label: '房间描述', visible: true },
        { key: 4, label: '房间图片', visible: true },
        { key: 5, label: '房间状态', visible: true },
        { key: 6, label: '订房次数', visible: true },
        { key: 7, label: '创建人', visible: false },
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
      // 酒店房间信息表格数据
      roomInfoList: [],
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
        roomId: null,
        roomName: null,
        roomStatus: null,
        userId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomName: [
          { required: true, message: '房间名称不能为空', trigger: 'blur' }
        ],
        roomPrice: [
          { required: true, message: '房间价格不能为空', trigger: 'blur' }
        ],
        roomStatus: [
          { required: true, message: '房间状态不能为空', trigger: 'change' }
        ],
        reserveNum: [
          { required: true, message: '订房次数不能为空', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
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
    //预定房间
    handleReserve(row) {
      this.title = '预定房间--' + row.roomName
      this.reset()
      this.form.roomId = row.roomId
      this.openReserve = true
    },
    //提交预定房间
    submitReserveForm() {
      addReserveRoomHistoryInfo(this.form).then(res => {
        this.$modal.msgSuccess('预定成功,请在十五分钟内立即支付')
        this.openReserve = false
        this.getList()
      })
    },
    /** 查询酒店房间信息列表 */
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
      listRoomInfo(this.queryParams).then(response => {
        this.roomInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openReserve = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        roomId: null,
        roomName: null,
        roomPrice: null,
        roomDesc: null,
        roomImage: null,
        roomStatus: null,
        reserveNum: null,
        userId: null,
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
      this.ids = selection.map(item => item.roomId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加酒店房间信息'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const roomId = row.roomId || this.ids
      getRoomInfo(roomId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改酒店房间信息'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.roomId != null) {
            updateRoomInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRoomInfo(this.form).then(response => {
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
      const roomIds = row.roomId || this.ids
      this.$modal.confirm('是否确认删除酒店房间信息编号为"' + roomIds + '"的数据项？').then(function() {
        return delRoomInfo(roomIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/roomInfo/export', {
        ...this.queryParams
      }, `roomInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
