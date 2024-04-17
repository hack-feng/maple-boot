<template>
  <div class="system-user-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="50%">
      <el-form ref="userDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="用户类型" prop="userType">
              <el-radio-group v-model="state.ruleForm.userType">
                <el-radio
                    v-for="dict in system_user_type"
                    :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <template v-if="state.ruleForm.userType === '1'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="所属部门" prop="deptId">
                <el-tree-select
                    :props="{ value: 'id', label: 'deptName' }"
                    v-model="state.ruleForm.deptId"
                    :data="state.deptTreeData"
                    check-strictly
                    filterable
                    :render-after-expand="false"
                    class="w100"
                />
              </el-form-item>
            </el-col>
          </template>
          <template v-if="state.ruleForm.userType === '2'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="openId" prop="openId">
                <el-input v-model="state.ruleForm.openId" placeholder="请输入openId" clearable></el-input>
              </el-form-item>
            </el-col>
          </template>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户账号" prop="account">
              <el-input v-model="state.ruleForm.account" placeholder="请输入用户账号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户角色" prop="status">
              <el-select
                  v-model="state.ruleForm.roleIds" 
                  placeholder="用户角色" 
                  clearable
                  multiple
                  collapse-tags
                  class="w100">
                <el-option
                    v-for="role in state.roleOptions"
                    :key="role.id"
                    :label="role.roleName"
                    :value="role.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="state.ruleForm.nickName" placeholder="请输入用户昵称" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="真实姓名" prop="userName">
              <el-input v-model="state.ruleForm.userName" placeholder="请输入真实姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户性别" prop="sex">
              <el-select v-model="state.ruleForm.sex" placeholder="请选择用户性别" clearable class="w100">
                <el-option
                    v-for="dict in sys_user_sex"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户邮箱" prop="email">
              <el-input v-model="state.ruleForm.email" placeholder="请输入用户邮箱" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="state.ruleForm.phone" placeholder="请输入手机号码" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="头像地址" prop="avatar">
              <el-input v-model="state.ruleForm.avatar" placeholder="请输入头像地址" clearable></el-input>
            </el-form-item>
          </el-col>
          <template v-if="state.dialog.type === 'add'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="密码" prop="password">
                <el-input v-model="state.ruleForm.password" placeholder="请输入密码" clearable></el-input>
              </el-form-item>
            </el-col>
          </template>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="帐号状态" prop="status">
              <el-switch v-model="state.ruleForm.status"
                         active-value=1
                         inactive-value=0
                         inline-prompt
                         active-text="是"
                         inactive-text="否"></el-switch>
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
import { onMounted, nextTick, reactive, ref, getCurrentInstance } from 'vue';
import { useUserApi } from '/@/api/system/user';
import { useRoleApi } from '/@/api/system/role';
import { useDeptApi } from '/@/api/system/dept';
import { ElMessage } from "element-plus";
// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 获取字典
const { proxy } = getCurrentInstance();
const { sys_status,system_user_type,sys_user_sex } = proxy.parseDict("sys_status","system_user_type","sys_user_sex");


// 定义变量内容
const useUser = useUserApi();
const useRole = useRoleApi();
const useDept = useDeptApi();
const userDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    openId: '',
    deptId: '',
    account: '',
    userName: '',
    nickName: '',
    userType: '1',
    email: '',
    phone: '',
    sex: '',
    avatar: '',
    password: '',
    status: 1,
    remark: '',
    roleIds: [],
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  rules: {
    account: { required: true, message: '请输入用户账号', trigger: 'blur' },
    userType: { required: true, message: '请输入用户类型', trigger: 'blur' },
    password: { required: true, message: '请输入用户密码', trigger: 'blur' },
  },
  roleOptions: [],
  deptTreeData: [],
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useUser.getUserById(row.id).then(res => {
      state.ruleForm = res;
      state.dialog.title = '修改用户中心-用户信息';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    state.dialog.title = '新增用户中心-用户信息';
    state.dialog.submitTxt = '新 增';
    // 清空表单，此项需加表单验证才能使用
    nextTick(() => {
      userDialogFormRef.value.resetFields();
    });
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
    currentValidate(userDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      if(state.dialog.type == 'add'){
        useUser.createUser(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useUser.updateUser(state.ruleForm).then(() => {
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

// 获取角色列表
const getRoleList = () => {
  useRole.getRoleList().then(res => {
    state.roleOptions = res;
  });
};

// 获取部门列表
const getDeptList = () => {
  useDept.getTreeList({}).then(res => {
    state.deptTreeData = res;
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
    openId: '',
    deptId: '',
    account: '',
    userName: '',
    nickName: '',
    userType: '1',
    email: '',
    phone: '',
    sex: '',
    avatar: '',
    password: '',
    status: 1,
    remark: '',
    roleIds: [],
  };
}

// 页面加载时
onMounted(() => {
  getRoleList();
  getDeptList();
});

// 暴露变量
defineExpose({
  openDialog,
});
</script>
