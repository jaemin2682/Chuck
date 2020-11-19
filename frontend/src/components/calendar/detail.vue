<template>
    <div style="padding:30px 30px 0px 0px;">
        <div style="padding-bottom:70px;">
            <span class="float-left ml-10">
                <img src="../../assets/logo.svg" class="logo">
            </span>
            <span class="float-right mr-10 mt-4">
                <font size=5 v-if="getBackState != 2">{{ this.getSelectedDay }}</font>
                <font size=5 v-else>{{ this.getChuckMap.get(this.getSelectedDiary).date }}</font>
            </span>
        </div>
        <div v-if="getChuckMap.get(getSelectedDiary)" style="margin: 10px 30px 10px 30px;">
            <v-carousel height="400" hide-delimiter-background :hide-delimiters=true>
                <v-carousel-item class="kpa" v-for="(item, i) in getChuckMap.get(getSelectedDiary).image" :key="i" :src="item" target></v-carousel-item>
            </v-carousel>
        </div>
        <div class="mb-15">
            <span v-if="getChuckMap.get(getSelectedDiary)" class="float-left ml-10" style="font-size:large">
                by. {{ getChuckMap.get(getSelectedDiary).writer }}
            </span>
            <span class="float-right mr-10">
                <a :href="picture" @click="getImageUrl()" target="_blank" download style="text-decoration:none;">
                    <img src="../../assets/download.svg" style="width:20px;">
                </a>
                <v-menu
                    offset-y
                    v-model="menu"
                    v-if="this.getSelectedDiary !== '' && this.getChuckMap.get(this.getSelectedDiary)
                    &&this.getId == this.getChuckMap.get(this.getSelectedDiary).writerId"
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-icon v-bind="attrs" v-on="on" style="margin:0px 0px 10px 5px;">
                            mdi-dots-horizontal
                        </v-icon>
                    </template>
                    <v-list>
                        <v-list-item class="pointer" @click="edit">
                            <v-list-item-title>수정하기</v-list-item-title>
                        </v-list-item>
                        <v-dialog v-model="dialog" max-width="290">
                            <template v-slot:activator="{ on, attrs }">
                                <v-list-item
                                    class="pointer"
                                    v-bind="attrs"
                                    v-on="on"
                                >
                                    <v-list-item-title>삭제하기</v-list-item-title>
                                </v-list-item>
                            </template>
                            <v-card>
                                <v-card-title class="headline"></v-card-title>
                                <v-card-text><b>정말로 삭제하시겠습니까?</b></v-card-text>
                                <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" text @click="remove()">확인</v-btn>
                                <v-btn color="green darken-1" text @click="clear()">취소</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-list>
                </v-menu>
            </span>
        </div>
        <div v-if="getChuckMap.get(getSelectedDiary)" class="mb-5" style="font-size:30px; padding:0px 30px 0px 40px; text-align:left;">
            {{ getChuckMap.get(getSelectedDiary).title }}
            <div class="underline" style="margin-top:0px;"></div>
        </div>
        <div v-if="getChuckMap.get(getSelectedDiary) && !changeContent" style="font-size:20px; padding:0px 30px 0px 40px; text-align:left; color:#2F2F2F;" v-html="getChuckMap.get(getSelectedDiary).content">
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
import api from '@/utils/api'
import eventBus from '@/utils/EventBus'
import axios from 'axios'
import store from '@/store'

export default {
    data () {
        return {
            picture: '',
            content: '',
            changeContent: false,
            dialog: false,
            menu: false,
        }
    },
    computed: {
        ...mapGetters([
            'getSelectedDay',
            'getChuckList',
            'getSelectedDiary',
            'getId',
            'getChuckMap',
            'getBackState',
            'getSelectedGroup',
        ]),
    },
    methods: {
        ...mapMutations([
            "setVisibleCalendar",
            "setVisibleDetail",
            "setVisibleWrite",
            "setVisibleChoice",
            "setVisibleAlbum",
            "setVisibleVideo",
            "setVisiblePreview",
            "setSelectedDay",
            "setBackState",
        ]),
        edit() {
            const diary = this.getChuckMap.get(this.getSelectedDiary)
            this.$store.dispatch('updateModify', true)
            this.setVisibleWrite(true);
            this.setVisibleDetail(false);
            this.setVisibleCalendar(false);
            diary.image.forEach(e => {
                this.$store.state.images.push(e)
            })
            eventBus.$emit('modifyDiary', diary)
        },
        remove() {
            this.dialog = false
            this.menu = false
            api.delete(`pictures/deleteByDiary?diary_id=${this.getSelectedDiary}&group_id=${this.getSelectedGroup.id}`)
            .then(() => {
                api.delete(`diaries/${this.getSelectedDiary}`, {
                    headers: {
                        token: this.$store.getters.getToken,
                    }
                }).then(() => {
                    api.get(`pictures/gallery?groupId=${this.getSelectedGroup.id}`)
                    .then(({ data }) => {
                        this.$store.commit('setFaceDataGallery', data)
                        api.get(`pictures/studio?groupId=${this.getSelectedGroup.id}`)
                        .then(({ data }) => {
                            this.$store.commit('setFaceDataStudio', data)
                            this.$store.dispatch('delChuckList', {index: this.getSelectedDiary, id: this.getSelectedDiary})
                            eventBus.$emit('updateList')
                            eventBus.$emit('back')
                            this.$notify({
                                title: 'Chuck이 삭제 되었습니다.',
                                dangerouslyUseHTMLString: true,
                                duration: 3000
                            });
                        })
                    })
                })
            })
        },
        getImageUrl() {
            let num = $('.kpa').length
            let picture = ''
            for(let i=0; i<num; i++) {
                if($('.kpa').eq(i).css('display') == 'block') {
                    picture = $('.kpa').eq(i).children().children().eq(1).css('backgroundImage')
                }
            }
            picture = picture.slice(5, -2)
            this.picture = picture
        },
        download() {
            this.getImageUrl();
            axios({
                method: 'get',
                url: this.picture,
                responseType: 'blob'
            }).then((res) => {
                this.forceFileDownload(res)
            })
        },
        forceFileDownload(response) {
            const headers = response.headers
            const blob = new Blob([response.data], {type: headers['content-type']})
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(blob)
            link.download = this.picture
            document.body.appendChild(link)
            link.click()
            link.remove()
        },
        cancle() {
            this.dialog = false
            this.changeContent = false
            this.content = ''
        },
        clear() {
            this.dialog = false
            this.menu = false
        },
    }
}
</script>

<style>

</style>