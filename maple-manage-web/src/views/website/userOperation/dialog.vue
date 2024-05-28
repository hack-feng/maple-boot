<template>
  <div class="website-webUserOperation-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webUserOperationDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="类型" prop="dataType">
                  <el-select v-model="state.ruleForm.dataType" placeholder="请选择类型" clearable class="w100">
                    <el-option
                        v-for="dict in web_data_type"
                        :key="Number(dict.value)"
                        :label="dict.label"
                        :value="Number(dict.value)"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="数据id" prop="dataId">
                  <el-input v-model="state.ruleForm.dataId" placeholder="请输入数据id" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="用户id" prop="userId">
                  <el-input v-model="state.ruleForm.userId" placeholder="请输入用户id" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="是否阅读" prop="isRead">
                  <el-radio-group v-model="state.ruleForm.isRead">
                    <el-radio value="">请选择字典</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="是否点赞" prop="isLike">
                  <el-radio-group v-model="state.ruleForm.isLike">
                    <el-radio value="">请选择字典</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="是否收藏" prop="isCollect">
                  <el-radio-group v-model="state.ruleForm.isCollect">
                    <el-radio value="">请选择字典</el-radio>
                  </el-radio-group>
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
  import { useWebUserOperationApi } from '/@/api/website/userOperation';
  import { ElMessage } from "element-plus";
  
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);

  // 获取字典
  const { proxy } = getCurrentInstance();
  const { web_data_type } = proxy.parseDict("web_data_type");


  // 定义变量内容
  const useWebUserOperation = useWebUserOperationApi();
  const webUserOperationDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      dataType: '',
      dataId: '',
      userId: '',
      isRead: '',
      isLike: '',
      isCollect: '',
    },
    dialog: {
      isShowDialog: false,
      type: '',
      title: '',
      submitTxt: '',
    },
    rules: {
      dataType: { required: true, message: '请输入类型', trigger: 'blur' },
      dataId: { required: true, message: '请输入数据id', trigger: 'blur' },
      userId: { required: true, message: '请输入用户id', trigger: 'blur' },
      isRead: { required: true, message: '请输入是否阅读', trigger: 'blur' },
      isLike: { required: true, message: '请输入是否点赞', trigger: 'blur' },
      isCollect: { required: true, message: '请输入是否收藏', trigger: 'blur' },
    },
  });

  // 打开弹窗
  const openDialog = (type: string, row) => {
    resetForm();
    if (type === 'edit') {
      useWebUserOperation.getWebUserOperationById(row.id).then(res => {
        state.ruleForm = res;
        state.dialog.title = '修改网站用户操作';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      state.dialog.title = '新增网站用户操作';
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
      currentValidate(webUserOperationDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        if(state.dialog.type == 'add'){
          useWebUserOperation.createWebUserOperation(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebUserOperation.updateWebUserOperation(state.ruleForm).then(() => {
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
      dataType: '',
      dataId: '',
      userId: '',
      isRead: '',
      isLike: '',
      isCollect: '',
    }
  }
  // 暴露变量
  defineExpose({
    openDialog,
  });
</script>

