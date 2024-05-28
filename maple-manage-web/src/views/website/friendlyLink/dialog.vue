<template>
  <div class="website-webFriendlyLink-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webFriendlyLinkDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="链接类型" prop="linkType">
                  <el-select v-model="state.ruleForm.linkType" placeholder="请选择链接类型" clearable class="w100">
                    <el-option
                        v-for="dict in web_friendly_link_type"
                        :key="Number(dict.value)"
                        :label="dict.label"
                        :value="Number(dict.value)"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="名称" prop="name">
                  <el-input v-model="state.ruleForm.name" placeholder="请输入名称" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="图标" prop="logo">
                  <el-input v-model="state.ruleForm.logo" placeholder="请输入图标" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-form-item label="链接地址" prop="url">
                  <el-input type="textarea" :rows="3"  v-model="state.ruleForm.url" placeholder="请输入链接地址" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="排序" prop="sortNum">
                  <el-input-number v-model="state.ruleForm.sortNum" placeholder="请输入排序"  />
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

              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-form-item label="描述" prop="description">
                  <el-input type="textarea" :rows="3"  v-model="state.ruleForm.description" placeholder="请输入描述" clearable></el-input>
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
  import { useWebFriendlyLinkApi } from '/@/api/website/friendlyLink';
  import { ElMessage } from "element-plus";
  
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);

  // 获取字典
  const { proxy } = getCurrentInstance();
  const { web_friendly_link_type } = proxy.parseDict("web_friendly_link_type");


  // 定义变量内容
  const useWebFriendlyLink = useWebFriendlyLinkApi();
  const webFriendlyLinkDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      linkType: '',
      name: '',
      logo: '',
      url: '',
      sortNum: '',
      isTop: true,
      isHot: true,
      description: '',
    },
    dialog: {
      isShowDialog: false,
      type: '',
      title: '',
      submitTxt: '',
    },
    rules: {
    },
  });

  // 打开弹窗
  const openDialog = (type: string, row) => {
    resetForm();
    if (type === 'edit') {
      useWebFriendlyLink.getWebFriendlyLinkById(row.id).then(res => {
        state.ruleForm = res;
        state.dialog.title = '修改网站链接';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      state.dialog.title = '新增网站链接';
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
      currentValidate(webFriendlyLinkDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        if(state.dialog.type == 'add'){
          useWebFriendlyLink.createWebFriendlyLink(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebFriendlyLink.updateWebFriendlyLink(state.ruleForm).then(() => {
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
      linkType: '',
      name: '',
      logo: '',
      url: '',
      sortNum: '',
      isTop: true,
      isHot: true,
      description: '',
    }
  }
  // 暴露变量
  defineExpose({
    openDialog,
  });
</script>

