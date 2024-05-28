<template>
  <div class="website-webUserComment-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webUserCommentDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="主数据ID" prop="dataId">
                  <el-input v-model="state.ruleForm.dataId" placeholder="请输入主数据ID" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="祖评论ID" prop="topId">
                  <el-input v-model="state.ruleForm.topId" placeholder="请输入祖评论ID" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="发表用户id" prop="userId">
                  <el-input v-model="state.ruleForm.userId" placeholder="请输入发表用户id" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="回复用户id" prop="toUserId">
                  <el-input v-model="state.ruleForm.toUserId" placeholder="请输入回复用户id" clearable></el-input>
                </el-form-item>
              </el-col>


              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="评论类型" prop="commentType">
                  <el-select v-model="state.ruleForm.commentType" placeholder="请选择评论类型" clearable class="w100">
                    <el-option label="请选择字典生成" value="" />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="state.ruleForm.status">
                    <el-radio value="">请选择字典</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="点赞数量" prop="likeNum">
                  <el-input v-model="state.ruleForm.likeNum" placeholder="请输入点赞数量" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="评论数量" prop="commentNum">
                  <el-input v-model="state.ruleForm.commentNum" placeholder="请输入评论数量" clearable></el-input>
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
  import { useWebUserCommentApi } from '/@/api/website/userComment';
  import { ElMessage } from "element-plus";
  
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);


  // 定义变量内容
  const useWebUserComment = useWebUserCommentApi();
  const webUserCommentDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      dataId: '',
      topId: '',
      userId: '',
      toUserId: '',
      content: '',
      commentType: '',
      status: '',
      likeNum: '',
      commentNum: '',
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
      useWebUserComment.getWebUserCommentById(row.id).then(res => {
        state.ruleForm = res;
        state.dialog.title = '修改网站用户评论';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      state.dialog.title = '新增网站用户评论';
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
      currentValidate(webUserCommentDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        if(state.dialog.type == 'add'){
          useWebUserComment.createWebUserComment(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebUserComment.updateWebUserComment(state.ruleForm).then(() => {
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
      dataId: '',
      topId: '',
      userId: '',
      toUserId: '',
      content: '',
      commentType: '',
      status: '',
      likeNum: '',
      commentNum: '',
    }
  }
  // 暴露变量
  defineExpose({
    openDialog,
  });
</script>

