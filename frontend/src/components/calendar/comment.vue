<template>
    <div style="padding:30px 0px 0px 30px; height:680px; overflow:scroll;">
        <div><img src="../../assets/title/comment_tabtitle.svg" style="height:24px; margin:10px 0px 30px 0px;"></div>
        <div v-for="(item, index) in getComments" :key="index" class="post-it">
            <div class="note" :class="{postodd:flagOddEven(index), posteven:!flagOddEven(index)}">
                <strong>{{ item.writer }}</strong>
                <v-dialog v-model="dialog" max-width="290">
                    <template v-slot:activator="{ on, attrs }">
                        <img src="@/assets/eraser.svg" alt="" v-show="getId==item.writerId" v-bind="attrs" v-on="on">
                    </template>
                    <v-card>
                        <v-card-title class="headline"></v-card-title>
                        <v-card-text><b>정말로 삭제하시겠습니까?</b></v-card-text>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="green darken-1" text @click="deleteComment(item)">확인</v-btn>
                        <v-btn color="green darken-1" text @click="cancel()">취소</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <p class="detail">{{ item.comment }}</p>
            </div>
        </div>
        <div class="comment">      
            <div class="el-input el-input-group el-input-group--append">
                <input 
                    type="text" 
                    autocomplete="off" 
                    placeholder="댓글 입력" 
                    class="el-input__inner" 
                    v-model="input" 
                    style="text-align:left"
                    @keypress.enter="comment()"
                >
                <div class="el-input-group__append" @click="comment()">
                    <i class="el-icon-edit"></i>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import store from '@/store'
import api from '@/utils/api'

export default {
    data() {
        return{
            input:'',
            dialog: false,
        }
    },
    computed: {
        ...mapGetters([
            'getComments',
            'getChuckMap',
            'getSelectedDiary',
            'getId',
        ]),
    },
    methods: {
        comment() {
            api.post(`/replies/insert`, {
                comment: this.input,
                diaryId: this.getChuckMap.get(this.getSelectedDiary).id,
                writerId: this.getId,
            }).then(({data}) => {
                store.dispatch('addComments', data)
                this.input = ''
            })
        },
        deleteComment(item) {
            this.dialog = false
            api.delete(`/replies/${item.id}`)
            .then((res)=>{
                store.dispatch('delComments', item)
                this.$notify({
                    title: '댓글이 삭제 되었습니다.',
                    dangerouslyUseHTMLString: true,
                    duration: 3000
                });
            })
        },
        flagOddEven(index){
            // 두번씩 true, false가 반복
            return (index/2%2)<1?true:false
        },
        cancel() {
            this.dialog = false
        }
    }
}
</script>

<style scoped>
.post-it {
    display: inline-block;
    padding: 20px;
}
.note { 
    -webkit-box-shadow: #DDD 0px 1px 2px;
    position: relative;
    background-color: #F4F39E;
    border-color: #DEE184;
    text-align: center;
    padding: 1.5em 1em;
    -webkit-box-shadow: 0px 1px 3px rgba(0,0,0,0.25);
    -moz-box-shadow: 0px 1px 3px rgba(0,0,0,0.25);
    box-shadow: 0px 1px 3px rgba(0,0,0,0.25);
    -webkit-transform: rotate(2deg);
    -moz-transform: rotate(2deg);
    -o-transform: rotate(2deg);
    -ms-transform: rotate(2deg);
    transform: rotate(2deg);
    width: 180px;
    height: 160px;
}
.note:after {
    display: block;
    content: "";
    position: absolute; 
    width: 110px;
    height: 30px;
    top: -21px;
    left: 20%;    
    background: rgba(230, 230, 230, 0.7);
    -webkit-box-shadow: 0px 0 3px rgba(0,0,0,0.1);
    -moz-box-shadow: 0px 0 3px rgba(0,0,0,0.1);
    box-shadow: 0px 0 3px rgba(0,0,0,0.1);  
}
.note img{
    position: absolute;
    cursor: pointer;
    left: 150px;
    top: 10px;
    width: 20px;
    height: 20px;
    filter: grayscale();
}
.note .detail{
    overflow: scroll;
    height: 100px;
}
.note strong{
    color: black;
}
.postodd{
    left: -30px;
}
.posteven{
    left: 30px;
}
.comment { 
    position: absolute; 
    bottom: 30px;
    left: 80px;
    width:500px;
}
.el-input-group__append {
    cursor: pointer;
}
</style>