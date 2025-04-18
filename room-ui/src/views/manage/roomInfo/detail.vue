<template>
  <div class="room-detail">
    <!-- 房间轮播图 -->
    <el-carousel :interval="3000" type="card" height="400px" v-if="roomImages.length">
      <el-carousel-item v-for="(img, index) in roomImages" :key="index">
        <el-image :src="img" fit="cover" style="width: 100%; height: 100%"/>
      </el-carousel-item>
    </el-carousel>

    <!-- 房间信息 -->
    <el-card class="room-info-card" shadow="hover">
      <h2>{{ roomInfo.roomName }}</h2>
      <p><strong>价格：</strong> ¥{{ roomInfo.roomPrice }}</p>
      <p><strong>描述：</strong> {{ roomInfo.roomDesc }}</p>
      <p><strong>订房次数：</strong> {{ roomInfo.reserveNum }}</p>
      <p><strong>备注：</strong> {{ roomInfo.remark }}</p>
      <p><strong>
        <el-button type="text" @click="handleReserve">立即预定</el-button>
      </strong></p>
    </el-card>
    <!-- 添加或修改订房记录对话框 -->
    <el-dialog :title="title" :visible.sync="openReserve" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
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
    <!-- 房间评论 -->
    <div class="comment-section">
      <h3>用户评论</h3>
      <el-empty description="暂无评论" v-if="comments.length === 0"/>
      <el-card class="comment-card" v-for="comment in comments" :key="comment.id" shadow="never">
        <div class="comment-header">
          <el-rate :value="comment.score" disabled/>
          <span class="comment-time">{{ comment.createTime }}</span>
        </div>
        <div class="comment-content">{{ comment.content }}</div>
        <div class="comment-images" v-if="comment.imageUrls">
          <el-image
            v-for="(img, index) in comment.imageUrls.split(';')"
            :key="index"
            :src="img"
            :preview-src-list="comment.imageUrls.split(';')"
            :initial-index="index"
            fit="cover"
            style="width: 100px; height: 100px; margin-right: 10px"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { getRoomCommentInfo } from '@/api/manage/roomCommentInfo'
import { getRoomInfo } from '@/api/manage/roomInfo'
import { addReserveRoomHistoryInfo } from '@/api/manage/reserveRoomHistoryInfo'

export default {
  name: 'RoomDetail',
  data() {
    return {
      openReserve: false,
      form: {
        roomId: '',
        userId: '',
        reserveTime: '',
        payTime: '',
        payStatus: '',
        payType: '',
        payAmount: '',
        remark: ''
      },
      baseUrl: process.env.VUE_APP_BASE_API,
      roomId: 1, // 实际使用中通过路由或父组件传入
      roomInfo: {
        roomName: '',
        roomPrice: 0,
        roomDesc: '',
        roomImage: '',
        roomStatus: 0,
        reserveNum: 0,
        remark: ''
      },
      roomImages: [],
      comments: []
    }
  },

  created() {
    const roomId = this.$route.params && this.$route.params.roomId
    this.form.roomId = roomId
    this.getRoomInfo(roomId)
  },
  methods: {
    //预定房间
    handleReserve() {
      this.title = '预定房间--' + this.roomInfo.roomName
      this.openReserve = true
    },
    //提交预定房间
    submitReserveForm() {
      addReserveRoomHistoryInfo(this.form).then(res => {
        this.$modal.msgSuccess('预定成功,请在十五分钟内立即支付')
        this.openReserve = false
        this.getRoomInfo(this.form.roomId)
      })
    },
    // 取消按钮
    cancel() {
      this.openReserve = false
    },
    getRoomInfo(id) {
      getRoomInfo(id).then(response => {
        this.roomInfo = response.data
        this.roomImages = this.roomInfo.roomImage.split(',').map(url => {
          console.log(this.baseUrl + url)
          return this.baseUrl + url
        })
      })
    },
    fetchRoomDetail() {
      // 模拟数据请求
      this.roomInfo = {
        roomName: '豪华大床房',
        roomPrice: 388.00,
        roomDesc: '带阳台、海景房，双人床，带早餐',
        roomImage: 'https://example.com/1.jpg;https://example.com/2.jpg;https://example.com/3.jpg',
        roomStatus: 0,
        reserveNum: 15,
        remark: '限时特惠'
      }
      this.roomImages = this.roomInfo.roomImage.split(';')
    },
    fetchRoomComments() {
      // 模拟评论数据
      this.comments = [
        {
          id: 1,
          score: 4,
          content: '房间挺大的，风景很好，性价比高！',
          imageUrls: 'https://example.com/c1.jpg;https://example.com/c2.jpg',
          createTime: '2025-04-17 10:25:00'
        },
        {
          id: 2,
          score: 5,
          content: '很满意的一次入住，服务也好~',
          imageUrls: '',
          createTime: '2025-04-15 14:50:00'
        }
      ]
    }
  }
}
</script>

<style scoped>
.room-detail {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.room-info-card {
  margin-top: 20px;
}

.comment-section {
  margin-top: 40px;
}

.comment-card {
  margin-bottom: 20px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.comment-content {
  margin-bottom: 10px;
}

.comment-images {
  display: flex;
  flex-wrap: wrap;
}
</style>
