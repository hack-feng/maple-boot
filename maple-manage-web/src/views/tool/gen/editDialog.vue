<template>
  <div class="system-user-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="95%">
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="first">
          <BasicInfoForm ref="pagesBasicInfoFormRef" :data="state.records"/>
        </el-tab-pane>
        <el-tab-pane label="字段信息" name="second">
          <EditTable ref="pagesEditTableRef" :columns="state.records.columns"/>
        </el-tab-pane>
        <el-tab-pane label="生成信息" name="third">
          <GenInfoForm ref="pagesGenInfoFormRef" :data="state.records"/>
        </el-tab-pane>
      </el-tabs>
      
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">修改</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="dbDialog">
import {defineAsyncComponent, reactive, ref} from 'vue';
import {ElMessage} from 'element-plus';
import {useGenerateApi} from '/@/api/tool/generate';

const BasicInfoForm = defineAsyncComponent(() => import('/@/views/tool/gen/component/basicInfoForm.vue'));
const EditTable = defineAsyncComponent(() => import('/@/views/tool/gen/component/editTable.vue'));
const GenInfoForm = defineAsyncComponent(() => import('/@/views/tool/gen/component/genInfoForm.vue'));

const pagesBasicInfoFormRef = ref();
const pagesEditTableRef = ref();
const pagesGenInfoFormRef = ref();

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const activeName = ref('first')
const state = reactive({
  loading: false,
  records: {},
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
  },
  multipleSelection: []
});

const handleSelectionChange = (val) => {
  state.multipleSelection = val
}

const useGenerate = useGenerateApi();

// 初始化表格数据
const getGenData = (row: object) => {
  state.loading = true;
  useGenerate.getInfo(row.id).then(res => {
    state.records = res;
  });
  setTimeout(() => {
    state.loading = false;
  }, 500);
};
// 打开弹窗
const openDialog = (row) => {
  state.dialog.title = '修改';
  state.dialog.isShowDialog = true;
  getGenData(row);
};
// 关闭弹窗
const closeDialog = () => {
  state.dialog.isShowDialog = false;
};
// 取消
const onCancel = () => {
  closeDialog();
};
// 提交
const onSubmit = () => {
  // 验证表单
  Promise.all([
    formRulesValidate(pagesBasicInfoFormRef, 'basicInfoFormRef'),
    formRulesValidate(pagesGenInfoFormRef, 'genInfoFormRef'),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      useGenerate.updateGenTable(state.records).then(() => {
        ElMessage.success('修改成功');
        closeDialog();
        emit('refresh');
      });
    } else {
      ElMessage.error("表单校验未通过，请重新检查提交内容");
    }
    
  });
};

// 表单组件验证
const formRulesValidate = (pageRef: RefType, sonRef: string) => {
  return new Promise((resolve) => {
    pageRef.value.$refs[sonRef].validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};

defineExpose({
  openDialog,
});
</script>
  
  