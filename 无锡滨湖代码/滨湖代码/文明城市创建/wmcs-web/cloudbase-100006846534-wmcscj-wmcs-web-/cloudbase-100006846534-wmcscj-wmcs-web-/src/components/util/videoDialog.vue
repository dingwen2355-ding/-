<template>
    <el-dialog :title="title" :visible.sync="show" append-to-body width="800px" :before-close="cancel">
        <div class="dia-bd-hei">
            <video ref="video" id="video" controls muted autoplay style="width:750px;height:450px"></video>
        </div>
    </el-dialog>
</template>
<script>
import { gethls } from "@/api/data/device";
import Hls from 'hls.js';
export default {
    props: ["row", "show"],
    data() {
        return {
            title: "",
            hls:""
        }
    },
    mounted() {
        gethls({ channelId: this.row.channelId }).then(res1 => {
            console.log(res1)
            if(this.row.orgName && this.row.name) {
                this.title = this.row.orgName + " ~ " + this.row.name
            } else {
                this.title = "监控"
            }
            if (Hls.isSupported()) {
                this.hls = new Hls();
                this.hls.loadSource(res1.data.url);
                this.hls.attachMedia(this.$refs.video);
                this.hls.on(Hls.Events.MANIFEST_PARSED, () => {
                    console.log('加载成功');
                    video.play();
                });
                this.hls.on(Hls.Events.ERROR, (event, data) => {
                    console.log(event, data);
                    // 监听出错事件
                    console.log('加载失败');
                });
            }
        })
    },
    methods: {
        cancel() {
            this.$refs.video.pause()
            this.hls.destroy();
            this.hls = null;
            this.$emit("cancel");
        }
    }
}
</script>