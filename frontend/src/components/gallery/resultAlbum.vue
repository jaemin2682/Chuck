<template>
    <div style="padding:20px 0px 0px 30px;">
        <div class="bg" style="text-align:center">
            <img src="../../assets/gallery/top_left.svg" class="tape" style="top:20px; left:70px;">
            <img src="../../assets/gallery/top_right.svg" class="tape" style="top:30px; left:530px;">
            <img src="../../assets/gallery/bottom_left.svg" class="tape" style="top:330px; left:70px;">
            <img src="../../assets/gallery/bottom_right.svg" class="tape" style="top:310px; left:510px;">
            <img v-if="currentImage" class="currentImg" :src="currentImage">
            <div v-else style="color:#C0C0C0; margin-bottom:96px;">
                <img src="../../assets/gallery/Camera_non.svg" style="width:100px; margin-top:90px;">
                <p>아래에서 사진을<br>선택해주세요</p>
            </div>
            <div class="pointer" style="margin-top:14px;">
                <span class="chuck-tag el-tag--dark" style="background-color: rgb(141, 98, 98); border-color: rgb(141, 98, 98);" @click="moveChuck()">Chuck으로 이동</span>
            </div>
        </div>
        <div class="dash" style="text-align:left; height:320px; margin-top:44px;">
            <span v-for="(item, index) in temp" :key="index" class="picture">
                <img class="pointer picture" :src="item" @click="clickedImg(index)" style="object-fit:cover">
            </span>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex"
import { mapMutations } from "vuex"
import api from '@/utils/api.js'
import eventBus from '@/utils/EventBus'

export default {
    data() {
        return {
            checkArr: [],
            imageList: new Map(),
            temp: [],
            currentImage: '',
            currentImageIndex: '',
        }
    },
    computed: {
        ...mapGetters([
            'getSelectedGroup',
            'getPersonArrayGallery',
            'getFaceDataGallery',
            'getChuckMap',
        ]),
    },
    watch: {
         getFaceDataGallery: function() {
            this.checkArr = []
            this.imageList = new Map()
            this.temp = []
            this.currentImage =  ''
            this.currentImageIndex = ''
            this.$forceUpdate()
        },
        getPersonArrayGallery: function(data) {
            let change = []
            if(this.checkArr.length == 0) {
                this.checkArr = data.slice()
                for(let i = 0; i<data.length; i++) change.push(i)
            } else {
                for(let i = 0; i<data.length; i++) {
                    if(this.checkArr[i] != data[i]) change.push(i)
                }
                this.checkArr = data.slice()
            }
            if(change.length != 0) {
                change.forEach(index => {
                    const flag = this.checkArr[index]
                    const num = this.getFaceDataGallery.gallery_list[index].content_list.length
                    for(let i = 0; i<num; i++) {
                        const diary_id = this.getFaceDataGallery.gallery_list[index].content_list[i].diary_id
                        const path = this.getFaceDataGallery.gallery_list[index].content_list[i].path
                        if(this.imageList.has(path)) {
                            const cnt = this.imageList.get(path).cnt
                            if(flag) this.imageList.set(path, {cnt: cnt + 1, diaryId: diary_id})
                            else {
                                if(cnt == 1) this.imageList.delete(path)
                                else this.imageList.set(path, {cnt: cnt - 1, diaryId: diary_id})
                            }
                        } else {
                            if(flag) this.imageList.set(path, {cnt: 1, diaryId: diary_id})
                        }
                    } 
                });
            }
            this.temp = Array.from(this.imageList.keys())
            if(!this.temp.includes(this.currentImage)) {
                this.currentImage = ''
                this.currentImageIndex = ''
            }
        }
    },
    methods: {
        clickedImg(index) {
            this.currentImage = this.temp[index]
            this.currentImageIndex = index
        },
        moveChuck() {
            const id = this.imageList.get(this.temp[this.currentImageIndex]).diaryId
            eventBus.$emit('movePage', {index: 1, item: this.getChuckMap.get(id), state: 3})
        }
    },
};
</script>

<style scoped>
.picture {
    width: 120px;
    height: 120px;
    margin: 3px;
}
.bg {
    background: url('../../assets/gallery/background.svg');
    background-size: cover;
    width: 500px;
    height: 340px;
    margin: 20px auto;
    margin-bottom: 0px;
}
.currentImg {
    width: 440px;
    height: 300px;
    margin-top: 20px;
    object-fit: cover;
}
.tape {
    position: absolute;
}
</style>