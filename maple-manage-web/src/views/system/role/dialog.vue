<template>
  <div class="system-role-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="50%">
      <el-form ref="roleDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="state.ruleForm.roleName" placeholder="请输入角色名称" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="角色编码" prop="roleKey">
              <el-input v-model="state.ruleForm.roleKey" placeholder="请输入角色编码" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="显示顺序" prop="sortNum">
              <el-input v-model="state.ruleForm.sortNum" placeholder="请输入显示顺序" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="角色状态" prop="status">
              <el-select v-model="state.ruleForm.status" placeholder="请选择角色状态" clearable class="w100">
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
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="3"  v-model="state.ruleForm.remark" placeholder="请输入备注" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="数据权限" prop="dataScope">
              <el-select v-model="state.ruleForm.dataScope" placeholder="请选择数据权限" clearable class="w100">
                <el-option
                    v-for="dict in sys_role_data_scope"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20" v-if="state.ruleForm.dataScope === '2' ">
            <el-form-item label="自定义权限" prop="deptIds">
              <el-tree
                  :data="state.deptData"
                  :props="{ label: 'deptName' }"
                  show-checkbox
                  node-key="id"
                  ref="deptRef"
                  check-strictly
                  v-model="state.ruleForm.deptIds"
                  class="menu-data-tree" >
              </el-tree>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="菜单权限" prop="menuIds">
              <el-tree 
                  :data="state.menuData"
                  :props="{ label: 'title' }"
                  show-checkbox
                  node-key="id"
                  ref="menuRef"
                  v-model="state.ruleForm.menuIds"
                  class="menu-data-tree" >
              </el-tree>
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
import { reactive, ref, getCurrentInstance, onMounted, defineEmits } from 'vue';
import { useRoleApi } from '/@/api/system/role';
import { useMenuApi } from '/@/api/system/menu';
import { useDeptApi } from '/@/api/system/dept';
import { ElMessage } from "element-plus";

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 获取字典
const { proxy } = getCurrentInstance();
const { sys_status, sys_role_data_scope } = proxy.parseDict("sys_status", "sys_role_data_scope");


// 定义变量内容
const useRole = useRoleApi();
const useMenu = useMenuApi();
const useDept = useDeptApi();
const roleDialogFormRef = ref();
const menuRef = ref();
const deptRef = ref();
const state = reactive({
  ruleForm: {
    roleName: '',
    roleKey: '',
    sortNum: '',
    dataScope: '1',
    menuCheckStrictly: true,
    deptCheckStrictly: true,
    status: 1,
    remark: '',
    menuIds:[],
    deptIds:[],
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  rules: {
    ancestorsArray: { required: true, message: '请选择父部门', trigger: 'blur' },
    roleName: { required: true, message: '请输入角色名称', trigger: 'blur' },
    roleKey: { required: true, message: '请输入角色编码', trigger: 'blur' },
    sortNum: { required: true, message: '请输入显示顺序', trigger: 'blur' },
    status: { required: true, message: '请输入角色状态', trigger: 'blur' },
  },
  menuData:[],
  deptData:[],
});

const getMenuData = () => {
  useMenu.getTreeList({}).then(res => {
    state.menuData = res;
  })
}

const getDeptData = () => {
  useDept.getTreeList({}).then(res => {
    state.deptData = res;
  })
}

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm()
  if (type === 'edit') {
    useRole.getRoleById(row.id).then(res => {
      state.ruleForm = res;
      let checkedKeys = res.menuIds
      checkedKeys.forEach((v) => {
        menuRef.value.setChecked(v, true ,false);
      });
      let deptCheckedKeys = res.deptIds
      deptCheckedKeys.forEach((v) => {
        deptRef.value.setChecked(v, true ,false);
      });
      state.dialog.title = '修改用户中心-角色信息';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    if(menuRef.value){
      menuRef.value.setCheckedKeys([]);
    }
    if(deptRef.value){
      deptRef.value.setCheckedKeys([]);
    }
    state.dialog.title = '新增用户中心-角色信息';
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
    currentValidate(roleDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      let checkedKeys = menuRef.value.getCheckedKeys();
      let halfCheckedKeys = menuRef.value.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      state.ruleForm.menuIds = checkedKeys;
      if(state.dialog.type == 'add'){
        useRole.createRole(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useRole.updateRole(state.ruleForm).then(() => {
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

const resetForm = () => {
  state.ruleForm = {
    roleName: '',
    roleKey: '',
    sortNum: '',
    dataScope: '1',
    menuCheckStrictly: true,
    deptCheckStrictly: true,
    status: 1,
    remark: '',
    menuIds:[],
    deptIds:[],
  };
}

// 主表-表单组件验证
const currentValidate = (pageRef) => {
  return new Promise((resolve) => {
    pageRef.value.validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};

// 页面加载时
onMounted(() => {
  getMenuData();
  getDeptData();
});
// 暴露变量
defineExpose({
  openDialog,
});
</script>

<style scoped lang="scss">
.system-role-dialog-container {
  .menu-data-tree {
    width: 100%;
    border: 1px solid var(--el-border-color);
    border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
    padding: 5px;
  }
}
</style>

