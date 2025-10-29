<template>
  <div class="videoManagePanel">
    <div class="left-content">
      <el-input placeholder="请输入内容" v-model="searchValue" class="search-input">
        <el-button slot="append" icon="el-icon-search" @click="mainSearch"></el-button>
      </el-input>
      <div class="tree-modules">
        <el-tree
          ref="tree"
          node-key="id"
          :highlight-current="true"
          :props="treeProps"
          :load="loadNode"
          lazy
          style="width: 100%"
          @node-click="checkChange"
          :filter-node-method="filterNodeMethod"
        ></el-tree>
      </div>
    </div>
    <div class="right-content">
      <div class="video-container">
        <div :class="'video-p'+videoType+'-item'"
             v-for="(item,index) in videoArray.slice(0,videoType)" :key="'video_'+ index">
          <div class="video-bow" v-if="item!=null">
            <i class="el-icon-close" @click="removeVideo(index)"></i>
            <span class="video-title" :title="item.label">{{item.label}}</span>
            <znv-video :src="item.url" :fontSize="64" :type="item.type" class="videoHight"></znv-video>
          </div>
        </div>
      </div>
      <div class="video-tools">
         <i class="el-icon-menu" :class="{'isSelected':videoType ===4}" @click="checkVideoType(4)"></i>
         <i class="el-icon-s-grid" :class="{'isSelected':videoType ===9}" @click="checkVideoType(9)"></i>
      </div>
    </div>
  </div>
</template>

<script>
import ZnvVideo from '@/components/znvVideo/Video.vue'
import { getHlsUrl, getFlvUrl } from '@/api/info.js'
import axios from '@/utils/request'
export default {
  name: 'videoManagePanel',
  components: { ZnvVideo },
  data() {
    return {
      searchValue: '',
      videoType: 4, // 4、9
      videoArray: [null, null, null, null, null, null, null, null, null],
      treeProps: {
        id: 'id',
        label: 'name',
        isLeaf: 'isLeaf'
      }
    }
  },
  methods: {
    mainSearch() {
      this.$refs.tree.filter(this.searchValue)
    },
    loadNode(node, resl) {
      let UpRegionId = ''
      if (node.level !== 0) {
        UpRegionId = node.data.id
      }
      let url = `/community/queryStaticRegionAndDeviceByUpId?UpRegionId=${UpRegionId}`
      axios.get(url).then((r) => {
        let data = r.data.data.map((t) => {
          t.children = []
          t.label = t.name
          t.id = t.indexCode
          t.isLeaf = !!t.isLeaf
          return t
        })
        resl(data)
      })
    },
    checkChange(node, isNodeCheck, isChildCheck) {
      if (node.isLeaf) {
        this.addVideoToDesk(node)
      }
    },
    filterNodeMethod(value, data, node) {
      return node.data.label.indexOf(value) > -1
    },
    checkVideoType(type) {
      this.videoType = type
    },
    removeVideo(index) {
      this.videoArray.splice(index, 1, null)
    },
    addVideoToDesk(node) {
      let hasAdd = false
      this.videoArray.forEach((v, i) => {
        // 当前格数只能放那么多，过多不可添加
        if (i >= this.videoType) {
          return
        }
        // 判断当前是否已经添加视频
        let deviceIds = this.videoArray.map(t => {
          if (!t) {
            return null
          } else {
            return t.id
          }
        })
        if (deviceIds.includes(node.id)) {
          return
        }

        if (!hasAdd && !v) {
          getFlvUrl({
            deviceId: node.id,
            type: 'HLS'
          }).then(res => {
            if (res.src) {
              this.videoArray.splice(i, 1, Object.assign({}, {
                label: node.label,
                deviceId: node.id,
                url: res.src,
                type: 'hls'
              }, node))
            } else {
              this.$message({
                message: '无对应视频，请确认！',
                type: 'warning'
              })
            }
          })
          hasAdd = true
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.videoManagePanel{
  margin-top: 100rem;
  height: 2766rem;
  display: flex;
  justify-content: center;
  .left-content{
    width: 1576rem;
    height: 2512rem;
    margin: 0rem 94rem 0rem 94rem;
    ::v-deep.search-input{
      .el-input__inner{
        height: 124rem;
        width:100%;
        border: 2rem solid rgba(0, 148, 255, 1);
        border-radius: 0rem;
      }
      .el-input-group__append{
        width: 118rem;
        height: 124rem;
        background-color: rgba(0, 148, 255, 1);
        border: 2rem solid rgba(0, 148, 255, 1);
        border-radius: 0rem;
        .el-button{
          .el-icon-search{
            font-size: 70rem;
            color: #BFE4FF;
          }
        }
      }
    }
    .tree-modules{
      margin-top: 50rem;
      height: calc(100% - 174rem);
      width: 100%;
      overflow:scroll;
      ::v-deep.el-tree{
        >.el-tree-node>.el-tree-node__content{
          background: #1E4E7D;
          .el-tree-node__label{
            color: #6FD1FF;
          }
        }
        .el-tree-node>.el-tree-node__children{
          background-color: rgba(7, 38, 73, 1);
        }
        .el-tree-node__content{
          height: 172rem;
        }
        .el-tree-node__label{
          font-size: 64rem;
          color: rgba(255, 255, 255, 1);
        }
        .el-tree-node__expand-icon{
          margin-left: 75rem;
          margin-right: 28rem;
        }
        .el-tree-node__expand-icon:before{
          font-size: 64rem;
          color: rgba(111, 209, 255, 1);
        }
        .el-tree-node__expand-icon.is-leaf:before{
          font-size: 64rem;
          color: transparent;
        }
        >.el-tree-node>.el-tree-node__content:hover, >.el-tree-node>.el-upload-list__item:hover {
          background: #1E4E7D;
        }
        .el-tree-node__content:hover, .el-upload-list__item:hover {
          background-color: rgba(30, 78, 125, 0.5);
          .el-tree-node__label{
            color: rgba(22, 240, 243, 1);
          }
        }
        .el-tree-node__content, .el-upload-list__item {
          background-color: rgba(7, 38, 73, 1);
        }
      }
    }
  }
  .right-content{
    width: 4426rem;
    height: 100%;
    margin: 0rem 194rem 0rem 194rem;
    .video-container{
      width: 100%;
      height: 2512rem;
      background: rgba(34, 65, 96, 1);
      border: 2rem solid #0D2D78;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-evenly;
      align-items: center;
      .el-icon-close{
        position: absolute;
        right: 50rem;
        top: 50rem;
        font-size: 64rem;
        color: rgba(255,255,255,0.5);
        z-index: 9;
      }
      .el-icon-close:hover{
        cursor: pointer;
        color: rgba(255,255,255,1);
      }
      .video-title{
        position: absolute;
        left: 50rem;
        top: 50rem;
        font-size: 64rem;
        color: rgba(255,255,255,1);
        width: 1152rem;
        overflow:hidden;/*内容超出后隐藏*/
        text-overflow: ellipsis;/* 超出内容显示为省略号 */
        white-space: nowrap;/* 文本不进行换行 */
      }
      .video-p9-item{
        width: 1300rem;
        height: 782rem;
        position: relative;
        background-color: black;
      }
      .video-bow{
        width: 100%;
        height: 100%;
      }
      .video-p4-item{
        width: 2008rem;
        height: 1180rem;
        position: relative;
        background-color: black;
      }
    }
    .video-tools{
      width: 100%;
      height: 254rem;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      i{
        font-size: 108rem;
        margin-left: 122rem;
        color: rgba(0, 168, 255, 0.5);
      }
      i.isSelected{
        color: rgba(0, 168, 255, 1);
      }
    }
  }
}
</style>
