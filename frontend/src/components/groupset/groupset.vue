<!-- 
    그룹장 양도
    - 우하단에 버튼
        - 그룹장일때만 show
        - 모달창을 통해 그룹원 한명 선택
        - 확인 버튼 이후 그룹관리 reload
    - 그룹장이 그룹탈퇴시
        - 그룹장 양도 모달창 띄워줌
        - 확인 이후 그룹탈퇴
    
    그룹원 목록
    - 그룹장은 추방버튼 없음

    그룹장 : store.getters.getSelectedGroup.user_id
-->
<template>
  <div class="groupset">
    <div>
      <img src="../../assets/title/management_tabtitle.svg" class="tabtitle" />
      <div class="underline"></div>
    </div>
    <!-- 그룹명 -->
    <font size="5">그룹명</font>
    <v-btn
      @click="focusGroupname"
      color="#2680EB"
      text
      v-show="!changeGroupnameClicked"
      >그룹명바꾸기</v-btn
    >
    <v-btn
      @click="changeGroupname"
      color="#2680EB"
      text
      v-show="changeGroupnameClicked"
      >수정완료</v-btn
    >
    <div class="information">
      <input
        ref="groupName"
        v-model="groupInfo.name"
        class="groupName"
        maxlength="10"
        :disabled="!changeGroupnameClicked"
      />
    </div>
    <!-- 그룹원 -->
    <font size="5">
      그룹원 목록
      <v-btn @click="InviteGroup" color="#3D91FF" text>그룹원 초대하기</v-btn>
    </font>
    <div class="list">
      <div v-for="(member, index) in memberList" :key="index">
        {{ member.name }}
        <v-btn
          @click="DeportGroup(member.userId)"
          color="#FFB6B6"
          text
          v-show="flag(member.owner)"
          >추방</v-btn
        >
      </div>
    </div>
    <!-- 그룹생성일 -->
    <font size="5">그룹 생성일</font>
    <div class="createDate">
      <p>{{ this.groupInfo.publishedDate | moment("YYYY.MM.DD") }}</p>
    </div>
    <!-- 그룹 로그 -->
    <font size="5">그룹 로그</font>
    <div class="log" style="margin-bottom:0px;">
      <div v-for="(log, index) in logList" :key="index">
        {{ log.content }}
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/utils/api";
import store from "@/store";

export default {
  data() {
    return {
      memberList: [],
      logList: [],
      changeGroupnameClicked: false,
    };
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

    api.get(`logs/${this.groupInfo.id}`).then((res) => {
      this.logList = res.data;
    });
  },
  mounted() {
    if (!window.Kakao && sessionStorage.getItem("ID") !== "1") {
      const script = document.createElement("script");
      script.onload = () =>
        window.Kakao.init("d1baf2cad3354a9138989baea6e65995");
      script.src = "/js/kakao.min.js";
      document.head.appendChild(script);
    }
  },
  methods: {
    InviteGroup() {
      if (sessionStorage.getItem("ID") === "1") {
        this.$notify({
          title: '테스트 계정은 일부 기능이 제한됩니다.',
          dangerouslyUseHTMLString: true,
          duration: 3000
        });
      } else {
        window.Kakao.Link.sendCustom({
          templateId: 40694,
          templateArgs: {
            key: this.groupInfo.token,
            group: this.groupInfo.name, // this.groupName,
            user: store.getters.getName,
          },
        });
      }
    },
    DeportGroup(id) {
      // axios 멤버 추방하기
      api
        .delete(`groups/${this.groupInfo.id}/` + id, {
          headers: {
            token: sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.update();
        });
    },
    update() {
      // 멤버리스트 , 회원 갱신
      api
        .get(`groups/${this.groupInfo.id}/members`, {
          headers: {
            token: sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.memberList = res.data;
        });

      api.get(`logs/${this.groupInfo.id}`).then((res) => {
        this.logList = res.data;
      });
    },
    flag(ownerflag) {
      //   console.log("로그인 id : " +sessionStorage.getItem('ID'))
      //   console.log("그룹장 id : " +this.groupInfo.userId)
      //   console.log("멤버 오너임? : " + ownerflag)
      //   console.log(sessionStorage.getItem('ID')==this.groupInfo.userId)
      return (
        sessionStorage.getItem("ID") == this.groupInfo.userId && !ownerflag
      );
    },
    focusGroupname(){
      this.changeGroupnameClicked = true;
      this.$nextTick(() => {
        this.$refs.groupName.focus();
      });
    },
    changeGroupname(){
      // 그룹명 변경
      // 403권한 변경되면 가능할것임!!
      api
        .put(
          `/groups/${this.groupInfo.id}`,
          {
            id: this.groupInfo.id,
            name: this.groupInfo.name,
            intro: this.groupInfo.intro,
          },
          {
            headers: { token: store.getters.getToken },
          }
        )
        .then((res) => {
          // db에는 변경이 되었을 것이고
          // session의 그룹정보에 변경을 해주어야함.
          store.dispatch('updateSelectedGroup', this.groupInfo);
          this.changeGroupnameClicked = false;
        })
        .catch((err) => {
          console.log(err);
          // store.dispatch('updateSelectedGroup', this.groupInfo);
          // this.changeGroupnameClicked = false;
        });
    },
  },
  computed: {
    groupInfo: () => store.getters.getSelectedGroup,
  },
};
</script>

<style scope>
.groupset {
  margin: 30px 30px 0px 30px;
  text-align: left;
}
.groupset > div {
  margin-top:5px;
  margin-bottom: 24px;
  color: #2d2d2d;
}
.information button {
  left: 300px;
}
.list {
  height: 140px;
  overflow: scroll;
}
.list div {
  line-height: 24px;
  vertical-align: middle;
}
.log {
  height: 160px;
  overflow: scroll;
}
.createDate font {
  margin-bottom: 10px;
}
.groupName{
  position: relative;
  font-size: 16px; 
  width: 200px; 
  color: #2d2d2d;
  padding-left: 5px;
  left: -5px;
  height: 30px;
}
.groupName:focus {
  outline: none;
  border:solid 1px #e0e0e0;
  border-radius: 5px;
}
</style>