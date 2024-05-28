<template>
  <div class="website-webArticle-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="60%">
          <el-form ref="webArticleDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="所属类目id" prop="categoryId">
                  <el-select v-model="state.ruleForm.categoryId" placeholder="请选择所属类目id" clearable class="w100">
                    <el-option label="请选择字典生成" value="" />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="标题" prop="title">
                  <el-input v-model="state.ruleForm.title" placeholder="请输入标题" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-form-item label="描述" prop="description">
                  <el-input type="textarea" :rows="3"  v-model="state.ruleForm.description" placeholder="请输入描述" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="图片" prop="img">
                  <el-upload
                      class="img-uploader"
                      action="/manageApi/file/uploadImage"
                      :headers="{Authorization: `${Session.get('token')}`}"
                      :show-file-list="false"
                      :on-success="handleImageSuccess"
                      :on-change="beforeImageUpload"
                  >
                    <img v-if="state.ruleForm.img" :src="state.ruleForm.img" class="img" />
                    <el-icon v-else class="img-uploader-icon"><ele-Plus /></el-icon>
                  </el-upload>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="排序" prop="sortNum">
                  <el-input-number v-model="state.ruleForm.sortNum" placeholder="请输入排序"  />
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="关键词" prop="keywords">
                  <el-input v-model="state.ruleForm.keywords" placeholder="请输入关键词，多个以英文逗号分割" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="作者" prop="author">
                  <el-input v-model="state.ruleForm.author" placeholder="请输入作者" clearable></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="原文地址" prop="originalUrl">
                  <el-input v-model="state.ruleForm.originalUrl" placeholder="请输入原文地址" clearable></el-input>
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

              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="状态" prop="status">
                  <el-select v-model="state.ruleForm.status" placeholder="请选择状态" clearable class="w100">
                    <el-option
                        v-for="dict in approve_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                  </el-select>
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
  import { useWebArticleApi } from '/@/api/website/article';
  import { ElMessage } from "element-plus";
  import { Session } from "/@/utils/storage";
  
  
  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);

  // 获取字典
  const { proxy } = getCurrentInstance();
  const { approve_status,web_article_source } = proxy.parseDict("approve_status","web_article_source");


  // 定义变量内容
  const useWebArticle = useWebArticleApi();
  const webArticleDialogFormRef = ref();
  const state = reactive({
    ruleForm: {
      categoryId: '',
      title: '',
      description: '',
      img: '',
      sortNum: '',
      keywords: '',
      author: '',
      originalUrl: '',
      isTop: true,
      isHot: true,
      status: '',
    },
    dialog: {
      isShowDialog: false,
      type: '',
      title: '',
      submitTxt: '',
    },
    rules: {
      title: { required: true, message: '请输入标题', trigger: 'blur' },
      isTop: { required: true, message: '请输入是否置顶', trigger: 'blur' },
      isHot: { required: true, message: '请输入是否热门', trigger: 'blur' },
    },
  });

  // 打开弹窗
  const openDialog = (type: string, row) => {
    resetForm();
    if (type === 'edit') {
      useWebArticle.getWebArticleById(row.id).then(res => {
        state.ruleForm = res;
        state.dialog.title = '修改网站文章';
        state.dialog.submitTxt = '修 改';
      });
    } else {
      state.dialog.title = '新增网站文章';
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
      currentValidate(webArticleDialogFormRef),
    ]).then(res => {
      const validateResult = res.every(item => !!item);
      if (validateResult) {
        if(state.dialog.type == 'add'){
          useWebArticle.createWebArticle(state.ruleForm).then(() => {
            ElMessage.success('创建成功');
            closeDialog();
            emit('refresh');
          });
        } else {
          useWebArticle.updateWebArticle(state.ruleForm).then(() => {
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


  
  const beforeImageUpload = (rawFile) => {
    if (rawFile.raw.type !== 'image/jpeg' && rawFile.raw.type !== 'image/png') {
      ElMessage.error('图片只支持jpg或png格式')
      return false
    } else if(rawFile.size / 1024 / 1024 > 3) {
      ElMessage.error("图片大小不能超过3MB");
      return false;
    }
    return true;
  }

  const handleImageSuccess = (response) => {
    if(response.code === '0000') {
      state.ruleForm.img = response.data;
    } else {
      ElMessage.error(response.msg);
    }
  }
  
  const resetForm = () => {
    state.ruleForm = {
      categoryId: '',
      title: '',
      description: '',
      img: '',
      sortNum: '',
      keywords: '',
      author: '',
      originalUrl: '',
      isTop: true,
      isHot: true,
      status: '',
    }
  }
  // 暴露变量
  defineExpose({
    openDialog,
  });
</script>

<style scoped>
  .img-uploader .avatar {
    width: 350px;
    height: 160px;
    display: block;
  }
</style>

<style>
  .img-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .img-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }

  .el-icon.img-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 350px;
    height: 160px;
    text-align: center;
  }
</style>
