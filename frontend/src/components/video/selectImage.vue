<template>
    <div style="margin:30px 0px 0px 30px;">
        <div class="dash">
            <font size=4>사진 고르기</font>
        </div>
        <div class="dash" style="height:620px; text-align:left">
            <v-row style="padding: 10px 0px 10px 10px;">
                <img src="../../assets/tip_icon.svg" style="width:16px; margin:8px 12px 10px 20px;">
                <font size=2 color="#3D91FF">
                동영상을 만들기 위해서는 최소 5장의 사진이 필요합니다.<br>
                5장 이상의 사진을 선택해 주세요.
                </font>
            </v-row>
            <span class="photo pointer" @click="selectAll">ALL</span>
            <span class="photo pointer" v-for="(chuck, i) in temp" :key="i" @click="select(i)" :style="'background-image:url('+chuck+')'">
                <img :id="'videoPhoto'+i" class="videoPhotoNoneDisplay" src="../../assets/check_square.svg">
            </span>
        </div>
        <div v-if="selectCount < 5" class="dash pointer">
            <font size=4 color="red">동영상을 만들기 위한 사진의 갯수가 부족합니다.</font>
        </div>
        <div v-else class="dash pointer" @click="nextStep">
            <font size=4>다음으로</font>
        </div>
        <v-dialog v-model="loading" hide-overlay persistent width="300">
            <v-card color="#8D6262" dark>
                <v-card-text>
                    Making Chuck Film...
                    <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
    
</template>

<script>
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
import api from '@/utils/api'
export default {
    data () {
        return {
            selectCount: 0,
            maxCount : 0,
            imageArray: [],
            loading: false,
            imageList: new Map(),
            checkArr: [],
            temp: [],
        }
    },
    computed: {
        ...mapGetters([
            'getId',
            'getChuckList',
            'getFaceDataFilm',
            'getPersonArrayFilm',
            'getVideoUrl',
            'getFaceDataStudio',
        ]),
    },
    watch: {
        getFaceDataStudio: function(data) {
            this.selectCount = 0
            this.maxCount = 0
            this.imageArray = []
            this.loading = false
            this.imageList = new Map()
            this.checkArr = []
            this.temp = []
            this.$forceUpdate()
        },
        getPersonArrayFilm: function(data) {
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
                    const num = this.getFaceDataFilm[index].content_list.length
                    for(let i = 0; i<num; i++) {
                        const path = this.getFaceDataFilm[index].content_list[i].image
                        if(this.imageList.has(path)) {
                            const cnt = this.imageList.get(path)
                            if(flag) this.imageList.set(path, cnt + 1)
                            else {
                                if(cnt == 1) this.imageList.delete(path)
                                else this.imageList.set(path, cnt - 1)
                            }
                        } else {
                            if(flag) this.imageList.set(path, 1)
                        }
                    } 
                });
            }
            this.temp = Array.from(this.imageList.keys())
        },
    },
    methods: {
        ...mapMutations([
            'setVisibleChoice',
            'setVisibleAlbum',
            'setVisibleVideo',
            'setVisiblePreview',
            'setVideoSrc',
            'setVideoUrl',
        ]),
        nextStep() {
            this.loading = true
            let src = new Array()
            for(let i=0; i<this.imageArray.length; i++) {
                if(this.imageArray[i]) src.push(this.temp[i])
            }
            api.post('pictures/mkVideo', {
                'music': "Fingertips.mp3",
                'userId': this.getId,
                'path_list': src,
            })
            .then(({ data }) => {
                data = data.replace('final', 'middle')
                this.setVideoUrl(data)
                this.setVideoSrc(src)
            
                // 동영상 생성
                let p = document.getElementById("media-video")
                while (p.hasChildNodes()) {
                    p.removeChild( p.firstChild )
                }
                
                let source = document.createElement("source")
                let url = this.getVideoUrl
                source.setAttribute('src', url)
                p.appendChild(source)
                p.play()
            
                this.setVisibleChoice(false)
                this.setVisibleAlbum(false)
                this.setVisibleVideo(false)
                this.setVisiblePreview(true)
                this.loading = false
            })
        },
        selectAll() {
            if(this.selectCount == this.temp.length) {
                for(let i=0; i<this.temp.length; i++) {
                    let el = document.getElementById('videoPhoto'+i)
                    el.setAttribute('class', 'videoPhotoNoneDisplay')
                    this.imageArray[i] = false
                }
                this.selectCount = 0
            }
            else {
                for(let i=0; i<this.temp.length; i++) {
                    let el = document.getElementById('videoPhoto'+i)
                    el.setAttribute('class', '')
                    this.imageArray[i] = true
                }
                this.selectCount = this.temp.length
            }
        },
        select(i) {
            let el = document.getElementById('videoPhoto'+i)
            el.classList.toggle("videoPhotoNoneDisplay")
            if(this.imageArray[i]) {
                this.imageArray[i] = false
                this.selectCount--
            }
            else {
                this.imageArray[i] = true
                this.selectCount++
            }
        },
    }
}
</script>

<style scoped>
.photo {
    height: 118px;
    width: 118px;
    line-height: 118px;
    margin: 7px;
    display: inline-block;
    box-sizing: border-box;
    text-align: center;
    color: #fff;
    background: #C0C4CC;
    background-size: cover;
    font-size: 26px;
    overflow: hidden;
}
.photo img {
    height: 100%;
}
.videoPhotoNoneDisplay {
    display: none;
}
</style>