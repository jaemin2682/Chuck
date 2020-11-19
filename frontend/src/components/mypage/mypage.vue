<!--
 애니메이션 잔상 처리
 -->
<template>
  <div
    class="floating-button-menu"
    :class="{
      'menu-off': !clicked,
      'menu-on': clicked,
      withBack: !this.getVisibleCalendar,
    }"
  >
    <div v-show="clicked" class="floating-button-menu-links">
      <div class="hole"></div>
      <h2>마이페이지</h2>
      <img src="@/assets/Temp_Img_g.png" alt="" />
      <div style="text-align: left; margin: 20px 20px 20px 20px">
        닉네임
        <v-btn
          @click="focusNickname"
          color="#3D91FF"
          text
          v-show="!changeNicknameClicked"
          >바꾸기</v-btn
        >
        <v-btn
          @click="changeNickname"
          color="#3D91FF"
          text
          v-show="changeNicknameClicked"
          >수정완료</v-btn
        >
        <input
          v-model="username"
          ref="username"
          style="font-size: 24px; width: 240px"
          maxlength="10"
          class="nickname"
          :disabled="!changeNicknameClicked"
        />
      </div>
      <div style="text-align: left; margin: 20px 20px 120px 20px">
        <router-link to="/group">그룹선택 화면으로</router-link>
        <button id="logout" @click="logout">로그아웃</button>
      </div>
      <button id="closeBtn" @click="close"><h1>x</h1></button>
    </div>
    <div @click="open" class="floating-button-menu-label">
      <img src="@/assets/setting_floatingbtn.svg" alt="" />
    </div>
  </div>
</template>

<script>
import api from "@/utils/api";
import store from "@/store";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      username: sessionStorage.getItem("NAME"),
      clicked: false,
      changeNicknameClicked: false,
    };
  },
  mounted() {},
  computed: {
    groupInfo: () => store.getters.getSelectedGroup,
    ...mapGetters(["getVisibleCalendar"]),
  },
  methods: {
    focusNickname() {
      this.changeNicknameClicked = true;
      this.$nextTick(() => {
        this.$refs.username.focus();
      });
    },
    changeNickname() {
      api
        .put(
          `/users/${store.getters.getId}`,
          {
            id: store.getters.getId,
            name: this.username,
          },
          {
            headers: { token: store.getters.getToken },
          }
        )
        .then((res) => {
          store.dispatch("updateName", this.username);
          this.changeNicknameClicked = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    close() {
      this.clicked = false;
    },
    open() {
      this.clicked = true;
    },
    logout() {
      this.$store.dispatch("logout");
      this.$router.push("/");
    },
  },
};
</script>

<style lang="scss" scoped>
.floating-button-menu {
  z-index: 35;
  position: relative;
  bottom: -40px;
  left: 1550px;
  cursor: pointer;
  border-radius: 50%;
  min-width: 70px;
  max-width: 0px;
  min-height: 70px;
  max-height: 0px;
  box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.3);
  transition: all ease 0.4s;
  .floating-button-menu-links {
    width: 0;
    height: 0;
    overflow: hidden;
    opacity: 0;
    // 메뉴 사라질때
    transition: all 0.3s;
    cursor: auto;
    a {
      position: relative;
      color: #454545;
      text-transform: uppercase;
      text-decoration: none;
      line-height: 49px;
      display: block;
      display: block;
      width: 100%;
      height: 50px;
      transition: background ease-in-out 0.3s;
      background: rgba(0, 0, 0, 0);
      &:last-child {
        border-bottom: 0px solid #fff;
      }
    }
    // &.menu-on {
    //   background: #fff;
    //   width: 100px;
    //   height: 800px;
    //   border-radius: 10px;
    //   opacity: 1;
    //   transition: all ease-in-out 0.5s;
    // }
    #closeBtn {
      width: 30px;
      height: 30px;
      outline: none;
      position: absolute;
      left: 200px;
      bottom: 35px;
    }
    #logout {
      width: 240px;
      height: 50px;
      outline: none;
      text-align: left;
      color: #d0d0d0;
    }
    h2 {
      margin-top: 78px;
      position: relative;
    }
    img {
      margin-top: 24px;
      width: 250px;
      height: 160px;
    }
    .hole {
      background-color: #e0e0e0;
      border-radius: 50px;
      width: 30px;
      height: 30px;
      position: absolute;
      left: 115px;
      top: 10px;
    }
    .nickname:focus{
      outline: none;
      border:solid 1px #e0e0e0;
      border-radius: 5px;
    }
  }

  .floating-button-menu-label {
    text-align: center;
    line-height: 74px;
    font-size: 50px;
    opacity: 1;
    transition: opacity 0.3s;
    img {
      width: 50%;
      height: 50%;
    }
  }

  &.menu-on {
    background: #fff;
    max-width: 250px;
    max-height: 600px;
    border-radius: 10px;
    left: 1370px;
    bottom: 500px;
    .floating-button-menu-links {
      width: 100%;
      height: 100%;
      opacity: 1;
    }
    .floating-button-menu-label {
      height: 0px;
      overflow: hidden;
    }
    &.withBack {
      left: 1350px;
      bottom: 600px;
    }
  }
  &.withBack {
    left: 1550px;
    bottom: 50px;
  }
}
</style>