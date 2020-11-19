import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/utils/api'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: sessionStorage.getItem('token'),
        ID: sessionStorage.getItem('ID'),
        NAME: sessionStorage.getItem('NAME'),
        refreshToken: sessionStorage.getItem('refreshToken'),
        selectedGroup: JSON.parse(sessionStorage.getItem('selectedGroup')),
        selectedDay: new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+ (new Date().getDate() < 10 ? `0${new Date().getDate()}` : new Date().getDate()),
        selectedDiary: '',
        visibleCalendar: true,
        visibleDetail: false,
        visibleWrite: false,
        visibleChoice: true,
        visibleAlbum: false,
        visibleVideo: false,
        visiblePreview: false,
        visibleModalAssignGroup: false,
        visibleModalSecedeGroup: false,
        chuckList: [],
        chuckMap: new Map(),
        comments: [],
        selectedChuckList: [],
        searchChuckList:[],
        backState: 1,
        color: ["#FFB6B6", "#FFD9A1", "#FBFFC8", "#C8FFCE", "#C8CDFF", "#C8EBFF", "#C8FFFD", "#C8FFEB", "#F0DDFE", "#FFE1F1"],
        faceDataGallery: [],
        faceDataStudio: [],
        faceDataBook: [],
        faceDataFilm: [],
        personArrayGallery : [],
        personArrayBook: [],
        personArrayFilm: [],
        init: false,
        videoSrc: [],
        videoUrl: '',
        videoMusic: '',
        isModify: false,
        images: [],
        deletedImages: [],
        cloudImages: [],
        change: false,
    },

    getters: {
        getSelectedGroup(state) {
            return state.selectedGroup
        },
        getRefreshToken(state) {
            return state.refreshToken
        },
        getToken(state) {
            return state.token
        },
        getId(state) {
            return state.ID
        },
        getName(state) {
            return state.NAME
        },
        getChuckList(state) {
            return state.chuckList
        },
        getSelectedDay(state) {
            return state.selectedDay
        },
        getSelectedDiary(state) {
            return state.selectedDiary
        },
        getVisibleCalendar(state) {
            return state.visibleCalendar
        },
        getVisibleDetail(state) {
            return state.visibleDetail
        },
        getComments(state) {
            return state.comments
        },
        getVisibleWrite(state) {
            return state.visibleWrite
        },
        getVisibleChoice(state) {
            return state.visibleChoice
        },
        getVisibleAlbum(state) {
            return state.visibleAlbum
        },
        getVisibleVideo(state) {
            return state.visibleVideo
        },
        getVisiblePreview(state) {
            return state.visiblePreview
        },
        getPersonClassificationResult(state){
            for(let i = 0; i < state.personArrayGallery.length; i++){
                if(state.personArrayGallery[i]){
                    return true
                }
            }
            return false
        },
        getPersonArrayGallery(state) {
            return state.personArrayGallery
        },
        getPersonArrayFilm(state) {
            return state.personArrayFilm
        },
        getPersonArrayBook(state) {
            return state.personArrayBook
        },
        getColor(state) {
            return state.color
        },
        getSelectedChuckList(state) {
            return state.selectedChuckList
        },
        getSearchChuckList(state) {
            return state.searchChuckList
        },
        getBackState(state) {
            return state.backState
        },
        getFaceDataGallery(state) {
            return state.faceDataGallery
        },
        getFaceDataStudio(state) {
            return state.faceDataStudio
        },
        getFaceDataBook(state) {
            return state.faceDataBook
        },
        getFaceDataFilm(state) {
            return state.faceDataFilm
        },
        getVisibleModalAssignGroup(state){
            return state.visibleModalAssignGroup
        },
        getVisibleModalSecedeGroup(state){
            return state.visibleModalSecedeGroup
        },
        getInit(state) {
            return state.init
        },
        getChuckMap(state) {
            return state.chuckMap
        },
        getVideoSrc(state) {
            return state.videoSrc
        },
        getVideoUrl(state) {
            return state.videoUrl
        },
        getVideoMusic(state) {
            return state.videoMusic
        },
        getModify(state) {
            return state.isModify
        },
        getImages(state) {
            return state.images
        },
        getDeletedImages(state) {
            return state.deletedImages
        },
        getCloudImages(state) {
            return state.cloudImages
        },
        getChange(state) {
            return state.change
        }
    },
    mutations: {
        setSelectedGroup(state, payload) {
            state.selectedGroup = payload
            sessionStorage.setItem('selectedGroup', JSON.stringify(payload))
        },
        setRefreshToken(state, payload) {
            state.refreshToken = payload
            sessionStorage.setItem('refreshToken', payload)
        },
        setToken(state, payload) {
            state.token = payload
            sessionStorage.setItem('token', payload)
        },
        setId(state, payload) {
            state.ID = payload
            sessionStorage.setItem('ID', payload)
        },
        setName(state, payload) {
            state.NAME = payload
            sessionStorage.setItem('NAME', payload)
        },
        setChuckList(state, payload) {
            state.chuckList = payload
        },
        setSelectedDay(state, payload) {
            state.selectedDay = payload
        },
        setSelectedDiary(state, payload) {
            state.selectedDiary = payload
        },
        setVisibleCalendar(state, payload) {
            state.visibleCalendar = payload
        },
        setVisibleDetail(state, payload) {
            state.visibleDetail = payload
        },
        setComments(state, payload) {
            state.comments = payload
        },
        setVisibleWrite(state, payload) {
            state.visibleWrite = payload
        },
        deleteUser(state) {
            state.NAME = ''
            state.token = ''
            state.ID = ''
            sessionStorage.clear
        },
        setVisibleChoice(state, payload) {
            state.visibleChoice = payload
        },
        setVisibleAlbum(state, payload) {
            state.visibleAlbum = payload
        },
        setVisibleVideo(state, payload) {
            state.visibleVideo = payload
        },
        setVisiblePreview(state, payload) {
            state.visiblePreview = payload
        },
        setPersonArrayGallery(state, payload){
            state.personArrayGallery = payload
        },
        setPersonArrayFilm(state, payload){
            state.personArrayFilm = payload
        },
        setPersonArrayBook(state, payload){
            state.personArrayBook = payload
        },
        setSelectedChuckList(state, payload) {
            state.selectedChuckList = payload
        },
        setSearchChuckList(state, payload){
            state.searchChuckList = payload
        },
        setBackState(state, payload) {
            state.backState = payload
        },
        setFaceDataGallery(state, payload) {
            state.faceDataGallery = payload
        },
        setFaceDataStudio(state, payload) {
            state.faceDataStudio = payload
        },
        setFaceDataBook(state, payload) {
            state.faceDataBook = payload
        },
        setFaceDataFilm(state, payload) {
            state.faceDataFilm = payload
        },
        setVisibleModalAssignGroup(state, payload){
            state.visibleModalAssignGroup = payload
        },
        setVisibleModalSecedeGroup(state, payload){
            state.visibleModalSecedeGroup = payload
        },
        insertComments(state, payload) {
            state.comments.push(payload)
        },
        removeComments(state, payload) {
            const num = state.comments.length
            for(let i=0; i<num; i++) {
                if(state.comments[i].id == payload.id) {
                    state.comments.splice(i, 1)
                    break;
                }
            }
        },
        setInit(state, payload) {
            state.init = payload
        },
        insertChucks(state, payload) {
            state.chuckMap = new Map(state.chuckMap.set(payload.id, payload))
        },
        removeChucks(state, payload) {
            state.chuckMap.delete(payload.id)
            state.chuckMap = new Map(state.chuckMap)
        },
        setVideoSrc(state, payload) {
            state.videoSrc = payload
        },
        setVideoUrl(state, payload) {
            state.videoUrl = payload
        },
        setVideoMusic(state, payload) {
            state.videoMusic = payload
        },
        setModify(state, payload) {
            state.isModify = payload
        },
        setChuckMap(state, payload) {
            state.chuckMap = new Map(state.chuckMap.set(payload.id, payload))
        },
        setCloudImages(state, payload) {
            state.cloudImages = payload
        }
    },
    actions: {
        updateSelectedGroup({commit}, items) {
            commit('setSelectedGroup', items)
        },
        updateRefreshToken({commit}, item) {
            commit('setRefreshToken', item)
        },
        updateToken({commit}, item) {
            commit('setToken', item)
        },
        updateId({commit}, item) {
            commit('setId', item)
        },
        updateName({commit}, item) {
            commit('setName', item)
        },
        updateChuckList({commit}) {
            api.get(`diaries/group/${this.state.selectedGroup.id}`, {
                headers: {
                    token: sessionStorage.getItem('token')
                },
            }).then(({ data }) => {
                for(var i=0; i<data.length; i++) {
                    const image = data[i].image.split(';')
                    data[i].image = image
                    data[i].color = this.state.color[i % 10]
                    data[i].index = i          
                }
                commit('setChuckList', data)
            })
        },
        logout({commit}) {
            api.post(`users/logout`, {
                refreshToken: sessionStorage.getItem('refreshToken')
            }, {
                headers: {
                    token: sessionStorage.getItem('token')
                },
            }).then(() => {
                commit('deleteUser')
                sessionStorage.clear()
            })
        },
        updateSelectedChuckList({commit}) {
            const day = [];
            const chuckList = Array.from(this.state.chuckMap.values()).slice().reverse()
            for(var i=0; i<chuckList.length; i++) {
                if(chuckList[i].date === this.state.selectedDay) {
                    day.push(chuckList[i])
                }
            }
            commit('setSelectedChuckList', day)
        },
        updateSearchChuckList({commit}, item){
            const result = []
            const chuckList = Array.from(this.state.chuckMap.values()).slice().reverse()
            for (let index = 0; index < chuckList.length; index++) {
                const element = chuckList[index];
                // console.log(element.title.indexOf(item)!==-1)
                if(element.title.indexOf(item)!==-1 || element.content.indexOf(item)!==-1){
                    result.push(element)
                }
            }
            commit('setSearchChuckList', result)
        },
        updateBackState({commit}, item) {
            commit('setBackState', item)
        },
        updateComments({commit}, item) {
            api.get(`replies/searchByDiary?diaryId=${item}`).then(({data}) => {
                commit('setComments', data)
            })
        },
        addComments({commit}, item) {
            commit('insertComments', item)
        },
        delComments({commit}, item) {
            commit('removeComments', item)
        },
        updateInit({commit}) {
            commit('setInit', true)
        },
        addChuckList({commit}, item) {
            commit('insertChucks', item)
        },
        delChuckList({commit}, item) {
            commit('removeChucks', item)
        },
        updateModify({commit}, item) {
            commit('setModify', item)
        },
    }
})