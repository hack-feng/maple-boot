<template>
  <div class="website-webUser-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
      <el-form ref="webUserDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default"
               label-width="90px">
        <el-row :gutter="35">

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户账号" prop="userNo">
              <el-input v-model="state.ruleForm.userNo" placeholder="保存时用户账号自动生成" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="openId" prop="openid">
              <el-input v-model="state.ruleForm.openid" placeholder="绑定微信小程序自动关联openId" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="state.ruleForm.nickName" placeholder="请输入昵称" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="state.ruleForm.sex" placeholder="请选择性别" clearable class="w100">
                <el-option
                    v-for="dict in sys_user_sex"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="state.ruleForm.email" placeholder="请输入邮箱" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="邀请用户ID" prop="inviterUserId">
              <el-input v-model="state.ruleForm.inviterUserId" placeholder="请输入邀请用户ID" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="帐号状态" prop="status">
              <el-select v-model="state.ruleForm.status" placeholder="请选择帐号状态" clearable class="w100">
                <el-option
                    v-for="dict in sys_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="用户描述" prop="userDesc">
              <el-input type="textarea" :rows="3" v-model="state.ruleForm.userDesc" placeholder="请输入用户描述"
                        clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="头像链接" prop="headPortraitLink">
              <el-input type="textarea" :rows="3" v-model="state.ruleForm.headPortraitLink" placeholder="请输入头像链接"
                        clearable></el-input>
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
import {defineEmits, getCurrentInstance, reactive, ref} from 'vue';
import {useWebUserApi} from '/@/api/website/webUser';
import {ElMessage} from "element-plus";


// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 获取字典
const {proxy} = getCurrentInstance();
const {sys_user_sex, sys_status} = proxy.parseDict("sys_user_sex", "sys_status");


// 定义变量内容
const useWebUser = useWebUserApi();
const webUserDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    userNo: '',
    openid: '',
    unionid: '',
    nickName: '',
    sex: '',
    email: '',
    userDesc: '',
    inviterUserId: '',
    headPortraitLink: '',
    version: '',
    status: 1,
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useWebUser.getWebUserById(row.id).then(res => {
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
    currentValidate(webUserDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      if (state.dialog.type == 'add') {
        useWebUser.createWebUser(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useWebUser.updateWebUser(state.ruleForm).then(() => {
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
    userNo: '',
    openid: '',
    unionid: '',
    nickName: '',
    sex: '',
    email: '',
    userDesc: '',
    inviterUserId: '',
    headPortraitLink: '',
    version: '',
    status: 1,
  }
}
// 暴露变量
defineExpose({
  openDialog,
});
</script>

