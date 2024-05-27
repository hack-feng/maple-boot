<script setup>
import {onMounted, ref} from "vue";
import setMaterialInput from "@/assets/js/material-input";
import {ElMessage} from 'element-plus'
import FormInstance from 'element-plus'
import { getToken } from '@/utils/auth'

import { getUserInfo } from "@/api/login";
import { updateUser } from "@/api/user";

onMounted(() => {
  setMaterialInput();
  getUserInfoClick();
});

const ruleFormRef = ref<FormInstance>({})

// do not use same name with ref
const userForm = ref({
  nickName: '',
  sex: 2,
  email: undefined,
  userDesc: undefined,
  headPortraitLink: undefined,
})

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.raw.type !== 'image/jpeg' && rawFile.raw.type !== 'image/png') {
    ElMessage.error('图片只支持jpg或png格式')
    return false
  } else if(rawFile.size / 1024 / 1024 > 1) {
    ElMessage.error("图片大小不能超过1MB");
    return false;
  }
  return true;
}

const handleAvatarSuccess = (response) => {
  if(response.code === '0000') {
    userForm.value.headPortraitLink = response.data;
  } else {
    ElMessage.error(response.msg);
  }
}


const getUserInfoClick = () => {
  getUserInfo().then(res => {
    userForm.value = res;
  });
}

const onSubmit = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      updateUser(userForm.value).then(res => {
        ElMessage.success("修改成功！")
      });
    } else {
      ElMessage.info("输入数据有误")
      return false
    }
  })
}

</script>

<template>
  <section>
    <div class="card card-body blur shadow-blur mx-3 mt-3">
      <div class="col-6 mx-auto">
        <div class="text-center">
          <el-upload
            class="avatar-uploader"
            action="/api/user/uploadUpy"
            :headers="{Authorization: getToken()}"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :on-change="beforeAvatarUpload"
          >
            <img v-if="userForm.headPortraitLink" :src="userForm.headPortraitLink" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </div>

        <el-form :model="userForm" ref="ruleFormRef" label-width="120px">
          <el-form-item label="昵称" prop="nickName"
            :rules="[
              { required: true, message: '必填哟' },
            ]"
          >
            <el-input v-model="userForm.nickName" maxlength="10"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email"
            :rules="[
              { type: 'email', message: '请输入正确的邮箱地址' },
            ]"
          >
            <el-input v-model="userForm.email" maxlength="50"/>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userForm.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">女</el-radio>
              <el-radio :label="3">保密</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input v-model="userForm.userDesc" type="textarea" maxlength="150"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit(ruleFormRef)">确认修改</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </section>
</template>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>