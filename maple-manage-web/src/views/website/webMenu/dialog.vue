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
            <el-form-item label="菜单名称" prop="title">
              <el-input v-model="state.ruleForm.title" placeholder="请输入菜单名称" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单类型" prop="menuType">
              <el-select v-model="state.ruleForm.menuType" placeholder="请选择菜单类型" clearable class="w100">
                <el-option
                    v-for="dict in website_menu_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="路由名称" prop="name">
              <el-input v-model="state.ruleForm.name" placeholder="请输入路由名称" clearable></el-input>
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
            <el-form-item label="菜单状态" prop="status">
              <el-select v-model="state.ruleForm.status" placeholder="请选择菜单状态" class="w100">
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
            <el-form-item label="是否外链" prop="isLink">
              <el-switch v-model="state.ruleForm.isLink" inline-prompt active-text="是" inactive-text="否"></el-switch>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="链接地址" prop="linkUrl">
              <el-input v-model="state.ruleForm.linkUrl" placeholder="请输入链接地址" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="显示顺序" prop="sortNum">
              <el-input-number v-model="state.ruleForm.sortNum" placeholder="请输入显示顺序"  />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单图标" prop="icon">
              <el-input v-model="state.ruleForm.icon" placeholder="请输入菜单图标" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单大图" prop="image">
              <el-upload
                  class="img-uploader"
                  action="/manageApi/file/uploadImage"
                  :headers="{Authorization: `${Session.get('token')}`}"
                  :show-file-list="false"
                  :on-success="handleImageSuccess"
                  :on-change="beforeImageUpload"
              >
                <img v-if="state.ruleForm.image" :src="state.ruleForm.image" class="img" />
                <el-icon v-else class="img-uploader-icon"><ele-Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="3"  v-model="state.ruleForm.remark" placeholder="请输入备注" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="关联目录" prop="categoryList">
              <el-tree
                  :data="state.categoryData"
                  :props="{ label: 'name' }"
                  show-checkbox
                  node-key="id"
                  ref="categoryRef"
                  check-strictly
                  default-expand-all
                  v-model="state.ruleForm.categoryList"
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
import {
  reactive,
  ref,
  getCurrentInstance,
  defineProps,
  defineEmits,
  onMounted, nextTick
} from 'vue';
import { useWebMenuApi } from '/@/api/website/webMenu';
import { useWebCategoryApi } from '/@/api/website/category';
import { ElMessage } from "element-plus";
import { Session } from "/@/utils/storage";

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
const { website_menu_type,sys_status } = proxy.parseDict("website_menu_type","sys_status");


// 定义变量内容
const useWebMenu = useWebMenuApi();
const useWebCategory = useWebCategoryApi();
const webMenuDialogFormRef = ref();
const categoryRef = ref();
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
    isLink: true,
    status: 1,
    icon: '',
    remark: '',
    parentId: '',
    categoryList: [],
    ancestors: '',
    ancestorsArray: [],
  },
  dialog: {
    isShowDialog: false,
    type: '',
    title: '',
    submitTxt: '',
  },
  categoryData: [],
  rules: {
    title: { required: true, message: '请输入菜单名称', trigger: 'blur' },
    menuType: { required: true, message: '请选择菜单类型', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row) => {
  resetForm();
  if (type === 'edit') {
    useWebMenu.getWebMenuById(row.id).then(res => {
      state.ruleForm = res;
      let checkedKeys = res.categoryList;
      if(checkedKeys && checkedKeys.length > 0) {
        checkedKeys.forEach((v) => {
          categoryRef.value.setChecked(v, true ,false);
        });
      } else {
        categoryRef.value.setCheckedKeys([]);
      }

      nextTick(()=> {
        state.ruleForm.ancestorsArray = state.ruleForm.ancestors.split(",").map(item => Number(item));
      });
      state.dialog.title = '修改网站菜单';
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
    if(categoryRef.value){
      categoryRef.value.setCheckedKeys([]);
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
      state.ruleForm.parentId = state.ruleForm.ancestorsArray[state.ruleForm.ancestorsArray.length - 1];
      state.ruleForm.ancestors = state.ruleForm.ancestorsArray.join(",");

      let checkedKeys = categoryRef.value.getCheckedKeys();
      let halfCheckedKeys = categoryRef.value.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      state.ruleForm.categoryList = checkedKeys;
      
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
    state.ruleForm.image = response.data;
  } else {
    ElMessage.error(response.msg);
  }
}

const getCategoryData = () => {
  useWebCategory.getTreeList({}).then(res => {
    state.categoryData = res;
  })
}

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
    isLink: false,
    status: 1,
    icon: '',
    remark: '',
    parentId: '',
    categoryList: [],
    ancestors: '',
    ancestorsArray: [],
  }
}

// 页面加载时
onMounted(() => {
  getCategoryData();
});

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

.menu-data-tree {
  width: 100%;
  border: 1px solid var(--el-border-color);
  border-radius: var(--el-input-border-radius, var(--el-border-radius-base));
  padding: 5px;
}

</style>
