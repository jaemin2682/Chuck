<template>
    <div style="padding:30px 30px 0px 0px;">
        <div>
        <img v-if="!getModify" src="../../assets/title/chuck_write.svg" class="logo">
        <img v-else src="../../assets/title/chuck_edit.svg" class="logo">
        </div>
        <div style="margin:20px 0px 20px 0px;">
            <font size=4 color="#3D91FF">최대 10장의 사진을 업로드할 수 있습니다.</font>
        </div>
        <div class="dash" style="height:230px; width:260px; display:inline-block; margin:5px;">
            <el-upload
            ref="upload"
            drag
            multiple
            action="http://k3a206.p.ssafy.io:8888/chuck/pictures/upload"
            :data="{groupId:this.getSelectedGroup.id}"
            :limit=10
            :show-file-list="false"
            :auto-upload="true"
            :before-upload="beforeImageUpload"
            :on-success="handleSuccess"
            :on-exceed="handleExceed"
            >
                <img src="../../assets/uploadIcon.svg" style="margin-top:40px; width:100px;">
                <div class="el-upload__text"><em>클릭</em>하거나 <em>드래그</em>하여<br>이미지를 업로드 하세요</div>
            </el-upload>
        </div>
        <div class="dash" style="height:230px; width:260px; display:inline-block; margin:5px;">
            <v-dialog v-model="dialog" width="900">
                <template v-slot:activator="{ on, attrs }">
                    <div @click="openCloud" v-bind="attrs" v-on="on">
                    <img src="../../assets/cloud.svg" style="margin-top:40px; width:100px;">
                    <div style="font-size:14px; color:#606266;"><font color="#409EFF">클라우드 서버</font>에서<br>이미지를 불러오세요</div>
                    </div>
                </template>
                <v-card>
                    <v-card-title>
                        <font size=6 color="#8D6262">
                            나의 클라우드 저장소
                        </font>
                        <img src="../../assets/update.svg" class="pointer" style="width:30px; height:30px; margin-left:20px;" @click="refresh">
                    </v-card-title>
                    <v-card-text>
                        <v-row>
                            <v-col cols=6 style="height:400px;">
                                <div style="margin-bopxttom:10px; text-align:left; margin-top:20px;"><font size=5>미리보기</font></div>
                                <img v-if="selectedImage" :src="selectedImage" style="width:360px; height:270px; object-fit:cover; margin-top:30px;">
                                <img v-else src="../../assets/gallery/Camera_non.svg" style="width:360px; height:270px; margin-top:30px; object-fit:scale-down">
                            </v-col>
                            <v-col cols=6 style="text-align:left; border-left: solid 1px #E0E0E0; padding-left:30px;">
                                <span class="photo pointer"  v-for="(image, index) in getCloudImages" :key="index" @click="select(index)" :style="'background-image:url('+image+')'">
                                    <img :id="'cloudImage'+index" class="cloudImageNoneDisplay" src="../../assets/check_square.svg" style="width:86px; height:86px;">
                                </span>
                            </v-col>
                        </v-row>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <el-button plain type="chuck" @click="selectCloud">선택 완료</el-button>
                        <el-button plain type="chuck" @click="dialog=false">닫기</el-button>
                    <v-spacer></v-spacer>
                    
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </div>
        <div style="padding:14px 28px; text-align:left; height:250px;">
            <ul class="el-upload-list el-upload-list--picture-card" style="padding:0px;">
                <li v-for="(image, index) in this.$store.state.images" :key="index" class="el-upload-list__item is-ready">
                    <img :src="image" class="el-upload-list__item-thumbnail">
                    <span class="el-upload-list__item-actions">
                        <span class="el-upload-list__item-preview">
                            <change
                                :index="index"
                                @changeImage="changeImage"
                            ></change>
                        </span>
                        <span class="el-upload-list__item-delete" @click="handleRemove(image, index)">
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <i 
                                        icon
                                        class="el-icon-delete"
                                        v-bind="attrs"
                                        v-on="on"
                                    ></i>
                                </template>
                                <span>삭제</span>
                            </v-tooltip>
                        </span>
                    </span>
                </li>
            </ul>
        </div>
        <div style="padding:0px 34px; text-align:right" v-if="this.$store.state.images.length > 0">
            <font size=5 color="#3D91FF" class="pointer" @click="removeAll">전체삭제</font>
        </div>
    </div>
</template>

<script>
import api from '@/utils/api'
import eventBus from '@/utils/EventBus'
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
import store from '@/store'

export default {
    data() {
        return {
            dialog: false,
            selectedImage: '',
            selectedImageArray: [],
            selectedImages: '',
        }
    },
    computed: {
        ...mapGetters([
            'getId',
            'getSelectedGroup',
            'getColor',
            'getChuckList',
            'getModify',
            'getChuckMap',
            'getCloudImages',
        ]),
    },
    components: {
        Change: () => import('@/components/calendar/change.vue'),
    },
    mounted() {
        for(let i=0; i<this.getCloudImages.length; i++) {
            this.selectedImageArray.push(false)
        }
    },
    updated() {
        eventBus.$on('init-el', () => {
            this.$refs.upload.clearFiles()
        })
    },
    methods: {
        ...mapMutations([
            'setCloudImages',
        ]),
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
        handleExceed(file, fileList) {
            this.$notify({
                title: '사진은 최대 10장 업로드할 수 있습니다.',
                dangerouslyUseHTMLString: true,
                duration: 3000
            });
        },
        handleSuccess(res, file) {
            store.state.images.push(res)
        },
        handleRemove(file, index) {
            this.$refs.upload.fileList.pop()
            // store.state.deletedImages.push(store.state.images[index])
            store.state.images.splice(index, 1)
        },
        removeAll() {
            this.$refs.upload.clearFiles()
            store.state.images.forEach(element => {
                store.state.deletedImages.push(element)
            });
            store.state.images = new Array()
        },
        changeImage(data) {
            store.state.deletedImages.push(store.state.images[data.index])
            store.state.images.splice(data.index, 1)
            store.state.images.splice(data.index, 0, data.res)
        },
        select(index) {
            let el = document.getElementById('cloudImage'+index)
            el.classList.toggle("cloudImageNoneDisplay")
            this.selectedImage = this.getCloudImages[index]
            if(this.selectedImageArray[index]) {
                this.$set(this.selectedImageArray, index, false)
            }
            else {
                this.$set(this.selectedImageArray, index, true)
            }
        },
        selectCloud() {
            for(let i=0; i<this.selectedImageArray.length; i++) {
                if(this.selectedImageArray[i]) {
                    this.$store.state.images.push(this.getCloudImages[i])
                }
            }
            this.dialog = false
        },
        openCloud() {
            this.dialog=true
            for(let i=0; i<this.selectedImageArray.length; i++) {
                if(this.selectedImageArray[i]) {
                    this.selectedImageArray[i] = false
                    let el = document.getElementById('cloudImage'+i)
                    el.classList.toggle("cloudImageNoneDisplay")
                }
            }
        },
        refresh() {
            api.get(`kakao/list?id=${this.getId}`)
            .then(({ data }) => {
                this.setCloudImages(data)
                for(let i=0; i<data.length; i++) {
                    this.selectedImageArray.push(false)
                }
                this.$forceUpdate()
            })
        }
    }
}
</script>
<style scoped>
.photo {
    height: 86px;
    width: 86px;
    line-height: 86px;
    margin: 5px;
    display: inline-block;
    box-sizing: border-box;
    text-align: center;
    color: #fff;
    background: #C0C4CC;
    background-size: cover;
    font-size: 26px;
    overflow: hidden;
}
.cloudImageNoneDisplay {
    display: none;
}
</style>