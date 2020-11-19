<template>
    <div style="margin:30px 30px 0px 0px;">
        <div class="text-left ml-8">
            <img src="../../assets/title/studio_tabtitle.svg" class="tabtitle">
            <div class="underline"></div>
        </div>
        <div style="margin-top:140px; cursor:pointer" @click="nextStep">
            <div class="studioInfo">
                <h3>
                <p>Chuck을 하나의 문서로<br>
                만들어 간직하세요</p>
                </h3>
            </div>
            <img src='../../assets/photo_album.svg' style="width:250px; height:250px;"><br>
            <img src='../../assets/title/chuckBook_tabtitle.svg' class="subtitle mt-10">
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
            this.setVisibleAlbum(true)
            this.setVisibleVideo(false)
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