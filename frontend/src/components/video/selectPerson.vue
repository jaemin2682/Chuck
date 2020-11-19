<template>
    <div style="padding:30px 30px 0px 0px;">
        <img src='../../assets/title/chuckFilm_tabtitle.svg' class="subtitle">
        <div class="dash">
            <font size=4>기간 선택</font>
        </div>
        <div class="dash" style="height:210px; text-align:left; overflow:auto;">
            <div style="margin:10px">
                <span style="display:inline-block; width:100px;">날짜 선택</span>
                <el-date-picker type="dates" v-model="value1" placeholder="날짜 선택" value-format="yyyy-MM-dd" size="small" @change="pickType1">
                </el-date-picker>
            </div>
            <div style="margin:10px">
                <span style="display:inline-block; width:100px;">범위 선택</span>
                <el-date-picker type="daterange" v-model="value2" start-placeholder="시작일" end-placeholder="종료일" value-format="yyyy-MM-dd" size="small" @change="pickType2">
                </el-date-picker>
            </div>
            <div style="margin:10px">
                <span style="display:inline-block; width:100px;">선택된 기간</span>
                <span v-if="value1">
                    <el-tag v-for="(tag, index) in value1" :key="index" closable @close="removeTag(tag)" type="info" style="margin:2px;">{{ tag }}</el-tag>
                </span>
                <span v-else-if="value2">
                    <el-tag type="info" style="margin:2px;">{{ value2[0] }}</el-tag>
                    ~
                    <el-tag type="info" style="margin:2px;">{{ value2[1] }}</el-tag>
                </span>
                <span v-else>
                    <font color="DCDFE6">기간을 선택해 주세요</font>
                </span>
            </div>
        </div>
        <div class="dash">
            <font size="4">인물 선택</font>
        </div>
        <div class="dash" style="height:325px; text-align:left">
            <span class="face pointer" @click="selectAll">ALL</span>
            <span class="face pointer" v-for="(face, index) in getFaceDataFilm" :key="index" @click="select(index)" :style="'background-image:url('+face.rep_image+')'">
                <img :id="'videoFace'+index" class="videoFaceNoneDisplay" src="../../assets/check_circle.svg">
            </span>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mapMutations } from 'vuex';
export default {
    data() {
        return {
            value1: '',
            value2: '',
            dates: [],
            personArray: [],
            selectCount: 0,
        }
    },
    computed: {
        ...mapGetters([
            'getFaceDataStudio',
            'getFaceDataFilm',
        ]),
    },
    watch: {
        getFaceDataStudio: function(data) {
            this.value1 = ''
            this.value2 = ''
            this.dates = []
            this.personArray = []
            this.selectCount = 0
            this.setPersonArrayFilm(this.personArray)
            this.setFaceDataFilm(null)
            this.$forceUpdate()
        }
    },
    methods: {
        ...mapMutations([
            'setPersonArrayFilm',
            'setFaceDataFilm',
        ]),
        removeTag(tag) {
            this.value1.splice(this.value1.indexOf(tag), 1);
            this.pickType1()
        },
        pickType1() {
            this.value2 = ''
            let arr1 = new Array()
            for(let i=0; i<this.getFaceDataStudio.studio_list.length; i++) {
                let arr2 = new Array()
                for(let j=0; j<this.getFaceDataStudio.studio_list[i].content_list.length; j++) {
                    if(this.value1.includes(this.getFaceDataStudio.studio_list[i].content_list[j].date.slice(0, 10))) {
                        arr2.push(this.getFaceDataStudio.studio_list[i].content_list[j])
                    }
                }
                if(arr2.length > 0) {
                    arr1.push({
                        'rep_image' : this.getFaceDataStudio.studio_list[i].rep_image,
                        'content_list' : arr2
                    })
                }
            }
            this.setFaceDataFilm(arr1)
        },
        pickType2() {
            this.value1 = ''
            let arr1 = new Array()
            for(let i=0; i<this.getFaceDataStudio.studio_list.length; i++) {
                let arr2 = new Array()
                for(let j=0; j<this.getFaceDataStudio.studio_list[i].content_list.length; j++) {
                    if(this.value2[0] <= this.getFaceDataStudio.studio_list[i].content_list[j].date.slice(0, 10)
                    && this.getFaceDataStudio.studio_list[i].content_list[j].date.slice(0, 10) <= this.value2[1]) {
                        arr2.push(this.getFaceDataStudio.studio_list[i].content_list[j])
                    }
                }
                if(arr2.length > 0) {
                    arr1.push({
                        'rep_image' : this.getFaceDataStudio.studio_list[i].rep_image,
                        'content_list' : arr2
                    })
                }
            }
            this.setFaceDataFilm(arr1)
        },
        selectAll() {
            if(this.selectCount === this.getFaceDataFilm.length) {
                for(let i=0; i<this.getFaceDataFilm.length; i++){
                    let el = document.getElementById('videoFace'+i)
                    el.setAttribute('class', 'videoFaceNoneDisplay')
                    this.$set(this.personArray, i, false)
                }
                this.selectCount = 0
                this.setPersonArrayFilm(this.personArray)
            }
            else {
                for(let i=0; i<this.getFaceDataFilm.length; i++){
                    let el = document.getElementById('videoFace'+i)
                    el.setAttribute('class', '')
                    this.$set(this.personArray, i, true)
                }
                this.selectCount = this.getFaceDataFilm.length
                this.setPersonArrayFilm(this.personArray)
            }
        },
        select(index) {
            let el = document.getElementById('videoFace'+index)
            el.classList.toggle("videoFaceNoneDisplay")
            if(this.personArray[index]) {
                this.$set(this.personArray, index, false)
                this.selectCount--
            }
            else {
                this.$set(this.personArray, index, true)
                this.selectCount++
            }
            this.setPersonArrayFilm(this.personArray)
        }
    }
  }
</script>
<style scoped>
.face {
    height: 92px;
    width: 92px;
    line-height: 92px;
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
.videoFaceNoneDisplay {
    display: none;
}
</style>