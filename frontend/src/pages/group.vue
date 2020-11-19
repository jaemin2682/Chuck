<template>
    <div class="group-select">
        <div class="global-wrapper">
            <img src="../assets/logo.svg" style="width:200px; margin:20px 0px;"><br>
            <font size=5>그룹을 선택해 주세요</font>
            <div class="floor-wrapper">
                <div class="floor">
                    <div class="book-list">       
                        <div v-for="(book, i) in books" :key="i" class="book-item" @click="selectBook(book, i)">
                            <img class="cover" :src="require(`../assets/books/book${(i % 10)}.png`)"/>
                            <div class="bookTitle">{{ book.name }}</div>
                            <div class="bookIntro">{{ book.intro.slice(0, 10) }}</div>
                            <div class="back"></div>
                        </div>
                        <div v-if="books.length < 12" class="book-item" @click="addGroup()">
                            <img class="cover" :src="require(`../assets/books/blink_book.png`)"/>
                            <div class="back"></div>
                        </div>
                    </div>
                </div>
            </div>

            <template v-if="selected" v-on:keyup.esc="selected=false">
                <div class="menu-overlay" @click="closeMenu"></div>
            </template>

            <transition name="menu" tag="div">
                <template v-if="selected">
                    <div class="side-menu" :style="bg[index]">
                        <div  v-if="this.bookgen === false" style="position:absolute; bottom:100px; width:300px; left:50%; transform:translate(-168px);">
                            <img class="logo" src="../assets/logo.svg">
                            <div class="group-title">{{ selectedBook.name }}</div>
                            <div class="group-intro">{{ selectedBook.intro }}</div>
                            <div class="picker" @click="selectGroup()">그룹 선택</div>
                        </div>
                        <div v-else style="position:absolute; bottom:80px; width:300px; left:50%; transform:translate(-168px);">
                            <img class="logo" src="../assets/logo.svg">
                            <input class="group-name" v-model="createBook.name" placeholder="그룹명을 입력해주세요" style="outline:none;" />
                            <hr size="0.5" color="#E0E0E0" width="70%">
                            <textarea class="description" rows=3 v-model="createBook.intro" placeholder="그룹 소개를 입력해주세요" style="resize:none; outline:none;" />
                            <div class="create" @click="uploadGroup">그룹 만들기</div>
                        </div>
                    </div>
                </template>
            </transition>
        </div>
    </div>
</template>

<script>
import api from '@/utils/api';
import axios from 'axios';
import store from '@/store';

export default {
    data() {
        return {
            books: [],
            selected: false,
            bookgen: false,
            selectedBook: {
                name: '',
                intro: '',
            },
            createBook: {
                name: '',
                intro: '',
            },
            index: '0',
            bg: [
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_0.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_1.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_2.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_3.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_4.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_5.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_6.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_7.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_8.png')})`
                },
                {
                    backgroundImage: `url(${require('../assets/select_group/select_group_9.png')})`
                },
                
            ],
        };
    },
    mounted() {
        api.get("groups/of/groups", {
            headers: {
                token: sessionStorage.getItem('token')
            }
        })
        .then((res) => {
            this.books = this.books.concat(res.data);
        });
    },

    methods: {
        selectGroup() {
            store.dispatch('updateSelectedGroup', this.selectedBook);
            this.$router.push('/fetch');
        },
        uploadGroup() {
            if(this.createBook.name.length === 0 || this.createBook.intro.length === 0) {
                alert("그룹명과 그룹소개를 입력해주세요.")
            }
            else if(this.createBook.name.length > 10) {
                alert("그룹명은 10자 이내로 작성해주세요.")
            }
            else {
                api.post(`groups`, {
                        name: this.createBook.name,
                        intro: this.createBook.intro,
                        userId: sessionStorage.getItem('id')
                    }, {
                        headers: {
                            token: sessionStorage.getItem('token')
                        }
                }).then((res) => {
                    this.books.splice(this.books.length - 1, 0, res.data);
                    this.bookgen = false;
                    this.selected = false;
                    this.createBook = {};
                })
            }
        },
        selectBook(book, index) {
            this.index = index;
            this.bookgen = false;
            this.selected = true;
            this.selectedBook = book;
        },
        closeMenu() {
            this.selected = false;
        },
        addGroup() {
            this.createBook.name = '';
            this.createBook.intro = '';
            this.bookgen = true;
            this.selected = true;
        }
    }
}
</script>

<style lang="scss" scoped>
.group-select {
    overflow: hidden;
}
.global-wrapper {
    height: 100vh;
}
.menu-enter-active,
.menu-leave-active {
    transition: all 0.5s cubic-bezier(0.15, 0.6, 0.52, 1);
}
.menu-enter, .menu-leave-to {
    transform: translateY(100%);
    opacity: 0;
}

.floor-wrapper {
    perspective: 500px;
    > .floor {
        transform-style: preserve-3d;
        transform: rotateX(20deg) rotateZ(-0deg) translateY(-50%);
        position: relative;
        top: 20vh;
        width: 880px;
        margin: 0 auto;
    }
}

.book-list {
    display: flex;
    flex-wrap: wrap;

    .bookTitle {
        position: absolute;
        bottom: 180px;
        margin: 0 auto; 
        width: 200px;
        font-size: 24px;
    }
    .bookIntro {
        position: absolute;
        bottom: 100px;
        margin: 0 auto;
        width: 200px;
        font-size: 12px;
    }
}

.book-item {
    width: 200px;
    margin: 0px 10px 30px 10px;
    transform: translateZ(2px);
    cursor: pointer;
    transform-style: preserve-3d;
    position: relative;

    &::before,
    &::after {
        content: "";
        display: block;
        position: absolute;
    }

    &::before {
        background-color: #c0c0c0;
        height: 100%;
        width: 10px;
        top: 0;
        left: 0;
        transform-origin: left;
        transform: rotateY(90deg);
    }

    &::after {
        background: linear-gradient(to bottom, #fff, #dadada);
        height: 10px;
        width: 100%;
        top: 100%;
        transform-origin: top;
        transform: rotateX(-180deg);
    }

    &:hover {
        > .cover {
            animation: blink .5s linear alternate infinite;
            box-shadow: 0px 0px 10px #8D6262;
        }
    }

    > .cover {
        display: block;
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: filter .2s;
    }

    > .back {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        transform: translateZ(-10px);
        box-shadow: 0 0 10px rgba(black, 0.2);
    }
  
  @keyframes blink {
        from {
            filter: brightness(1);
        }
        
        to {
            filter: brightness(1.2);
        }
    }
}
.side-menu {
    width: 736px;
    height: 584px;
    overflow-y: scroll;
    position: absolute;
    bottom:0px;
    left: 50%;
    transform: translate(-368px);
    margin: 0 auto;
    background-size: cover;

    .group-title {
        font-size: 20px;
        position: relative;
        margin: 0 auto;
        margin-bottom: 20px;
        width: 200px;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    
    .group-intro {
        height: 100px;
        width: 200px;
        line-height: 1.7;
        font-size: 16px;
        position: relative;
        margin: 0 auto;
        margin-bottom: 40px;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .group-name {
        line-height: 1.7;
        font-size: 20px;
        position: relative;
        width: 200px;
        margin: 0 auto;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .description {
        line-height: 1.7;
        font-size: 16px;
        position: relative;
        width: 200px;
        margin: 0 auto;
        margin-top: 40px;
        margin-bottom: 40px;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    .picker {
        cursor: pointer;
        position: relative;
        color: #8D6262;
        line-height: 1.7;
        font-size: 20px;
        margin: 0 auto;
        margin-bottom: 50px;
    }

    .logo {
        display: block;
        height: 100px;
        margin: 0 auto;
        margin-bottom: 40px;
    }

    .create {
        cursor: pointer;
        position: relative;
        color: #8D6262;
        line-height: 1.7;
        font-size: 20px;
        margin-bottom: 50px;
    }
}

.menu-overlay {
    width: 100vw;
    height: 100vh;
    background-color: rgba(black, 0.4);
    position: absolute;
    top: 0;
    left: 0;
}
</style>>