<template>
  <div class="system-menu-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
      <el-form ref="menuDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="上级菜单" prop="ancestorsArray">
              <el-cascader
                  :options="props.menuOptions"
                  :props="{ checkStrictly: true, value: 'id', label: 'title' }"
                  placeholder="请选择上级菜单"
                  clearable
                  class="w100"
                  v-model="state.ruleForm.ancestorsArray"
              >
                <template #default="{ node, data }">
                  <span> {{ data.title }} </span>
                  <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
                </template>
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="state.ruleForm.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单名称" prop="title">
              <el-input v-model="state.ruleForm.title" placeholder="请输入菜单名称" clearable></el-input>
            </el-form-item>
          </el-col>
          <template v-if="state.ruleForm.menuType === 'C'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="state.ruleForm.name" placeholder="路由中的 name 值" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="路由路径" prop="path">
                <el-input v-model="state.ruleForm.path" placeholder="路由中的 path 值" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="组件路径" prop="component">
                <el-input v-model="state.ruleForm.component" placeholder="组件路径" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="链接地址" prop="linkUrl">
                <el-input
                    v-model="state.ruleForm.linkUrl"
                    placeholder="外链/内嵌时链接地址（http:xxx.com）"
                    clearable
                    :disabled="!state.ruleForm.isLink"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </template>
          <template v-if="state.ruleForm.menuType === 'F'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="权限标识" prop="perms">
                <el-input v-model="state.ruleForm.perms" placeholder="请输入权限标识" clearable></el-input>
              </el-form-item>
            </el-col>
          </template>
          <template v-if="state.ruleForm.menuType === 'M'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="重定向地址" prop="redirect">
                <el-input v-model="state.ruleForm.redirect" placeholder="请输入路由重定向" clearable></el-input>
              </el-form-item>
            </el-col>
          </template>
          <template v-if="state.ruleForm.menuType === 'M' || state.ruleForm.menuType === 'C'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="菜单图标" prop="icon">
                <IconSelector placeholder="请输入菜单图标" v-model="state.ruleForm.icon"  class="w100"/>
              </el-form-item>
            </el-col>
          </template>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单排序" prop="sortNum">
              <el-input-number v-model="state.ruleForm.sortNum" controls-position="right" placeholder="请输入排序" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单状态" prop="status">
              <el-select v-model="state.ruleForm.status" placeholder="请选择菜单状态" clearable class="w100">
                <el-option
                    v-for="dict in sys_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <template v-if="state.ruleForm.menuType === 'C'">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="是否隐藏" prop="isHide">
                <el-radio-group v-model="state.ruleForm.isHide">
                  <el-radio :label="true">隐藏</el-radio>
                  <el-radio :label="false">不隐藏</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="页面缓存" prop="isKeepAlive">
                <el-radio-group v-model="state.ruleForm.isKeepAlive">
                  <el-radio :label="true">缓存</el-radio>
                  <el-radio :label="false">不缓存</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="是否固定" prop="isAffix">
                <el-radio-group v-model="state.ruleForm.isAffix">
                  <el-radio :label="true">固定</el-radio>
                  <el-radio :label="false">不固定</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="是否外链" prop="isLink">
                <el-radio-group v-model="state.ruleForm.isLink" :disabled="state.ruleForm.isIframe">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="是否内嵌" prop="isIframe">
                <el-radio-group v-model="state.ruleForm.isIframe" @change="onSelectIframeChange">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </template>
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
import { useMenuApi } from '/@/api/system/menu';
import { ElMessage } from "element-plus";
// 定义父组件传过来的值
const props = defineProps({
  menuOptions: {
    type: Array,
    default: () => [],
  }
});

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 引入组件
const IconSelector = defineAsyncComponent(() => import('/@/components/iconSelector/index.vue'));

// 获取字典
const { proxy } = getCurrentInstance();
const { menu_type,sys_status,sys_show_hide } = proxy.parseDict("menu_type","sys_status","sys_show_hide");


// 定义变量内容
const useMenu = useMenuApi();
const menuDialogFormRef = ref();
const state = reactive({
  ruleForm: {
    name: '',
    title: '',
    menuType: 'C',
    sortNum: '',
    path: '',
    component: '',
    linkUrl: '',
    redirect: '',
    isLink: false,
    isIframe: false,
    isKeepAlive: true,
    isHide: false,
    isAffix: false,
    status: 1,
    perms: '',
    icon: '',
    remark: '',
    parentId: 0,
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
    title: { required: true, message: '请输入菜单名称', trigger: 'blur' },
    menuType: { required: true, message: '请输入菜单类型', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useMenu.getMenuById(row.id).then(res => {
      state.ruleForm = res;
      nextTick(()=> {
        state.ruleForm.ancestorsArray = state.ruleForm.ancestors.split(",").map(item => Number(item));
      });
      state.dialog.title = '修改用户中心-菜单权限';
      state.dialog.submitTxt = '修 改';
    });
  } else {
    if(row) {
      if(row.ancestors) {
        state.ruleForm.ancestorsArray = row.ancestors.split(",").map(item => Number(item));
      }
      nextTick(()=> {
        state.ruleForm.ancestorsArray.push(row.id);
      });
    }
    state.dialog.title = '新增用户中心-菜单权限';
    state.dialog.submitTxt = '新 增';
    // 清空表单，此项需加表单验证才能使用
    nextTick(() => {
      menuDialogFormRef.value.resetFields();
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
    currentValidate(menuDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      if(state.ruleForm.ancestorsArray) {
        state.ruleForm.parentId = state.ruleForm.ancestorsArray[state.ruleForm.ancestorsArray.length - 1];
        state.ruleForm.ancestors = state.ruleForm.ancestorsArray.join(",");
      } else {
        state.ruleForm.parentId = 0;
        state.ruleForm.ancestors = '';
      }
      if(state.dialog.type == 'add'){
        useMenu.createMenu(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useMenu.updateMenu(state.ruleForm).then(() => {
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

// 是否内嵌下拉改变
const onSelectIframeChange = () => {
  if (state.ruleForm.isIframe) state.ruleForm.isLink = true;
  else state.ruleForm.isLink = false;
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
    name: '',
    title: '',
    menuType: 'C',
    sortNum: '',
    path: '',
    component: '',
    linkUrl: '',
    redirect: '',
    isLink: false,
    isIframe: false,
    isKeepAlive: true,
    isHide: false,
    isAffix: false,
    status: 1,
    perms: '',
    icon: '',
    remark: '',
    parentId: 0,
    ancestors: '',
    ancestorsArray: [],
  };
}

// 暴露变量
defineExpose({
  openDialog,
});
</script>
