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
    </el-card>

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

export default {
  name: 'RoomDetail',
  data() {
    return {
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
      comments: [],
    }
  },

  created() {
    const roomId = this.$route.params && this.$route.params.roomId
    this.getRoomInfo(roomId)
  },
  methods: {
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
