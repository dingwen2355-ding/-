<template>
  <el-dialog
    :title="'附件详情'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="800px"
    class="PreviewViewerDialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="PreviewViewerBox">
      <div v-show="previewType === 0" style="display: flex">
        <znv-video :src="previewSources[previewIndex]" style="width: 80%" type="mp4"></znv-video>
        <div style="padding: 0 10px; flex-grow: 1; width: 20%">
          <div v-for="(file, index) in previewSources" @click="previewIndex === index" :key="file">
            <span :class="[previewIndex === index ? 'previewActive' : '', 'fileName']">{{ findName(file) }}</span>
          </div>
        </div>
      </div>
      <div v-show="previewType === 2">
        <div v-for="file in previewSources" :key="file" class="fileName" style="margin: 20px">
          <a :href="file" download>{{ findName(file) }}<i class="el-icon-download"></i></a>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import ZnvVideo from '@/components/znvVideo/Video'
export default {
  name: 'PreviewViewerDialog',
  props: ['dialogVisibleFlag', 'type', 'list'],
  components: { ZnvVideo },
  watch: {
    type(v) {
      this.previewType = v
    },
    list(v) {
      this.previewSources = v
    },
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      previewIndex: 0,
      previewType: 0,
      previewSources: [],
      eventInfo: {}
    }
  },
  methods: {
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
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    reset() {
      this.currentPage = 1
      this.formInline = {
        title: '',
        precinctId: '',
        time: ''
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.PreviewViewerDialog {
  .PreviewViewerBox {
    margin-left: 10px;
    min-height: 100%;
    height: auto;
    padding: 16px;
    .fileName {
    //   font-size: 80rem;
      color: #e0f3ff;
    }
    .previewActive {
      color: #3388ff;
    }
  }
}
</style>
