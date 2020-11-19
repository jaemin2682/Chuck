<template>
    <div style="margin:30px 0px 0px 30px;">
        <div class="dash">
            <font size=4>동영상 미리보기</font>
        </div>
		<div class="dash" style="height:620px; padding:20px;">
			<div class='main'>
				<div id='media-player' width="100%">
					<video id='media-video' loop width="100%" autoplay>
					</video>
				</div>
				<div id='media-controls' class='ctrl-box'>
					<div id="play-bar" class="play-bar"></div>
					<div class="play-bar-border"></div>
				</div>
				선택된 노래 : 
				<span v-if="getVideoMusic === 'middle'">없음</span>
				<span v-else>{{ getVideoMusic.replace('.mp3', '') }}</span>
			</div>
        </div>
        <div class="dash pointer" @click="download">
            <font size=4>동영상 다운로드</font>
        </div>
		<v-dialog v-model="loading" hide-overlay persistent width="300">
            <v-card color="#8D6262" dark>
                <v-card-text>
                    Encoding Chuck Film...
                    <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import api from '@/utils/api'
export default {
	data () {
		return {
			loading: false
		}
	},
	mounted() {
		let mediaPlayer = document.getElementById('media-video')
		mediaPlayer.controls = false
		mediaPlayer.addEventListener('timeupdate', updateProgressBar, false)

		function updateProgressBar() {
			var progressBar = document.getElementById('play-bar');
			var percentage = Math.floor((100 / mediaPlayer.duration) * mediaPlayer.currentTime)
			progressBar.style.width = percentage + '%'
			if(mediaPlayer.ended) {
				progressBar.style.width = '0%'
			}
		}
	},
	computed: {
        ...mapGetters([
			'getId',
            'getVideoSrc',
			'getVideoMusic',
			'getVideoUrl',
        ]),
    },
	methods: {
		download() {
			let el
			el = document.getElementsByTagName('audio')
			for(let i=0; i<el.length; i++) {
				el[i].load()
				document.getElementById('musicbar'+i).style.display = "none"
			}
			document.getElementById('media-video').load()
		
			if(this.getVideoMusic == 'middle') {
				let link = document.createElement('a')
				link.target = "_blank"
				link.href = this.getVideoUrl
				link.download = 'ChuckFilm.mp4'
				link.click()
				link.remove()
				this.loading = false
			}
			else {
				this.loading = true
				api.post('pictures/mkVideo', {
					'music': this.getVideoMusic,
					'userId': this.getId,
					'path_list': this.getVideoSrc,
				})
				.then(({ data }) => {
					console.log(data)
					let link = document.createElement('a')
					link.target = "_blank"
					link.href = data
					link.download = 'ChuckFilm.mp4'
					link.click()
					link.remove()
					this.loading = false
				})
			}
		}
	}
}
</script>

<style scoped>
@import url(https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css);
.main {
	position: absolute;
	width: 500px;
	height: 500px;
}

#media-player {
	display: block;
	position: absolute;
	top: 0;
	bottom: 64px;
	left: 0;
	right: 0;
}

#media-video {
	display: block;
	width: 100%;
	height: 100%;
	object-fit: contain;
}

.ctrl-box {
	display: block;
	position: absolute;
	left: 0;
	right: 0;
	height: 64px;
	bottom: 0;
	margin-top: -5px;
}

.play-bar {
	width: 0%;
	display: block;
	position: absolute;
	bottom: 7px;
	left: 0;
	right: 0;
	height: 8px;
	background-color: #8D6262;
	border-radius: 5px;
}
.play-bar-border {
	display: block;
	position: absolute;
	bottom: 7px;
	left: 0;
	right: 0;
	height: 8px;
	border: solid 1px #8D6262;
	border-radius: 5px;
}

</style>