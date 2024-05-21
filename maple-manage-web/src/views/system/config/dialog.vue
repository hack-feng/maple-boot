<template>
  <div class="system-config-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
      <el-form ref="configDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="参数名称" prop="configName">
              <el-input v-model="state.ruleForm.configName" placeholder="请输入参数名称" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="参数键名" prop="configKey">
              <el-input v-model="state.ruleForm.configKey" placeholder="请输入参数键名" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="参数键值" prop="configValue">
              <el-input type="textarea" :rows="3"  v-model="state.ruleForm.configValue" placeholder="请输入参数键值" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否系统内置" prop="configType">
              <el-switch v-model="state.ruleForm.configType" inline-prompt active-text="是" inactive-text="否"></el-switch>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="3"  v-model="state.ruleForm.remark" placeholder="请输入备注" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel" size="default">取 消</el-button>
          <el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="dictDialog">
import { defineAsyncComponent, nextTick, reactive, ref, getCurrentInstance, defineProps, defineEmits } from 'vue';
import { useConfigApi } from '/@/api/system/config';
import { ElMessage } from "element-plus";


// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);


// 定义变量内容
const useConfig = useConfigApi();
const configDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    configName: '',
    configKey: '',
    configValue: '',
    configType: true,
    remark: '',
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  rules: {
    configName: { required: true, message: '请输入参数名称', trigger: 'blur' },
    configKey: { required: true, message: '请输入参数键名', trigger: 'blur' },
    configValue: { required: true, message: '请输入参数键值', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useConfig.getConfigById(row.id).then(res => {
      state.ruleForm = res;
      state.dialog.title = '修改系统管理-参数配置';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    state.dialog.title = '新增系统管理-参数配置';
    state.dialog.submitTxt = '新 增';
  }
  state.dialog.type = type;
  state.dialog.isShowDialog = true;
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
    currentValidate(configDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      if(state.dialog.type == 'add'){
        useConfig.createConfig(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useConfig.updateConfig(state.ruleForm).then(() => {
          ElMessage.success('修改成功');
          closeDialog();
          emit('refresh');
        });
      }
    } else {
      ElMessage.error("表单校验未通过，请重新检查提交内容");
    }
  });
};

// 主表-表单组件验证
const currentValidate = (pageRef) => {
  return new Promise((resolve) => {
    pageRef.value.validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};



const resetForm = () => {
  state.ruleForm = {
    configName: '',
    configKey: '',
    configValue: '',
    configType: true,
    remark: '',
  }
}
// 暴露变量
defineExpose({
  openDialog,
});
</script>

