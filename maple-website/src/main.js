import { createApp } from "vue";
import { createPinia } from "pinia";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from "./App.vue";
import router from "./router";
import  "./assets/icon/iconfont.css";
import "./assets/icon/iconfont.js";
import MyIcon from '@/components/MyIcon.vue';
import { createMetaManager} from 'vue-meta'
import materialKit from "./material-kit";

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(materialKit);
app.use(ElementPlus);
app.use(createMetaManager(false, {
        meta: { tag: 'meta', nameless: true }
    }))
app.component('MyIcon', MyIcon);
app.mount("#app");
