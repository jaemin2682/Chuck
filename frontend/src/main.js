import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/ko'

import VueMoment from 'vue-moment'
import moment from 'moment-timezone'

import { library } from "@fortawesome/fontawesome-svg-core"
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome"

import { fas } from "@fortawesome/free-solid-svg-icons"
import { far } from "@fortawesome/free-regular-svg-icons"

library.add(fas)
library.add(far)

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.use(Vuetify);
Vue.use(ElementUI, {locale});

Vue.use(VueMoment);
Vue.use(moment);

// vue-turnjs install
import { FlippingWidgets } from "vue-turnjs";
import "vue-turnjs/dist/vue-turnjs.esm.css";
Vue.config.productionTip = false
Vue.use(FlippingWidgets);

import "./styles/default.css"
import "./styles/custom.css"

import TextHighlight from "vue-text-highlight";
Vue.component('text-highlight', TextHighlight);
new Vue({
  render: h => h(App),
  vuetify : new Vuetify(),
  router: router,
  store: store,
}).$mount('#app')
