<template>
    <el-upload
        action="http://k3a206.p.ssafy.io:8888/chuck/pictures/upload"
        :data="{groupId:this.getSelectedGroup.id}"
        :show-file-list="false"
        :auto-upload="true"
        :before-upload="beforeImageUpload"
        :on-success="handleSuccess"
        :on-exceed="handleExceed"
    >
        <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
                <i
                    icon
                    class="el-icon-zoom-in"
                    v-bind="attrs"
                    v-on="on"
                ></i>
            </template>
            <span>해당 위치에 사진 변경하기</span>
        </v-tooltip>
    </el-upload>
</template>
<script>
import api from '@/utils/api';
import eventBus from '@/utils/EventBus';
import { mapGetters } from 'vuex'

export default {
    props: {
        index: Number,
    },
    computed: {
        ...mapGetters([
            'getSelectedGroup',
        ]),
    },
    methods: {
        beforeImageUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isPNG = file.type === 'image/png';
            const isLt10M = file.size / 1024 / 1024 < 10;

            if (!(isPNG || isJPG)) {
                this.$notify({
                    title: 'JPG, PNG 포맷만 지원합니다.',
                    dangerouslyUseHTMLString: true,
                    duration: 3000
                });
            }
            if (!isLt10M) {
                this.$notify({
                    title: '파일 크기 제한은 10MB입니다.',
                    dangerouslyUseHTMLString: true,
                    duration: 3000
                });
            }
            return (isJPG || isPNG) && isLt10M;
        },
        handleExceed(file, fileList){
            alert("사진은 최대 10장의 사진을 업로드 할 수 있습니다.")
        },
        handleSuccess(res, file) {
            this.$emit('changeImage', {res: res, index: this.index})
        },
    }

}
</script>

<style>

</style>