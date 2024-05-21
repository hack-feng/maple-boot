<template>
  <div class="system-user-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="70%">
      <el-tabs v-model="state.dialog.activeName">
        <el-tab-pane
            v-for="(value, key) in state.previews"
            :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
            :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
            :key="key"
        >
          <el-link @click="copyText(value)" :underline="false" icon="el-icon-document-copy" style="float:right">复制
          </el-link>
          <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="viewDialog">
import { reactive } from 'vue';
import { useGenerateApi } from '/@/api/tool/generate';
import { ElMessage } from 'element-plus';
import commonFunction from '/@/utils/commonFunction';

import hljs from "highlight.js";
import "highlight.js/styles/vs2015.css";
import javaLanguage from "highlight.js/lib/languages/java";
import xmlLanguage from "highlight.js/lib/languages/xml";
import javascriptLanguage from "highlight.js/lib/languages/javascript";
import sqlLanguage from "highlight.js/lib/languages/sql";

hljs.registerLanguage("java", javaLanguage);
hljs.registerLanguage("xml", xmlLanguage);
hljs.registerLanguage("html", xmlLanguage);
hljs.registerLanguage("vue", xmlLanguage);
hljs.registerLanguage("typescript", javascriptLanguage);
hljs.registerLanguage("sql", sqlLanguage);

// 定义变量内容
const state = reactive({
  loading: false,
  previews: [],
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    activeName: 'model.java',
  },
});

const useGenerate = useGenerateApi();

// 定义变量内容
const { copyText } = commonFunction();

const getPreviewData = (id: number) => {
  state.loading = true;
  useGenerate.preview(id).then(res => {
    state.previews = res;
  });
  setTimeout(() => {
    state.loading = false;
  }, 500);
};
// 打开弹窗
const openDialog = (id: number) => {
  state.dialog.title = '预览生成代码';
  state.dialog.isShowDialog = true;
  getPreviewData(id);
};
// 关闭弹窗
const closeDialog = () => {
  state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
  closeDialog();
};

/** 高亮显示 */
const highlightedCode = (code, key) => {
  const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
  const language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
  const result = hljs.highlight(language, code || "", true);
  return result.value || '&nbsp;';
};

defineExpose({
  openDialog,
});
</script>