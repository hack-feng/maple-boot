import { createApp } from 'vue';
import pinia from '/@/stores/index';
import App from '/@/App.vue';
import router from '/@/router';
import { directive } from '/@/directive/index';
import { i18n } from '/@/i18n/index';
import other from '/@/utils/other';

import ElementPlus from 'element-plus';

import '/@/theme/index.scss';
import VueGridLayout from 'vue-grid-layout';

import { parseDict } from '/@/utils/dict'

const app = createApp(App);
app.config.warnHandler = () => null
directive(app);
other.elSvg(app);
// 全局方法挂载
app.config.globalProperties.parseDict = parseDict
app.use(pinia).use(router).use(ElementPlus).use(i18n).use(VueGridLayout).mount('#app');
