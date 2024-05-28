<template>
  <div class="website-webMenu-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webMenuDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-form-item label="父节点" prop="ancestorsArray">
                  <el-cascader
                      :options="props.webMenuOptions"
                      :props="{ checkStrictly: true, value: 'id', label: 'title' }"
                      placeholder="请选择节点"
                      filterable
                      clearable
                      class="w100"
                      v-model="state.ruleForm.ancestorsArray"
                  >
                    <template #default="{ node, data }">
                      <span>{{ data.title }}</span>
                      <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
                    </template>
                  </el-cascader>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="路由名称" prop="name">
                  <el-input v-model="state.ruleForm.name" placeholder="请输入路由名称" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="菜单名称" prop="title">
                  <el-input v-model="state.ruleForm.title" placeholder="请输入菜单名称" clearable></el-input>
                </el-form-item>
              </el-col>


              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="菜单类型" prop="menuType">
                  <el-select v-model="state.ruleForm.menuType" placeholder="请选择菜单类型" clearable class="w100">
                    <el-option label="请选择字典生成" value="" />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="显示顺序" prop="sortNum">
                  <el-input-number v-model="state.ruleForm.sortNum" placeholder="请输入显示顺序"  />
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="路由地址" prop="path">
                  <el-input v-model="state.ruleForm.path" placeholder="请输入路由地址" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="组件路径" prop="component">
                  <el-input v-model="state.ruleForm.component" placeholder="请输入组件路径" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="链接地址" prop="linkUrl">
                  <el-input v-model="state.ruleForm.linkUrl" placeholder="请输入链接地址" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="是否外链" prop="isLink">
                  <el-radio-group v-model="state.ruleForm.isLink">
                    <el-radio value="">请选择字典</el-radio>
                  </el-radio-group>
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

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="菜单图标" prop="icon">
                  <el-input v-model="state.ruleForm.icon" placeholder="请输入菜单图标" clearable></el-input>
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
  import { useWebMenuApi } from '/@/api/website/webMenu';
  import { ElMessage } from "element-plus";
  
    // 定义父组件传过来的值
    const props = defineProps({
      webMenuOptions: {
        type: Array,
        default: () => [],
      }
    });
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);

  // 获取字典
  const { proxy } = getCurrentInstance();
  const { sys_status } = proxy.parseDict("sys_status");


  // 定义变量内容
  const useWebMenu = useWebMenuApi();
  const webMenuDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      name: '',
      title: '',
      image: '',
      menuType: '',
      sortNum: '',
      path: '',
      component: '',
      linkUrl: '',
      isLink: '',
      status: '',
      icon: '',
      remark: '',
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
      ancestorsArray: { required: true, message: '请选择父部门', trigger: 'blur' },
    },
  });

  // 打开弹窗
  const openDialog = (type: string, row) => {
    resetForm();
    if (type === 'edit') {
      useWebMenu.getWebMenuById(row.id).then(res => {
        state.ruleForm = res;
        state.ruleForm.ancestorsArray = JSON.parse(state.ruleForm.ancestors);
        state.dialog.title = '修改网站菜单';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      if(row) {
        if(row.ancestors) {
          state.ruleForm.ancestorsArray = JSON.parse(row.ancestors);
        }
        state.ruleForm.ancestorsArray.push(row.id)
      }
      state.dialog.title = '新增网站菜单';
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
      currentValidate(webMenuDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        state.ruleForm.parentId = state.ruleForm.ancestorsArray[state.ruleForm.ancestorsArray.length - 1]
        state.ruleForm.ancestors = JSON.stringify(state.ruleForm.ancestorsArray);
        if(state.dialog.type == 'add'){
          useWebMenu.createWebMenu(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebMenu.updateWebMenu(state.ruleForm).then(() => {
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
      name: '',
      title: '',
      image: '',
      menuType: '',
      sortNum: '',
      path: '',
      component: '',
      linkUrl: '',
      isLink: '',
      status: '',
      icon: '',
      remark: '',
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

