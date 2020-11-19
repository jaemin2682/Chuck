<template>
  <transition name="modal" appear>
    <div class="modal modal-overlay" @click.self="close">
      <div class="modal-window">
        <header class="modal-header">그룹장양도</header>
        <div class="modal-content">
          <div v-for="(member, index) in memberList" :key="index">
            <v-btn
              class="justify-start"
              @click="check(member.userId, member.name)"
              color="#8D6262"
              text
              >{{ member.name }}</v-btn
            >
          </div>
        </div>
        <footer class="modal-footer">
          <button class="assginButton" @click="assgin">양도하기</button>
          <button class="closeButton" @click="close">취소하기</button>
        </footer>
      </div>
    </div>
  </transition>
</template>

<script>
import { mapMutations } from "vuex";
import api from "@/utils/api";
import store from "@/store";

export default {
  data() {
    return {
      memberList: [],
      selectedMember: "",
      selectedMemberName: "",
      memberClicked: [],
    };
  },
  methods: {
    ...mapMutations(["setVisibleModalAssignGroup"]),
    close() {
      console.log("닫기");
      this.setVisibleModalAssignGroup(false);
    },
    assgin() {
      // 양도하기
      api
        .put(
          `groups/changes/${this.selectedMember}/`,
          {
            id: this.groupInfo.id,
          },
          {
            headers: { token: store.getters.getToken },
          }
        )
        .then((res) => {
            // 그룹정보 갱신
          this.groupInfo.userId = this.selectedMember
          this.groupInfo.ownerName = this.selectedMemberName
          store.dispatch('updateSelectedGroup', this.groupInfo);
          // 모달창 닫기
          this.close();
          // 페이지 새로고침
          this.$router.go()
        })
    },
    check(id, name) {
      this.selectedMember = id;
      this.selectedMemberName = name;
    },
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
        for (let index = 0; index < this.memberList.length; index++) {
          this.memberClicked.push(false);
        }
      });
  },
  computed: {
    groupInfo: () => store.getters.getSelectedGroup,
  },
};
</script>

<style lang="scss" scoped>
.modal {
  &.modal-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }

  &-window {
    background: #fff;
    border-radius: 4px;
    width: 300px;
    height: 400px;
    overflow: hidden;
  }

  &-content {
    margin: 10px 10px;
    padding-top: 10px;
    height: 250px;
    border: solid #c4c4c4 0.1rem;
    justify-content: left;
    overflow: scroll;
    button {
      width: 100%;
    }
    .clicked {
      background-color: #e8e8e8;
    }
  }

  &-footer {
    background: #fff;
    margin-top: 20px;
    button {
      width: 135px;
      height: 40px;
      font-size: 15px;
      border-radius: 5px;
      margin: 5px;
    }
    .assginButton {
      background-color: #8d6262;
      color: #fff;
    }
    .closeButton {
      border: #8d6262 solid 0.1rem;
    }
  }

  &-header {
    margin: 20px;
    font-size: 20px;
  }
}

// 오버레이 트랜지션
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.4s;

  // 오버레이에 포함되어 있는 모달 윈도의 트랜지션
  .modal-window {
    transition: opacity 0.4s, transform 0.4s;
  }
}

// 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐
.modal-leave-active {
  transition: opacity 0.6s ease 0.4s;
}

.modal-enter,
.modal-leave-to {
  opacity: 0;

  .modal-window {
    opacity: 0;
    transform: translateY(-20px);
  }
}
</style>