<template>
    <div style="margin: 30px 30px 0px 0px">
        <div class="text-left" style="padding-left:30px;">
            <img src="../../assets/title/gallery_tabtitle.svg" class="tabtitle">
            <font size=6>
                <el-popover placement="bottom" width="230" trigger="click">
                    <p style="font-family:MaplestoryOTFLight;">그룹의 모든 게시글을 분석하여 인물을<br> 찾아냅니다. 선택된 인물이 포함된<br>사진을 필터링하여 볼 수 있습니다.</p>
                    <i class="el-icon-info pointer" slot="reference" style="position:relative; left:250px; bottom:20px;"></i>
                </el-popover>
            </font>
        <div class="underline" style="margin-top: -5px;"></div>
        </div>
        <div v-if="getFaceDataGallery.gallery_list" style="text-align:left; padding:20px 0px 10px 30px;">{{getFaceDataGallery.gallery_list.length}}명의 인물이 검색되었습니다.</div>
        <div v-if="getFaceDataGallery.gallery_list" class="dash" style="width:550px; height:560px; text-align:left;">
            <div v-if="getFaceDataGallery.gallery_list.length > 0" style="padding:8px;">
                <span class="face pointer" @click="selectAll">ALL</span>
                <span class="face pointer" v-for="(face, index) in getFaceDataGallery.gallery_list" :key="index" @click="select(index)">
                    <img :src="face.rep_image">
                    <img :id="'galleryFace' + index" class="galleryFaceNoneDisplay" src="@/assets/check_circle.svg" style="position:relative; top:-128px;">
                </span>
            </div>
            <div v-else style="padding:8px; text-align:center; margin-top:160px; color:#C0C0C0;">
                <img src="../../assets/gallery/Oops.svg" style="width:200px;">
                <p>
                    게시된 Chuck의 인물 데이터가<br>
                    충분하지 않아요!
                </p>
            </div>
        </div>
    </div>
</template>
<script>
import { mapGetters } from "vuex"
import { mapMutations } from "vuex"
import api from '@/utils/api.js'
export default {
    data() {
        return {
            checkArr: [],
            selectCount: 0,
        };
    },
    computed: {
        ...mapGetters([
            'getSelectedGroup',
            'getFaceDataGallery',
        ])
    },
    watch: {
        getFaceDataGallery: function() {
            this.checkArr = []
            this.selectCount = 0
            this.$forceUpdate()
        }
    },
    methods: {
        ...mapMutations([
            'setPersonArrayGallery',
        ]),
        selectAll() {
            if(this.getFaceDataGallery.gallery_list.length !== 0 && this.selectCount === this.getFaceDataGallery.gallery_list.length) {
                for(let i=0; i<this.getFaceDataGallery.gallery_list.length; i++){
                    let el = document.getElementById('galleryFace'+i)
                    el.setAttribute('class', 'galleryFaceNoneDisplay')
                    this.$set(this.checkArr, i, false)
                }
                this.selectCount = 0
                this.setPersonArrayGallery(this.checkArr)
            }
            else {
                for(let i=0; i<this.getFaceDataGallery.gallery_list.length; i++){
                    let el = document.getElementById('galleryFace'+i)
                    el.setAttribute('class', '')
                    this.$set(this.checkArr, i, true)
                }
                this.selectCount = this.getFaceDataGallery.gallery_list.length
                this.setPersonArrayGallery(this.checkArr)
            }
        },
        select(index) {
            let el = document.getElementById("galleryFace" + index);
            el.classList.toggle("galleryFaceNoneDisplay");
            if (this.checkArr[index]) {
                this.$set(this.checkArr, index, false);
                this.selectCount--
            } else {
                this.$set(this.checkArr, index, true);
                this.selectCount++
            }
            // store에 저장
            this.setPersonArrayGallery(this.checkArr);
        },
    },
};
</script>

<style scoped>
.face {
    height: 90px;
    width: 90px;
    line-height: 90px;
    margin: 7px;
    border-radius: 50%;
    display: inline-block;
    box-sizing: border-box;
    text-align: center;
    color: #fff;
    background: #C0C4CC;
    background-size: cover;
    font-size: 26px;
    overflow: hidden;
}
.face img {
    height: 100%;
}
.galleryFaceNoneDisplay {
    display: none;
}
</style>