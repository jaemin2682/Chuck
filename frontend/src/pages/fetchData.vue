<template>
    <div>
        <div class="loader book">
            <figure class="page"></figure>
            <figure class="page"></figure>
            <figure class="page"></figure>
        </div>
        <h1>loading</h1>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
import store from '@/store'
import api from '@/utils/api.js'
import moment from 'moment-timezone'
import VueMoment from 'vue-moment'

export default {
    data() {
        return {
        }
    },
    created() {
        this.fetchData()
    },
    computed: {
        ...mapGetters([
            'getId',
            'getSelectedGroup',
            'getColor'
        ]),
    },
    watch: {
        '$route': 'fetchData'
    },
    methods: {
        ...mapMutations([
            "setChuckList",
            'setCloudImages',
        ]),
        fetchData() {
            // 게시글 불러오기
            api.get(`diaries/group/${this.getSelectedGroup.id}`, {
                headers: {
                    token: sessionStorage.getItem('token')
                },
            })
            .then(({ data }) => {
                this.$store.state.chuckMap = new Map()
                const num = data.length-1
                for(var i=num; i>=0; i--) {
                    const image = data[i].image.split(';')
                    data[i].image = image
                    data[i].color = this.getColor[i % 10]
                    data[i].index = i
                    data[i].date = data[i].date.toString().slice(0,10)
                    this.$store.state.chuckMap.set(data[i].id, data[i])
                }
                this.setChuckList(data)
                
                // 얼굴 분류 정보 불러오기
                api.get(`pictures/gallery?groupId=${this.getSelectedGroup.id}`)
                .then(({ data }) => {

                    this.$store.commit('setFaceDataGallery', data)

                    // 스튜디오 정보 불러오기
                    api.get(`pictures/studio?groupId=${this.getSelectedGroup.id}`)
                    .then(({ data }) => {
                        this.$store.commit('setFaceDataStudio', data)
                        
                        // 클라우드 이미지 정보 불러오기
                        api.get(`kakao/list?id=${this.getId}`)
                        .then(({ data }) => {
                            this.setCloudImages(data)
                            
                            // 페이지 이동
                            this.$store.dispatch('updateInit')
                            this.$router.push('/diary')
                        })
                        .catch(( { error }) => {
                            console.log(error)  
                            alert("error")
                            this.$router.push('/group')
                            
                        })
                    })
                    .catch(( { error }) => {
                        console.log(error)  
                        alert("error")
                        this.$router.push('/group')
                        
                    })
                })
                .catch(( { error }) => {
                    console.log(error)
                    alert("error")
                    this.$router.push('/group')
                })
            })
            .catch(( { error }) => {
                console.log(error)
                alert("error")
                this.$router.push('/group')
            })
        },
    }
}

</script>

<style scoped>


h1 {
  color: #8D6262;
  text-align: center;
  font-family: sans-serif;
  text-transform: uppercase;
  font-size: 20px;
  position: relative;
}

h1:after {
    position: absolute;
    content: "";
    -webkit-animation: Dots 2s cubic-bezier(0, .39, 1, .68) infinite;
    animation: Dots 2s cubic-bezier(0, .39, 1, .68) infinite;
}

.loader {
    margin: 15% auto 30px;
}

.book {
    border: 4px solid #8D6262;
    width: 60px;
    height: 45px;
    position: relative;
    perspective: 150px;
}

.page {
    display: block;
    width: 30px;
    height: 45px;
    border: 4px solid #8D6262;
    border-left: 1px solid #8D6262;
    margin: 0;
    position: absolute;
    right: -4px;
    top: -4px;
    overflow: hidden;
    background: #FFFFFF;
    transform-style: preserve-3d;
    -webkit-transform-origin: left center;
    transform-origin: left center;
}

.book .page:nth-child(1) {
    -webkit-animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.6s infinite;
    animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.6s infinite;
}

.book .page:nth-child(2) {
    -webkit-animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.45s infinite;
    animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.45s infinite;
}

.book .page:nth-child(3) {
    -webkit-animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.2s infinite;
    animation: pageTurn 1.2s cubic-bezier(0, .39, 1, .68) 1.2s infinite;
}


/* Page turn */

@-webkit-keyframes pageTurn {
    0% {
        -webkit-transform: rotateY( 0deg);
        transform: rotateY( 0deg);
    }
    20% {
        background: #FFFFFF;
    }
    40% {
        background: #FFFFFF;
        -webkit-transform: rotateY( -180deg);
        transform: rotateY( -180deg);
    }
    100% {
        background: #FFFFFF;
        -webkit-transform: rotateY( -180deg);
        transform: rotateY( -180deg);
    }
}

@keyframes pageTurn {
    0% {
        transform: rotateY( 0deg);
    }
    20% {
        background: #FFFFFF;
    }
    40% {
        background: #FFFFFF;
        transform: rotateY( -180deg);
    }
    100% {
        background: #FFFFFF;
        transform: rotateY( -180deg);
    }
}


/* Dots */

@-webkit-keyframes Dots {
    0% {
        content: "";
    }
    33% {
        content: ".";
    }
    66% {
        content: "..";
    }
    100% {
        content: "...";
    }
}

@keyframes Dots {
    0% {
        content: "";
    }
    33% {
        content: ".";
    }
    66% {
        content: "..";
    }
    100% {
        content: "...";
    }
}
</style>