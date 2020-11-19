<template>
  <div id="canvas">
    <div id="book-zoom">
      <div class="sample-docs animated">
        <!-- tabs area -->
        <div ignore="1" class="tabs">
          <div class="left">
            <button
              ref="btn0"
              v-on:click="movePage(0)"
              v-show="!buttonArray[0]"
              id="timeline"
              :style="{
                background: 'url(' + this.backgroundImageArray[0] + ')',
                top: '60px',
              }"
            ></button>
            <button
              ref="btn1"
              v-on:click="movePage(1)"
              v-show="!buttonArray[1]"
              id="calender"
              :style="{
                background: 'url(' + this.backgroundImageArray[1] + ')',
                top: '120px',
              }"
            ></button>
            <button
              ref="btn2"
              v-on:click="movePage(2)"
              v-show="!buttonArray[2]"
              id="album"
              :style="{
                background: 'url(' + this.backgroundImageArray[2] + ')',
                top: '180px',
              }"
            ></button>
            <button
              ref="btn3"
              v-on:click="movePage(3)"
              v-show="!buttonArray[3]"
              id="chuck"
              :style="{
                background: 'url(' + this.backgroundImageArray[3] + ')',
                top: '240px',
              }"
            ></button>
            <button
              ref="btn4"
              v-on:click="movePage(4)"
              v-show="!buttonArray[4]"
              id="info"
              :style="{
                background: 'url(' + this.backgroundImageArray[4] + ')',
                top: '300px',
              }"
            ></button>
          </div>
          <div class="right">
            <button
              v-on:click="movePage(0)"
              v-show="buttonArray[0]"
              id="timeline"
            ></button>
            <button
              v-on:click="movePage(1)"
              v-show="buttonArray[1]"
              id="calender"
            ></button>
            <button
              v-on:click="movePage(2)"
              v-show="buttonArray[2]"
              id="album"
            ></button>
            <button
              v-on:click="movePage(3)"
              v-show="buttonArray[3]"
              id="chuck"
            ></button>
            <button
              v-on:click="movePage(4)"
              v-show="buttonArray[4]"
              id="info"
            ></button>
          </div>
        </div>

        <!-- book area -->
        <div class="hard"></div>
        <!-- timeline -->
        <div><Intro /></div>
        <div><Timeline /></div>
        <!-- calender -->
        <div>
          <Calendar v-show="this.getVisibleCalendar" />
          <Detail v-show="this.getVisibleDetail" />
          <Write1 v-show="this.getVisibleWrite" />
        </div>
        <div>
          <List v-if="this.getVisibleCalendar" />
          <Comment v-show="this.getVisibleDetail" />
          <Write2 v-show="this.getVisibleWrite" />
        </div>
        <!-- gallery -->
        <div>
          <PersonClassification></PersonClassification>
        </div>
        <div>
          <NoResult v-show="!this.getPersonClassificationResult"></NoResult>
          <ResultGallery
            v-show="this.getPersonClassificationResult"
          ></ResultGallery>
        </div>
        <!-- studio -->
        <div>
          <Album1 v-show="this.getVisibleChoice" />
          <Album2 v-show="this.getVisibleAlbum" />
          <Video2 v-show="this.getVisibleVideo" />
          <Video4 v-show="this.getVisiblePreview" />
        </div>
        <div>
          <Video1 v-show="this.getVisibleChoice" />
          <Album3 v-show="this.getVisibleAlbum" />
          <Video3 v-show="this.getVisibleVideo" />
          <Video5 v-show="this.getVisiblePreview" />
        </div>
        <!-- groupset -->
        <div>
          <Groupset></Groupset>
        </div>
        <div>
          <Outro></Outro>
        </div>
        <div class="hard p29"></div>
        <div class="hard p30"></div>
      </div>
    </div>
    <img id="pen" src="@/assets/pen.png" alt="" @click="write" />
    <img class="pointer" id="back" src="@/assets/back_button.svg" @click="back" v-show="!this.getVisibleCalendar">
    <Mypage></Mypage>
    <assginGroupModal v-if="getVisibleModalAssignGroup"></assginGroupModal>
    <secedeGroupModal v-if="getVisibleModalSecedeGroup"></secedeGroupModal>
  </div>
</template>

<script>
import $ from "jquery";
import { mapGetters } from "vuex";
import { mapMutations } from 'vuex';
import eventBus from '@/utils/EventBus';

// timeline
import Intro from "@/components/timeline/intro.vue";
import Timeline from "@/components/timeline/timeline.vue";

// calender
import Calendar from "@/components/calendar/calendar.vue";
import List from "@/components/calendar/list.vue";
import Detail from "@/components/calendar/detail.vue";
import Comment from "@/components/calendar/comment.vue";
import Write1 from "@/components/calendar/write1.vue";
import Write2 from "@/components/calendar/write2.vue";

// gallery
import PersonClassification from "@/components/gallery/personClassification.vue";
import NoResult from "@/components/gallery/noResult.vue";
import ResultGallery from "@/components/gallery/resultAlbum.vue";

// studio
import Album1 from "@/components/album/pick.vue";
import Album2 from "@/components/album/selectPerson.vue";
import Album3 from "@/components/album/preview.vue";
import Video1 from "@/components/video/pick.vue";
import Video2 from "@/components/video/selectPerson.vue";
import Video3 from "@/components/video/selectImage.vue";
import Video4 from "@/components/video/selectMusic.vue";
import Video5 from "@/components/video/preview.vue";

// groupset
import Groupset from "@/components/groupset/groupset.vue";
import Outro from "@/components/groupset/outro.vue";
import assginGroupModal from "@/components/groupset/assginGroupModal.vue";
import secedeGroupModal from "@/components/groupset/secedeGroupModal.vue";

import Mypage from "@/components/mypage/mypage.vue";

export default {
  components: {
    Calendar,
    List,
    Detail,
    Comment,
    Write1,
    Write2,
    PersonClassification,
    NoResult,
    ResultGallery,
    Timeline,
    Intro,
    Album1,
    Album2,
    Album3,
    Video1,
    Video2,
    Video3,
    Video4,
    Video5,
    Groupset,
    Outro,
    Mypage,
    assginGroupModal,
    secedeGroupModal,
  },
  data() {
    return {
      // 타임라인, 캘린더, 사진첩, 추억, 그룹정보
      buttonArray: [false, true, true, true, true],
      buttonName: ["timeline", "calendar", "gallery", "studio", "groupset"],
      backgroundImageArray: [],
    };
  },
  created() {
    if(!this.getInit) {
      this.$router.push('/fetch')
    }
    eventBus.$on('movePage', (data) => {
      this.movePage(data.index);
      this.setBackState(data.state);
      eventBus.$emit('showDetail', data.item);
    });
    eventBus.$on('back', () => {
      this.back();
    })
  },
  computed: {
    ...mapGetters([
      "getChuckList",
      "getSelectedDiary",
      "getSelectedGroup",
      "getVisibleCalendar",
      "getVisibleDetail",
      "getVisibleWrite",
      "getPersonClassificationResult",
      "getVisibleChoice",
      "getVisibleAlbum",
      "getVisibleVideo",
      "getVisiblePreview",
      "getBackState",
      "getVisibleModalAssignGroup",
      "getVisibleModalSecedeGroup",
      "getInit",
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
    movePage(num) {
      eventBus.$emit('clearWrite')
      this.$store.dispatch('updateModify', false)
      this.init()
      $(".sample-docs").turn("disable", false);
      $(".sample-docs").turn("page", 2 + 2 * num);
      for (let i = 0; i < 5; i++) {
        if (i < num) {
          this.$set(this.buttonArray, i, false);
          this.$set(
            this.backgroundImageArray,
            i,
            require("@/assets/img_tab/" +
              this.buttonName[i] +
              "_left_nonselected.png")
          );
        } else if (i == num) {
          // 클릭한 탭 background img 변경
          this.$set(this.buttonArray, i, false);
          this.$set(
            this.backgroundImageArray,
            i,
            require("@/assets/img_tab/" +
              this.buttonName[i] +
              "_left_selected.png")
          );
        } else {
          this.$set(this.buttonArray, i, true);
        }
      }
      $(".sample-docs").turn("disable", true);
      if(num == 1) {
        this.setSelectedDay(new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+ (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate()));
        eventBus.$emit('updateCalendar');
        eventBus.$emit('updateList');
      }
    },
    // 글작성 페이지 이동
    write() {
      // 캘린더 페이지 이동
      eventBus.$emit('init-el')
      this.movePage(1);
      this.setSelectedDay(new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+ (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate()));
      this.setVisibleWrite(true);
      this.setVisibleDetail(false);
      this.setVisibleCalendar(false);
    },
    // init v-show
    init(){
      // calender
      this.setVisibleWrite(false);
      this.setVisibleDetail(false);
      this.setVisibleCalendar(true);
      // studio
      this.setVisibleChoice(true);
      this.setVisibleAlbum(false);
      this.setVisibleVideo(false);
      this.setVisiblePreview(false);
    },
    back(){
      eventBus.$emit('clearImage')
      eventBus.$emit('clearWrite')
      this.$store.dispatch('updateModify', false)
      if(this.getBackState === 1) {
        // calender
        this.setVisibleWrite(false);
        this.setVisibleDetail(false);
        this.setVisibleCalendar(true);
      } else if(this.getBackState == 2) {
        // TimeLine
        this.movePage(0);
        this.setBackState(1);
      } else {
        // Gallery
        this.movePage(2);
        this.setBackState(1);
      }
    }
  },
  mounted() {
    $(".sample-docs").turn({
      elevation: 50,
      acceleration: false,
      gradients: true,
      autoCenter: true,
      duration: 1000,
      pages: 30,
      page: 2,
      when: {},
    });
    // turn 기능 막기
    $(".sample-docs").turn("disable", true);

    // url -> base64 encode
    for (let index = 0; index < this.buttonName.length; index++) {
      const element = this.buttonName[index];
      this.backgroundImageArray.push(
        require("@/assets/img_tab/" + element + "_left_selected.png")
      );
    }
  },
};
</script>

<style scoped>
@import "../styles/docs.css";
#pen {
  cursor: pointer;
  width: 34px;
  height: 661px;
  position: relative;
  top: -20px;
  left: 40px;
}
#pen:hover{
  transition: all ease-in-out 0.2s;
  top: -60px;
}
#back {
  width: 160px;
  position: relative;
  left: -800px;
  top: 30px;
}
</style>