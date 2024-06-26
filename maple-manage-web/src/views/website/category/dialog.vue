<template>
  <div class="website-webCategory-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
      <el-form ref="webCategoryDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="父节点" prop="ancestorsArray">
              <el-cascader
                  :options="props.webCategoryOptions"
                  :props="{ checkStrictly: true, value: 'id', label: 'name' }"
                  placeholder="请选择节点"
                  filterable
                  clearable
                  class="w100"
                  v-model="state.ruleForm.ancestorsArray"
              >
                <template #default="{ node, data }">
                  <span>{{ data.name }}</span>
                  <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
                </template>
              </el-cascader>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="名称" prop="name">
              <el-input v-model="state.ruleForm.name" placeholder="请输入名称" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="描述" prop="description">
              <el-input v-model="state.ruleForm.description" placeholder="请输入描述" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="作者" prop="target">
              <el-input v-model="state.ruleForm.target" placeholder="请输入作者" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="跳转地址" prop="url">
              <el-input v-model="state.ruleForm.url" placeholder="请输入跳转地址" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="排序" prop="sortNum">
              <el-input-number v-model="state.ruleForm.sortNum" placeholder="请输入排序"  />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否有效" prop="isValid">
              <el-switch v-model="state.ruleForm.isValid" inline-prompt active-text="是" inactive-text="否"></el-switch>
            </el-form-item>
          </el-col>
          
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否置顶" prop="isTop">
              <el-switch v-model="state.ruleForm.isTop" inline-prompt active-text="是" inactive-text="否"></el-switch>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否热门" prop="isHot">
              <el-switch v-model="state.ruleForm.isHot" inline-prompt active-text="是" inactive-text="否"></el-switch>
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
import { useWebCategoryApi } from '/@/api/website/category';
import { ElMessage } from "element-plus";

// 定义父组件传过来的值
const props = defineProps({
  webCategoryOptions: {
    type: Array,
    default: () => [],
  }
});

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);


// 定义变量内容
const useWebCategory = useWebCategoryApi();
const webCategoryDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    icon: '',
    name: '',
    description: '',
    sortNum: '',
    isTop: true,
    isHot: true,
    target: '',
    url: '',
    isValid: true,
    parentId: '',
    ancestors: '',
    ancestorsArray: [],
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  rules: {
    name: { required: true, message: '请输入名称', trigger: 'blur' },
    isTop: { required: true, message: '请输入是否置顶', trigger: 'blur' },
    isHot: { required: true, message: '请输入是否热门', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useWebCategory.getWebCategoryById(row.id).then(res => {
      state.ruleForm = res;
      state.ruleForm.ancestorsArray = state.ruleForm.ancestors.split(",").map(item => Number(item));
      state.dialog.title = '修改网站类目';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    if(row) {
      if(row.ancestors) {
        state.ruleForm.ancestorsArray = row.ancestors.split(",").map(item => Number(item));
      }
      state.ruleForm.ancestorsArray.push(row.id)
    }
    state.dialog.title = '新增网站类目';
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
    currentValidate(webCategoryDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      state.ruleForm.parentId = state.ruleForm.ancestorsArray[state.ruleForm.ancestorsArray.length - 1]
      state.ruleForm.ancestors = state.ruleForm.ancestorsArray.join(",");
      if(state.dialog.type == 'add'){
        useWebCategory.createWebCategory(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useWebCategory.updateWebCategory(state.ruleForm).then(() => {
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
    icon: '',
    name: '',
    description: '',
    sortNum: '',
    isTop: true,
    isHot: true,
    target: '',
    url: '',
    isValid: true,
    parentId: '',
    ancestors: '',
    ancestorsArray: [],
  }
}
// 暴露变量
defineExpose({
  openDialog,
});
</script>

