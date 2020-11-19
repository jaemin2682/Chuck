<template>
    <div style="padding:30px 0px 0px 30px;">
        <font v-if="!getModify" size=6>{{ getSelectedDay }}</font>
        <font v-else size=6>{{ this.date }}</font>
        <input type="text" autocomplete="off" placeholder="오늘의 Chuck을 적어주세요." class="el-input__inner" v-model="title">
        <hr size="0.5" color="#E0E0E0" width="88%">
        <el-input type="textarea" :rows="19" placeholder="Chuck에 대한 설명을 적어주세요."  resize="none" v-model="content" style="margin-top:10px;">
        </el-input>
        <div class="solid pointer" v-if="!getModify" @click.prevent="write">
            <font size=4>작성 완료</font>
        </div>
        <div class="solid pointer" v-else @click.prevent="modify">
            <font size=4>수정 완료</font>
        </div>
        <v-dialog v-model="loading" hide-overlay persistent width="300">
            <v-card color="#8D6262" dark>
                <v-card-text>
                    Chuck 기록중...
                    <v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
                </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import eventBus from '@/utils/EventBus';
import api from '../../utils/api.js';
import moment from 'moment';

export default {
    data() {
        return {
            title: '',
            content: '',
            date: '',
            id: '',
            loading: false,
        }
    },
    created() {
        eventBus.$on('clearWrite', () => {
            this.clear()
        });
        eventBus.$on('modifyDiary', (data) => {
            this.id = data.id
            this.title = data.title
            this.content = data.content
            this.date = data.date
        });
    },
    computed: {
        ...mapGetters([
            'getSelectedDay',
            'getId',
            'getSelectedDay',
            'getSelectedGroup',
            'getToken',
            'getModify',
            'getChuckMap',
            'getImages',
            'getDeletedImages',
            'getColor',
        ])
    },
    methods: {
        write() {
            if(this.getImages.length > 0) {
                this.loading=true
                api.post('diaries', {
                    title: this.title,
                    content: this.content,
                    writer: this.getId,
                    date: moment(this.getSelectedDay).tz('Asia/Seoul').format(),
                    groupId: this.getSelectedGroup.id,
                },{
                    headers: {
                        token: this.getToken
                    }   
                })
                .then(({ data }) => {
                    api.post(`pictures/insert`, {
                        diary_id: data.id,
                        group_id: this.getSelectedGroup.id,
                        path_list: this.getImages,
                    }).then(() => {
                        this.getDeletedImages.forEach(e => {
                            api.delete(`pictures/deleteByPath?path=${e}`)
                        })
                        api.get(`diaries/${data.id}`, {
                            headers: {
                                token: this.$store.getters.getToken,
                            }
                        }).then(({data}) => {
                            const image = data.image.split(';')
                            data.image = image
                            data.color = this.getColor[(this.getChuckMap.size + 1) % 10]
                            data.date = data.date.toString().slice(0,10)
                            this.$store.dispatch('addChuckList', data)
                            this.done()
                        })
                    })
                });
            } else {
                this.$notify({
                    title: '사진을 한 장 이상 올려주세요.',
                    dangerouslyUseHTMLString: true,
                    duration: 3000
                });
            }
        },
        done() {
            this.title = ''
            this.content = ''
            this.id = ''
            this.date = ''
            this.$store.state.images = new Array()
            this.$store.state.deletedImages = new Array()
            api.get(`pictures/gallery?groupId=${this.getSelectedGroup.id}`)
            .then(({ data }) => {
                this.$store.commit('setFaceDataGallery', data)
                api.get(`pictures/studio?groupId=${this.getSelectedGroup.id}`)
                .then(({ data }) => {
                    this.$store.commit('setFaceDataStudio', data)
                    this.$store.commit('setVisibleWrite', false)
                    this.$store.commit('setVisibleCalendar', true)
                    this.$store.commit('setVisibleChoice', true)
                    this.$notify({
                        title: '새로운 Chuck이 기록되었습니다.',
                        dangerouslyUseHTMLString: true,
                        duration: 3000
                    })
                    this.loading=false
                })
            })
            eventBus.$emit('updateList')
            
        },
        clear() {
            this.title = ''
            this.content = ''
            this.id = ''
            this.date = ''
            this.$store.state.images = new Array()
            this.$store.state.deletedImages = new Array()
        },
        modify() {
            if(this.getImages.length > 0) {
                api.put(`diaries/${this.id}`, {
                        content: this.content,
                        title: this.title,
                        writerId: this.getChuckMap.get(this.id).writerId,
                        id: this.id,
                }, {
                    headers: {
                        token: this.$store.getters.getToken,
                    }
                }).then(() => {
                    api.put(`pictures/modify`, {
                        diary_id: this.id,
                        group_id: this.getSelectedGroup.id,
                        path_list: this.getImages,
                    }).then((data) => {
                        api.get(`diaries/${this.id}`, {
                            headers: {
                                token: this.$store.getters.getToken,
                            }
                        }).then(({data}) => {
                            const images = data.image.split(';')
                            this.$store.state.chuckMap.get(this.id).content = this.content
                            this.$store.state.chuckMap.get(this.id).title = this.title
                            this.$store.state.chuckMap.get(this.id).image = images
                            this.$store.dispatch('updateModify', false)
                            this.done()
                        })
                    })
                })
            } else {
                this.$notify({
                    title: '사진을 한 장 이상 올려주세요.',
                    dangerouslyUseHTMLString: true,
                    duration: 3000
                });
            }
        }
    }
}
</script>
<style scoped>
.el-input__inner {
    text-align:left;
    width: 91%;
    border: none;
    font-size:20px;
    margin-top: 20px;
}
</style>