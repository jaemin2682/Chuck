<template>
    <div style="margin:30px 0px 0px 30px;">
        <div style="margin-top:250px; cursor:pointer" @click="nextStep">
            <div class="studioInfo">
                <h3>
                <p>Chuck을 하나의 영상으로<br>
                만들어 간직하세요</p>
                </h3>
            </div>
            <img src='../../assets/video_make.svg' style="width:250px; height:250px;" ><br>
            <img src='../../assets/title/chuckFilm_tabtitle.svg' class="subtitle mt-10">
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
export default {
    methods: {
        ...mapMutations([
            'setVisibleChoice',
            'setVisibleAlbum',
            'setVisibleVideo',
            'setVisiblePreview',
        ]),
        nextStep() {
            this.setVisibleChoice(false)
            this.setVisibleAlbum(false)
            this.setVisibleVideo(true)
            this.setVisiblePreview(false)
        }
    },
    computed: {
        ...mapGetters([
            'getChuckList',
            'getSelectedDiary',
            'getVisibleCalendar',
        ]),
    }
}
</script>

<style scoped>
.studioInfo {
    position: absolute;
    left: 350px;
    top: 150px;
    text-align: left;
    color: #8D6262;
}
</style>