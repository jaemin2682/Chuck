<template>
<div>
    <div class="left">
        <div class="text-left" style="padding:30px 0px 0px 30px;">
            <img src="../../assets/logo.svg" class="subtitle">
            <div class="groupInfo">
                <font size=5>그룹명</font>
                <div class="information">
                    <font size=5>
                    {{this.groupInfo.name}}
                    </font>
                </div>
                <font size=5>그룹 생성일</font>
                <div class="createDate">
                    {{this.groupInfo.publishedDate | moment('YYYY.MM.DD')}}
                </div>
                <font size=5>그룹장</font>
                <div>
                    {{this.groupInfo.ownerName}}
                </div>
                <font size=5>그룹소개</font>
                    <div id="group_intro">
                    </div>
            </div>
        </div>
        <div class="right">
        </div>
    </div>
</div>
</template>

<script>
import store from '@/store';

export default {
    data() {
        return {
            intro: '',
        };
    },
    computed: {
        groupInfo: () => store.getters.getSelectedGroup,
    },
    mounted() {
        // 아직 그룹 입력시 br 태그를 못 받아서 임시로 함
        const len = this.groupInfo.intro.length / 20;
        for(var i=0; i<len; i++) {
            this.intro += this.groupInfo.intro.substring(20*i, 20*(i+1));
            this.intro += '<br/>';
        }
        $('#group_intro').html(this.intro);
    }

}
</script>

<style scoped>
.left {
    text-align: left;
    width: 300px;
    height: 767px;
    background-color: #FFF8F8;
    box-shadow: 2px 0px 10px 1px #E2E2E2;
}
.groupInfo {
    position: absolute;
    bottom: 0px;
}
.groupInfo > div{
    margin-bottom: 24px;
    color: #2d2d2d;
}
</style>