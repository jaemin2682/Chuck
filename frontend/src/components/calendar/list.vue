<template>
    <div style="padding:30px 0px 0px 30px;">
        <!-- 상단 (날짜, 검색기능, 글쓰기 버튼) -->
        <v-row v-if="searchFlag">
            <span class="col-3">
            </span>
            <span class="col-6">
                <font size=6>{{ this.getSelectedDay }}</font>
            </span>
            <span class="col-3 float-right">
                <el-button class="iconColor" icon="el-icon-search" circle @click="openSearchBar"></el-button>
                <el-button  class="iconColor" icon="el-icon-edit" circle @click="write"></el-button>
            </span>
        </v-row>

        <v-row v-else>
            <span class="col-12 float-right">
                <div class="el-input el-input--suffix" style="width: 80%; margin:0px 12px 0px 26px; color:#8D6262; border-color:#8D6262">
                    <input type="text" autocomplete="off" class="el-input__inner iconColor" id="searchBar" v-model="keyword" @keydown.enter="search" style="text-align:left">
                    <span class="el-input__suffix">
                        <span class="el-input__suffix-inner">
                            <i class="el-input__icon el-icon-close pointer iconColor" @click="closeSearchBar"></i>
                        </span>
                    </span>
                </div>
                <el-button class="iconColor" icon="el-icon-edit" circle @click="write"></el-button>
            </span>
        </v-row>
    <!-- detail ( 검색결과, 날짜 클릭 결과, 오늘의 척 등록하세요 문구 ) -->
    <!-- 검색 클릭시 나오는 화면 -->
    <v-container v-if="!searchFlag" style="padding: 0px 20px 0px 20px">
      <div v-show="searchResult" style="text-align:left">'{{ searchResult }}' 검색 결과</div>
      <v-row dense>
        <v-col
          v-for="(item, i) in searchChuckList"
          :key="i"
          cols="12"
          @click="detail(item.id)"
          style="cursor: pointer"
        >
          <v-card>
            <div class="d-flex flex-no-wrap">
              <span :style="{ backgroundColor: item.color, color: item.color }"
                >dd</span
              >
              <v-avatar size="125" tile>
                <v-img :src="item.image[0]"></v-img>
              </v-avatar>
              <div style="width: 400px">
                <v-card-title>
                  <text-highlight :queries="queries"  :highlightStyle="styleObject"
                    >{{ item.title }}
                  </text-highlight>
                </v-card-title>
                <v-card-subtitle style="text-align: left; paadingbottom: 0px">
                  <!-- 검색어 하이라이트 -->
                  <text-highlight :queries="queries" :highlightStyle="styleObject">{{
                    item.content.slice(0, 70)
                  }}</text-highlight>
                </v-card-subtitle>
                <v-card-text>
                  <v-row>
                    <span
                      class="col-6"
                      style="text-align: left; padding: 0px 12px"
                    >
                      작성자 : {{ item.writer }}
                    </span>
                    <span
                      class="col-6"
                      style="text-align: right; padding: 0px 12px"
                    >
                      작성일 : {{ item.date }}
                    </span>
                  </v-row>
                </v-card-text>
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <!-- 날짜 클릭시 나오는 화면 -->
    <v-container
      v-else-if="selectedChuckList.length != 0"
      style="padding: 0px 20px 0px 20px"
    >
      <v-row dense>
        <v-col
          v-for="(item, i) in selectedChuckList"
          :key="i"
          cols="12"
          @click="detail(item.id)"
          style="cursor: pointer"
        >
          <v-card>
            <div class="d-flex flex-no-wrap">
              <div class="belt" :style="{ backgroundColor: item.color }"></div>
              <v-avatar size="130" tile style="margin: 5px 0px">
                <v-img :src="item.image[0]"></v-img>
              </v-avatar>
              <div style="width: 400px">
                <v-card-title style="font-size: 16px"
                  >{{ item.title }}
                </v-card-title>
                <v-card-subtitle
                  v-text="item.content.slice(0, 70)"
                  style="
                    text-align: left;
                    padding-bottom: 0px;
                    height: 50px;
                    font-size: 14px;
                  "
                ></v-card-subtitle>
                <hr style="width: 90%" color="#F2F2F2" />
                <v-card-text
                  style="padding: 10px 20px; font-size: 12px; color: #8d6262"
                >
                  <v-row>
                    <span
                      class="col-6"
                      style="text-align: left; padding: 0px 12px"
                    >
                      작성자 : {{ item.writer }}
                    </span>
                    <span
                      class="col-6"
                      style="text-align: right; padding: 0px 12px"
                    >
                      작성일 : {{ item.date }}
                    </span>
                  </v-row>
                </v-card-text>
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <v-container v-else style="padding: 0px 20px 0px 20px">
      <font size="5">오늘의 새로운 Chuck을 작성해 보세요</font>
    </v-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mapMutations } from "vuex";
import eventBus from "@/utils/EventBus";
import store from "@/store";

export default {
  data: () => ({
    searchFlag: true,
    searchResult: "",
    keyword: "",
    color: [],
    queries: [""],
    styleObject: {
      background:'#FBFFC8',
    },
  }),
  mounted() {
    this.color = this.$store.getters.getColor;
  },
  created() {
    eventBus.$on("updateList", () => {
      store.dispatch("updateSelectedChuckList");
    });
    eventBus.$on("showDetail", (data) => {
      this.detail(data.id);
    });
  },
  computed: {
    ...mapGetters(["getChuckList", "getSelectedDay"]),
    selectedChuckList: () => store.getters.getSelectedChuckList,
    searchChuckList: () => store.getters.getSearchChuckList,
    chuckMap: () => store.getters.getChuckMap,
  },
  watch: {
    getSelectedDay() {
      store.dispatch("updateSelectedChuckList");
      this.closeSearchBar();
    },
    chuckMap: function() {
            store.dispatch('updateSelectedChuckList')
            this.closeSearchBar()
        },
  },
    methods: {
        ...mapMutations([
            'setSelectedDay',
            'setSelectedDiary',
            'setVisibleCalendar',
            'setVisibleDetail',
            'setVisibleWrite',
            'setSearchChuckList',
        ]),
        detail(item) {
            store.dispatch('updateComments', item)
            this.setSelectedDiary(item)
            this.setVisibleDetail(true)
            this.setVisibleCalendar(false)
            this.setVisibleWrite(false)
        },
        write() {
            eventBus.$emit('init-el')
            this.setVisibleWrite(true)
            this.setVisibleDetail(false)
            this.setVisibleCalendar(false)
        },
        openSearchBar() {
            this.keyword = ''
            this.searchFlag = false
            // 이전 검색 초기화
            this.setSearchChuckList([])
            setTimeout(function(){
                document.getElementById('searchBar').focus()
            }, 1);
        },
        closeSearchBar() {
            this.searchFlag = true
            this.searchResult = ''
        },
        search() {
      this.searchResult = this.keyword;
      this.keyword = "";
      // 검색 구현
      store.dispatch("updateSearchChuckList", this.searchResult);
      // 검색어 발견 위치 색 변경!!!
      this.$nextTick(function () {
        this.$set(this.queries, 0, this.searchResult);
      });
    },
    }
}
</script>

<style scoped>
.belt {
  display: inline-block;
  height: 130px;
  width: 10px;
  margin: 5px;
  border-radius: 5px;
}
</style>