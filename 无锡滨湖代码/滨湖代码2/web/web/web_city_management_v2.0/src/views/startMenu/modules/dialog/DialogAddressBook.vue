<template>
  <el-dialog
    title="通讯录"
    :visible.sync="dialogVisible"
    :modal="false"
    width="640rem"
    class="addressbook-dialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="contact-search">
      <el-input
        placeholder="请输入电话号码、姓名、部门进行搜索"
        prefix-icon="el-icon-search"
        v-model="searchInput"
        @focus="showView = '2'"
        @keyup.enter.native="searchTextEnter()"
      >
      </el-input>
    </div>
    <div class="organization-search-content">
      <div class="contact-list">
        <div class="contact-item" v-for="(contact, index4) in currentContactList" :key="index4">
          <div class="item-line" @mouseleave="contact.isHove = false">
            <div class="left">
              <img src="@/assets/leftOne/people.png" alt="" />
              <div class="left1">
                <div class="name" @mouseover="contact.isHove = true">{{ contact.name || '暂无姓名' }}</div>
                <div style="width: 286rem;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" :title="contact.unit">{{ contact.unit || '暂无部门' }}</div>
              </div>
            </div>
            <div class="cont-icon" v-show="contact.isHove">
              <!-- <img
                class="command-icon"
                src="@/assets/leftOne/video.png"
                title="视频通话"
                @click="openDialog('video', contact)"
                alt=""
              />
              <img
                class="command-icon"
                src="@/assets/leftOne/lawRecord.png"
                @click="openDialog('law-record', contact)"
                title="执法记录仪"
                alt=""
              />
              <img
                class="command-icon"
                src="@/assets/leftOne/video.png"
                title="传真"
                style="width: 26rem;"
                alt=""
              /> -->
              <el-button @click="openDialog('phone', contact)" type="text" size="small" icon="el-icon-phone" title="拨打电话"></el-button>
              <el-button @click="openDialog('message', contact)" type="text" size="small" icon="el-icon-message" title="发送短信"></el-button>
              <!-- <img
                class="command-icon"
                src="@/assets/leftOne/addressbook.png"
                title="短信"
                style="width: 22rem;"
                @click="openDialog('message', contact)"
                alt=""
              />
              <img
                class="command-icon"
                src="@/assets/leftOne/phone.png"
                @click="openDialog('phone', contact)"
                title="电话呼叫"
                alt=""
              /> -->
            </div>
            <span class="right">{{ contact.phoneTheme || '暂无电话' }}</span>
          </div>
          <!-- <div class="item-line">
            <div class="item-line-l" v-show="contact.unit">
              <i class="iconfont iconzhihui_tongxunlu_bumen"></i> {{ contact.unit }}
            </div>
            <div class="item-line-r" v-show="contact.duty">
              <i class="iconfont iconzhihui_tongxunlu_zhiwu"></i> {{ contact.duty }}
            </div>
          </div> -->
        </div>
      </div>
    </div>
    <div class="footer">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total"
        :page-count="3"
      ></el-pagination>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'

export default {
  props: ['dialogAddressBookVisible'],
  data() {
    return {
      dialogVisible: false,
      currentContactList: [],
      searchInput: '',
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  watch: {
    dialogAddressBookVisible(v) {
      if (v) {
        this.dialogVisible = v
        this.currentPage = 1
        this.pageSize = 10
        this.getCurrentContactList()
      }
    }
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getCurrentContactList()
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogAddressBookVisible', false)
    },
    handleOk() {
      this.handleClose()
    },
    searchTextEnter: function () {
      this.getCurrentContactList()
    },
    getCurrentContactList: function () {
      // if (!this.searchInput) return
      let url = '/apis/communication/baseInfo?pageLen=' + this.pageSize + '&pageStart=' + this.currentPage
      if (this.searchInput) {
        url += '&text=' + this.searchInput
      }
      Axios.get(url).then((res) => {
        let data = res.data.data.list || []
        data.forEach((item) => {
          item.leader = item.name
          item.isHove = false
          item.phoneTheme = item.phone ? (item.phone.substring(3, 0)) + '****' + (item.phone.substring(7)) : ''
        })
        this.currentContactList = data
        this.total = res.data.data.count
      })
    },
    openDialog(type, data) {
      this.$emit('openDialog', {
        type: type,
        data: data
      })
    }
  },
  mounted() {}
}
</script>

<style lang='scss'>
.addressbook-dialog {
  .contact-search {
    // border: 1rem solid #28feff;
    // border-radius: 22rem;
    .el-input__icon {
      color: #4d98cd;
      font-size: 20rem;
    }
    .el-input__inner {
      border: 1rem solid rgba(0, 145, 255, 0.5);
      border-radius: 2rem;
      background: RGBA(16, 38, 60, 1);
      padding-left: 40rem;
    }
  }
  .organization-search-content {
    position: relative;
    height: calc(100% - 90rem);
    margin-top: 10rem;
    margin-bottom: 15rem;

    .contact-list {
      position: relative;
      height: 670rem;
      overflow-x: hidden;
      overflow-y: auto;

      .contact-item {
        position: relative;
        margin-bottom: 10rem;

        .item-line {
          color: #e5edff;
          // line-height: 1;
          height: 90rem;
          overflow: hidden;
          display: flex;
          justify-content: space-between;
          align-items: center;
          .cont-icon {
            float: right;
            margin-left: 15rem;
            .command-icon {
                width: 25rem;
                margin-right: 10rem;
                cursor: pointer;
            }

            i {
              cursor: pointer;
              margin: 0 10rem;
              color: #9ab9ff;
              font-size: 28rem;
            }
          }
          .left {
            display: flex;
            justify-content: space-between;
            align-items: center;
            img {
              width: 38rem;
              height: 36rem;
              margin-right: 20rem;
            }
            .left1 {
              display: flex;
              flex-direction: column;
              align-items: flex-start;
              justify-content: center;
              .name {
                color: #ffffff;
                font-size: 24rem;
                margin-bottom: 10rem;
              }
            }
          }

          .item-line-l {
            float: left;
          }

          .item-line-r {
            float: right;
          }

          i {
            font-size: 28rem;
            color: #0c99db;
          }
        }
      }
    }
  }
  .footer {
    float: right;
    margin-top: -10rem;

    .el-pagination .el-select .el-input .el-input__inner {
      color: #fff;
    }

    .el-pager li,
    .el-pagination button,
    .el-pagination span:not([class*='suffix']) {
      font-size: 18rem;
      color: #fff;
    }

    .el-pagination__sizes {
      margin: 0 1200rem 0 0;
    }
  }
}
</style>
