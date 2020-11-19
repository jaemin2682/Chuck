<template>
    <div style="margin:30px 30px 0px 0px;">
        <img src='../../assets/title/chuckFilm_tabtitle.svg' class="subtitle">
        <div class="dash">
            <font size=4>노래 선택하기</font>
        </div>
        <div class="dash" style="height:540px;">
            <span class="wrap pointer" v-for="(music, index) in musics" :key="index" @mouseover="mouseover(index)" @mouseout="mouseout(index)" @click="selectMusic(index)">
                <div>
                    <div class="music" :style="bg[index]">
                        <MusicBar :id="'musicbar'+index" style="display:none;"></MusicBar>
                    </div>
                    <img :id="'videoMusic'+index" class="videoMusicNoneDisplay" src="../../assets/music_play.svg" style="position:relative; top:-150px; width:150px;">
                    <div style="padding:5px; padding: 5px; top: 150px;">{{ music.title }}</div>
                </div>
            </span>
            <audio id="audio0" src="" loop></audio>
            <audio id="audio1" src="../../assets/music/BeatYourCompetition.mp3" loop></audio>
            <audio id="audio2" src="../../assets/music/Breathe.mp3" loop></audio>
            <audio id="audio3" src="../../assets/music/Contigo.mp3" loop></audio>
            <audio id="audio4" src="../../assets/music/Fingertips.mp3" loop></audio>
            <audio id="audio5" src="../../assets/music/MMXX.mp3" loop></audio>
            <audio id="audio6" src="../../assets/music/Neon.mp3" loop></audio>
            <audio id="audio7" src="../../assets/music/NoYeah.mp3" loop></audio>
            <audio id="audio8" src="../../assets/music/Sahara.mp3" loop></audio>
            <audio id="audio9" src="../../assets/music/SummerRain.mp3" loop></audio>
            <audio id="audio10" src="../../assets/music/Waves.mp3" loop></audio>
        </div>
        <div class="dash pointer" @click="previousStep">
            <font size=4>이전 단계로</font>
        </div>
    </div>
</template>

<script>
import { mapMutations } from 'vuex'
import MusicBar from './musicbar.vue'
export default {
    components: {
        MusicBar,
    },
    data () {
        return {
            bg: [
                {
                    backgroundImage: `url(${require('../../assets/music/image/no_music.svg')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/BeatYourCompetition.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/Breathe.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/Contigo.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/fingertips.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/MMXX.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/Neon.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/NoYeah.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/Sahara.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/SummerRain.png')})`
                },
                {
                    backgroundImage: `url(${require('../../assets/music/image/Waves.png')})`
                },
            ],
            musics:
            [
                {
                    title: '음악 없음',
                    value: 'middle'
                },
                {
                    title: 'BeatYourCompetition',
                    value: 'BeatYourCompetition.mp3'
                },
                {
                    title: 'Breathe',
                    value: 'Breathe.mp3'
                },
                {
                    title: 'Contigo',
                    value: 'Contigo.mp3'
                },
                {
                    title: 'Fingertips',
                    value: 'Fingertips.mp3'
                },
                {
                    title: 'MMXX',
                    value: 'MMXX.mp3'
                },
                {
                    title: 'Neon',
                    value: 'Neon.mp3'
                },
                {
                    title: 'NoYeah',
                    value: 'NoYeah.mp3'
                },
                {
                    title: 'Sahara',
                    value: 'Sahara.mp3'
                },
                {
                    title: 'SummerRain',
                    value: 'SummerRain.mp3'
                },
                {
                    title: 'Waves',
                    value: 'Waves.mp3'
                },
            ]
        }
    },
    methods: {
        ...mapMutations([
            'setVisibleChoice',
            'setVisibleAlbum',
            'setVisibleVideo',
            'setVisiblePreview',
            'setVideoMusic',
        ]),
        previousStep() {
            let el
            for(let i=0; i<this.musics.length; i++) {
                el = document.getElementById('audio'+i)
                el.load()
                el = document.getElementById('musicbar'+i)
                el.style.display = "none"
            }
            this.setVisibleChoice(false)
            this.setVisibleAlbum(false)
            this.setVisibleVideo(true)
            this.setVisiblePreview(false)
        },
        mouseover(index) {
            let el = document.getElementById('videoMusic'+index)
            el.setAttribute('class', '')
        },
        mouseout(index) {
            let el = document.getElementById('videoMusic'+index)
            el.setAttribute('class', 'videoMusicNoneDisplay')
        },
        selectMusic(index) {
            let el
            for(let i=0; i<this.musics.length; i++) {
                el = document.getElementById('audio'+i)
                el.load()
                el = document.getElementById('musicbar'+i)
                el.style.display = "none"
            }
            if(index > 0) {
                el = document.getElementById('musicbar'+index)
                el.style.display = "block"
            }
            el = document.getElementById('audio'+index)
            el.play()
            
            this.setVideoMusic(this.musics[index].value)

			let mediaPlayer = document.getElementById('media-video')
			mediaPlayer.load()
			mediaPlayer.play()
        }
    }
}
</script>

<style>
.wrap {
    height: 200px;
    width: 150px;
    margin: 7px;
    display: inline-block;
    box-sizing: border-box;
    overflow: hidden;
}
.music {
    height: 150px;
    width: 150px;
    background-size: cover;
}
.music img {
    height: 100%;
}
.videoMusicNoneDisplay {
    display: none;
}
</style>