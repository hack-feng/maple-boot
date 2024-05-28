<template>
  <div class="website-webConfig-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webConfigDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="网站标题" prop="title">
                  <el-input v-model="state.ruleForm.title" placeholder="请输入网站标题" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="网站描述" prop="description">
                  <el-input v-model="state.ruleForm.description" placeholder="请输入网站描述" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="网站图标" prop="webImg">
                  <el-upload
                      class="img-uploader"
                      action="/manageApi/file/uploadImage"
                      :headers="{Authorization: `${Session.get('token')}`}"
                      :show-file-list="false"
                      :on-success="handleImageSuccess"
                      :on-change="beforeImageUpload"
                  >
                    <img v-if="state.ruleForm.webImg" :src="state.ruleForm.webImg" class="img" />
                    <el-icon v-else class="img-uploader-icon"><ele-Plus /></el-icon>
                  </el-upload>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="备案号" prop="icp">
                  <el-input v-model="state.ruleForm.icp" placeholder="请输入备案号" clearable></el-input>
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
  import { useWebConfigApi } from '/@/api/website/config';
  import { ElMessage } from "element-plus";
  import { Session } from "/@/utils/storage";
  
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);


  // 定义变量内容
  const useWebConfig = useWebConfigApi();
  const webConfigDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      title: '',
      description: '',
      webImg: '',
      icp: '',
    },
    dialog: {
      isShowDialog: false,
      type: '',
      title: '',
      submitTxt: '',
    },
    rules: {
      description: { required: true, message: '请输入网站描述', trigger: 'blur' },
    },
  });

  // 打开弹窗
  const openDialog = (type: string, row) => {
    resetForm();
    if (type === 'edit') {
      useWebConfig.getWebConfigById(row.id).then(res => {
        state.ruleForm = res;
        state.dialog.title = '修改网站用户';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      state.dialog.title = '新增网站用户';
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
      currentValidate(webConfigDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        if(state.dialog.type == 'add'){
          useWebConfig.createWebConfig(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebConfig.updateWebConfig(state.ruleForm).then(() => {
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


  
  const beforeImageUpload = (rawFile) => {
    if (rawFile.raw.type !== 'image/jpeg' && rawFile.raw.type !== 'image/png') {
      ElMessage.error('图片只支持jpg或png格式')
      return false
    } else if(rawFile.size / 1024 / 1024 > 3) {
      ElMessage.error("图片大小不能超过3MB");
      return false;
    }
    return true;
  }

  const handleImageSuccess = (response) => {
    if(response.code === '0000') {
      state.ruleForm.webImg = response.data;
    } else {
      ElMessage.error(response.msg);
    }
  }
  
  const resetForm = () => {
    state.ruleForm = {
      title: '',
      description: '',
      webImg: '',
      icp: '',
    }
  }
  // 暴露变量
  defineExpose({
    openDialog,
  });
</script>

<style scoped>
  .img-uploader .avatar {
    width: 350px;
    height: 160px;
    display: block;
  }
</style>

<style>
  .img-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .img-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }

  .el-icon.img-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 350px;
    height: 160px;
    text-align: center;
  }
</style>
