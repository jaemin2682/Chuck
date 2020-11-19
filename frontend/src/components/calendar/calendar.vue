<template>
    <div class="fill-height" style="padding:20px 30px 0px 0px;">
        <v-col>
            <v-sheet height="90">
                <v-toolbar flat>
                    <span @click="setToday" style="cursor:pointer;">
                        <div class="text-left">
                            <img src="../../assets/title/calendar_tabtitle.svg" class="tabtitle">
                        <div class="underline"></div>
                        </div>
                    </span>
                    <v-spacer></v-spacer>
                    <v-btn fab text small color="grey darken-2" @click="prev">
                        <font-awesome-icon :icon="faAngleLeft" />
                    </v-btn>
                    <v-toolbar-title v-if="$refs.calendar">
                        {{ calendarTitle }}
                    </v-toolbar-title>
                    <v-btn fab text small color="grey darken-2" @click="next">
                        <font-awesome-icon :icon="faAngleRight" />
                    </v-btn>
                </v-toolbar>
            </v-sheet>
            <v-sheet height="620">
                <v-calendar ref="calendar"
                v-model="focus"
                color="primary"
                locale="ko"
                :events="events"
                :event-color="getEventColor"
                :type="type"
                @click:event="showEvent"
                @click:more="viewDay"
                @click:date="viewDay"
                @change="updateRange"
                ></v-calendar>
            </v-sheet>
        </v-col>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mapMutations } from 'vuex'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons'
import { faAngleRight } from '@fortawesome/free-solid-svg-icons'
import { faUser } from '@fortawesome/free-regular-svg-icons'
import { faHeart } from '@fortawesome/free-regular-svg-icons'
import { faShoppingBag } from '@fortawesome/free-solid-svg-icons'
import eventBus from '@/utils/EventBus'
import store from '@/store'

export default {
    data: () => ({
        faAngleLeft,
        faAngleRight,
        focus: '',
        type: 'month',
        events: [],
    }),
    mounted () {
        this.$refs.calendar.checkChange()
    },
    computed: {
        ...mapGetters([
            'getSelectedDiary',
            'getVisibleCalendar',
        ]),
        chuckList: () => Array.from(store.getters.getChuckMap.values()).slice().reverse(),
        calendarTitle: function() {
            if(this.$refs.calendar.title.length == 7){
                return this.$refs.calendar.title.substring(3,7)+"년 "+this.$refs.calendar.title.substring(0,2)
            }
            else{
                return this.$refs.calendar.title.substring(4,8)+"년 "+this.$refs.calendar.title.substring(0,3)
            }
        }
    },
    watch: {
        chuckList: function() {
            this.updateRange()
        },
    },
    created() {
        eventBus.$on('updateCalendar', () => {
            this.updateRange();
        });
    },
    methods: {
        ...mapMutations([
            'setSelectedDay',
            'setSelectedDiary',
            'setVisibleCalendar',
            'setVisibleDetail',
            'setVisibleWrite',
        ]),
        viewDay ({ date }) {
            this.setSelectedDay(date)
            this.focus = date
        },
        getEventColor (event) {
            return event.color
        },
        setToday () {
            this.focus = ''
        },
        prev () {
            this.$refs.calendar.prev()
        },
        next () {
            this.$refs.calendar.next()
        },
        showEvent ({ nativeEvent, event, date }) {
            this.setSelectedDay(this.$moment(event.start).format('YYYY-MM-DD'))
            this.focus = date
            store.dispatch('updateComments', event.id)     
            this.setSelectedDiary(event.id)
            this.setVisibleDetail(true)
            this.setVisibleCalendar(false)
            this.setVisibleWrite(false)
        },
        updateRange () {
            const events = []
            const color = this.$store.getters.getColor

            for(var i=0; i<this.chuckList.length; i++){
                var date = this.chuckList[i].date.split('-')
                events.push({
                    index: this.chuckList[i].index,
                    id: this.chuckList[i].id,
                    name: this.chuckList[i].title,
                    start: new Date(date[0], date[1]-1, date[2]),  
                    color: this.chuckList[i].color,
                })
            }
            this.events = events
        },
    },
}
</script>
<style>
</style>