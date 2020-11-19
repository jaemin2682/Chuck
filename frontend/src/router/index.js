import Vue from 'vue'
import Router from 'vue-router'
import landing from '@/pages/landing.vue'
import login from '@/pages/login.vue'
import group from '@/pages/group.vue'
import diary from '@/pages/diary.vue'
import fetchData from '@/pages/fetchData.vue'

Vue.use(Router)

const checkAuth = () => (to, from, next) => {
    if('ID' in sessionStorage) {
        return next(); 
    } else {
        return next('/login');
    }
}

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'landing',
            component: landing,
        },
        {
            path: '/login',
            name: 'login',
            component: login,
            props: (route) => ({redirect: route.query.redirect}),
        },
        
        {
            path: '/group',
            name: 'group',
            component: group,
            props: true,
            beforeEnter: checkAuth(),
        },
        {
            path: '/fetch',
            name: 'fetch',
            component: fetchData,
            beforeEnter: checkAuth(),
        },
        {
            path: '/diary',
            name: 'diary',
            component: diary,
            props: true,
            beforeEnter: checkAuth(),
        },
        
    ]
})