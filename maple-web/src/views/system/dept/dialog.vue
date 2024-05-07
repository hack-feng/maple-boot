<template>
  <div class="system-dept-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="50%">
      <el-form ref="deptDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="父部门" prop="ancestorsArray">
              <el-cascader
                  :options="props.deptOptions"
                  :props="{ checkStrictly: true, value: 'id', label: 'deptName' }"
                  placeholder="请选择部门"
                  filterable
                  clearable
                  class="w100"
                  v-model="state.ruleForm.ancestorsArray"
              >
                <template #default="{ node, data }">
                  <span>{{ data.deptName }}</span>
                  <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
                </template>
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="state.ruleForm.deptName" placeholder="请输入部门名称" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="显示顺序" prop="sortNum">
              <el-input v-model="state.ruleForm.sortNum" placeholder="请输入显示顺序" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="state.ruleForm.leader" placeholder="请输入负责人" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="state.ruleForm.phone" placeholder="请输入联系电话" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="state.ruleForm.email" placeholder="请输入邮箱" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="部门状态" prop="status">
              <el-select v-model="state.ruleForm.status" placeholder="请选择部门状态" clearable  class="w100">
                <el-option
                    v-for="dict in sys_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel()" size="default">取 消</el-button>
          <el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="dictDialog">
import { defineAsyncComponent, nextTick, reactive, ref, getCurrentInstance } from 'vue';
import { useDeptApi } from '/@/api/system/dept';
import { ElMessage } from "element-plus";
// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义父组件传过来的值
const props = defineProps({
  deptOptions: {
    type: Array,
    default: () => [],
  }
});

// 获取字典
const { proxy } = getCurrentInstance();
const { sys_status } = proxy.parseDict("sys_status");


// 定义变量内容
const useDept = useDeptApi();
const deptDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    ancestors: '',
    ancestorsArray: [],
    parentId: '',
    deptName: '',
    sortNum: '',
    leader: '',
    phone: '',
    email: '',
    status: 1,
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  rules: {
    ancestorsArray: { required: true, message: '请选择父部门', trigger: 'blur' },
    deptName: { required: true, message: '请输入部门名称', trigger: 'blur' },
    sortNum: { required: true, message: '请输入显示顺序', trigger: 'blur' },
    status: { required: true, message: '请输入部门状态', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useDept.getDeptById(row.id).then(res => {
      state.ruleForm = res;
      state.ruleForm.ancestorsArray = state.ruleForm.ancestors.split(",").map(item => Number(item));
      console.log(state.ruleForm.ancestorsArray)
      state.dialog.title = '修改用户中心-部门';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    if(row) {
      console.log(row.ancestors)
      if(row.ancestors) {
        state.ruleForm.ancestorsArray = row.ancestors.split(",").map(item => Number(item));
      }
      state.ruleForm.ancestorsArray.push(row.id)
    }
    console.log(state.ruleForm.ancestorsArray)
    state.dialog.title = '新增用户中心-部门';
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
    currentValidate(deptDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      state.ruleForm.parentId = state.ruleForm.ancestorsArray[state.ruleForm.ancestorsArray.length - 1]
      state.ruleForm.ancestors = state.ruleForm.ancestorsArray.join(",");
      if(state.dialog.type == 'add') {
        useDept.createDept(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useDept.updateDept(state.ruleForm).then(() => {
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
// 子表-表单组件验证
const formRulesValidate = (pageRef, sonRef: string) => {
  return new Promise((resolve) => {
    pageRef.value.$refs[sonRef].validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};
const resetForm = () => {
  state.ruleForm = {
    ancestors: '',
    ancestorsArray: [],
    parentId: '',
    deptName: '',
    sortNum: '',
    leader: '',
    phone: '',
    email: '',
    status: 1,
  }
}


// 暴露变量
defineExpose({
  openDialog,
});
</script>
