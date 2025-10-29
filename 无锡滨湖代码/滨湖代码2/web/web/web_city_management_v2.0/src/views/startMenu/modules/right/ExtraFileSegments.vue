<template>
  <div class="ExtraFileSegments">
    <el-button-group>
      <el-button
        v-if="videoSrources.length > 0"
        @click="showExtraFile(0, videoSrources)"
        type="primary"
        size="mini"
        icon="el-icon-video-camera"
      ></el-button>
      <el-button
        v-if="imageSrources.length > 0"
        @click="showExtraFile(1, imageSrources)"
        type="primary"
        size="mini"
        icon="el-icon-picture-outline"
      >
      </el-button>
      <el-button
        v-if="doucSrources.length > 0"
        @click="showExtraFile(2, doucSrources)"
        type="primary"
        size="mini"
        icon="el-icon-document-copy"
      ></el-button>
      <el-button
        v-if="doucSrources.length > 0 || imageSrources.length > 0 || videoSrources.length > 0"
        type="primary"
        size="mini"
        icon="el-icon-share"
      ></el-button>
    </el-button-group>
    <el-image-viewer
      v-if="pictureShow"
      :z-index="2024"
      :on-close="
        () => {
          pictureShow = false
        }
      "
      :url-list="previewSources"
    ></el-image-viewer>
    <ExtraFilePreviewViewerDialog
      :dialogVisibleFlag.sync="fileDialogVisible"
      :type="previewType"
      :list="previewSources"
    />
  </div>
</template>

<script>
import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
import ExtraFilePreviewViewerDialog from './ExtraFilePreviewViewerDialog'
export default {
  name: 'ExtraFileSegments',
  props: ['images', 'videos', 'doucs'],
  components: { ElImageViewer, ExtraFilePreviewViewerDialog },
  watch: {
    images(val) {},
    videos(val) {},
    doucs(val) {}
  },
  computed: {
    imageSrources() {
      return this.transToArray(this.images)
    },
    videoSrources() {
      return this.transToArray(this.videos)
    },
    doucSrources() {
      return this.transToArray(this.doucs)
    }
  },
  data() {
    return {
      previewType: 0,
      previewSources: [],
      fileDialogVisible: false,
      pictureShow: false
    }
  },
  methods: {
    transToArray(target = undefined) {
      if (Array.isArray(target)) {
        return target
      } else {
        return target ? target.split(',') : []
      }
    },
    findName(v) {
      let data = []
      if (v !== null) {
        if (v.indexOf(',') !== -1) {
          v.split(',').forEach((item) => {
            let da = item.split('/')
            let da2 = da[da.length - 1].split('-')
            da2.splice(0, 1)
            data.push(da2)
          })
        } else {
          let da = v.split('/')
          let da2 = da[da.length - 1].split('-')
          da2.splice(0, 1)
          data.push(da2)
          console.log(data)
        }
      }
      return data.toString()
    },
    showExtraFile(type, sources = []) {
      this.previewType = type
      this.previewSources = sources
      switch (type) {
        case 2:
        case 0:
          this.fileDialogVisible = true
          break
        default:
          this.pictureShow = true
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.ExtraFileSegments {
  .el-button {
    min-width: 60rem;
    padding: 10rem 40rem;
    height: 80rem;
    border-radius: 10rem;
    // background: none;
    background: -webkit-gradient(linear, left top, right top, from(#00a3ff), to(#8ad4ff));
    background: linear-gradient(90deg, #00a3ff 0%, #8ad4ff 100%);
    border: none;
    font-size: 60rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
  }
}
</style>
