<!--
    그룹장 양도
    - 우하단에 버튼
        - 그룹장일때만 show (해결)
        - 모달창을 통해 그룹원 한명 선택
        - 확인 버튼 이후 그룹관리 reload
    - 그룹장이 그룹탈퇴시
        - 그룹장 양도 모달창 띄워줌
        - 확인 이후 그룹탈퇴
 -->
<template>
  <div class="outro">
    <div class="left"></div>
    <div class="right">
      <div class="text-right" style="padding: 30px 30px 0px 0px">
        <img src="../../assets/logo.svg" class="subtitle" />
      </div>
      <!-- 오른쪽 버튼 그룹 -->
      <div class="BtnGroup" v-show="isAlone()">
        <v-btn
          class="btn"
          @click="DeleteGroup"
          color="#8D6262"
          text
          x-large
          >그룹 삭제</v-btn
        >
      </div>
      <div class="BtnGroup" v-show="!isAlone()">
        <v-btn
          class="btn"
          @click="AssignGroup"
          color="#8D6262"
          text
          x-large
          v-show="flag()"
          >그룹장 양도</v-btn
        >
        <v-btn
          class="btn"
          @click="SecedeGroupOwner"
          color="#8D6262"
          text
          x-large
          v-show="flag()"
          >그룹 탈퇴</v-btn
        >
        <v-btn
          class="btn"
          @click="SecedeGroup"
          color="#8D6262"
          text
          x-large
          v-show="!flag()"
          >그룹 탈퇴</v-btn
        >
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/utils/api";
import store from "@/store";
import { mapGetters, mapMutations } from "vuex";
export default {
  data() {
    return {
      memberList: [],
    };
  },
  computed: {
    ...mapGetters(["getId"]),
    groupInfo: () => store.getters.getSelectedGroup,
  },
  created() {
    api
      .get(`groups/${this.groupInfo.id}/members`, {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      })
      .then((res) => {
        this.memberList = res.data;
      });
  },
  methods: {
    ...mapMutations([
      "setVisibleModalAssignGroup",
      "setVisibleModalSecedeGroup",
    ]),
    AssignGroup() {
      this.setVisibleModalAssignGroup(true);
    },
    SecedeGroupOwner() {
      this.setVisibleModalSecedeGroup(true);
    },
    DeleteGroup() {
      // 그룹 삭제하기
      api
        .delete(`groups/${this.groupInfo.id}`, {
          headers: {
            token: sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
            this.$router.push("/group");
        });
    },
    SecedeGroup() {
      // 그룹 멤버 바로 탈퇴하기
      api
        .delete(`groups/${this.groupInfo.id}/` + this.getId, {
          headers: {
            token: sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
        //   console.log(res);
          this.$router.push("/group");
        });
    },
    flag() {
        // 그룹장이면 true
        // console.log("그룹장 id : " + this.groupInfo.userId)
        // console.log("회원 id : " + this.getId)
      return this.groupInfo.userId == this.getId;
    },
    isAlone() {
      return this.memberList.length == 1;
    },
  },
};
</script>

<style scoped>
.left {
  width: 326px;
  height: 767px;
  float: left;
}
.right {
  width: 300px;
  height: 767px;
  float: left;
  background-color: #fff8f8;
  position: relative;
  box-shadow: 2px 0px 10px 5px #e2e2e2;
}
.right .BtnGroup {
  text-align: left;
  position: absolute;
  bottom: 15px;
  left: 5px;
  margin-bottom: 10px;
}
.v-btn.v-size--x-large {
  font-size: 1.7rem;
}
</style>