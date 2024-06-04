<template>
  <div class="website-webCategory-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webCategory-search mb15">
        <el-form :inline="true" ref="webCategorySearchRef" :model="state.tableData.param" size="default">
          <el-row>
            <el-form-item label="名称" class="ml10" size="default">
              <el-input v-model="state.tableData.param.name" placeholder="请输入名称" clearable
                        style="max-width: 180px"></el-input>
            </el-form-item>
            <el-button size="default" type="primary" class="ml10" @click="getTableData">
              <el-icon><ele-Search /></el-icon> 查询
            </el-button>
            <el-button size="default" class="ml10" @click="resetQuery">
              <el-icon><ele-Refresh /></el-icon> 重置
            </el-button>
          </el-row>
        </el-form>
      </div>
      <el-row :gutter="35">
        <el-button size="default" type="success" plain class="ml30" @click="onOpenAdd('add')">
          <el-icon><ele-FolderAdd /></el-icon> 新增
        </el-button>
      </el-row>
      <el-table
          :data="state.tableData.data"
          v-loading="state.tableData.loading"
          style="width: 100%"
          row-key="id"
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column label="名称" prop="name" show-overflow-tooltip/>
        <el-table-column label="描述" prop="description" show-overflow-tooltip/>
        <el-table-column label="排序" prop="sortNum" show-overflow-tooltip/>
        <el-table-column label="是否置顶" prop="isTop" show-overflow-tooltip>
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.isTop">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否热门" prop="isHot" show-overflow-tooltip>
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.isHot">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="作者" prop="target" show-overflow-tooltip/>
        <el-table-column label="跳转地址" prop="url" show-overflow-tooltip/>
        <el-table-column label="是否有效" prop="isValid" show-overflow-tooltip>
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.isValid">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" show-overflow-tooltip width="140">
          <template #default="scope">
            <el-button size="small" text type="primary" @click="onOpenAdd('add', scope.row)">新增</el-button>
            <el-button size="small" text type="primary" @click="onOpenEdit('edit', scope.row)">修改</el-button>
            <el-button size="small" text type="primary" @click="onTableRowDel(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <WebCategoryDialog ref="webCategoryDialogRef" @refresh="getTableData()" :webCategoryOptions="state.tableData.data"/>
  </div>
</template>

<script setup lang="ts" name="webCategory">
import { defineAsyncComponent, ref, reactive, nextTick, onMounted, getCurrentInstance } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { useWebCategoryApi } from '/@/api/website/category';
import { parseDateTime } from '/@/utils/formatTime';

// 引入组件
const WebCategoryDialog = defineAsyncComponent(() => import('./dialog.vue'));

// 定义变量内容
const webCategoryDialogRef = ref();
const webCategorySearchRef = ref();
const useWebCategory = useWebCategoryApi();
const state = reactive({
  tableData: {
    data: [],
    loading: false,
    param: {
      name: '',
      isTop: '',
      isHot: '',
      isValid: '',
    }
  },
});

// 初始化表格数据
const getTableData = () => {
  state.tableData.loading = true;
  state.tableData.data = [];
  useWebCategory.getTreeList(state.tableData.param).then(res => {
    state.tableData.data = res;
  });
  setTimeout(() => {
    state.tableData.loading = false;
  }, 500);
};
// 打开新增菜单弹窗
const onOpenAdd = (type: string, row) => {
  webCategoryDialogRef.value.openDialog(type, row);
};
// 打开编辑菜单弹窗
const onOpenEdit = (type: string, row) => {
  webCategoryDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTableRowDel = (row) => {
  ElMessageBox.confirm(`此操作将永久删除数据：${row.deptName}, 是否继续?`, '提示', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        useWebCategory.deleteWebCategory(row.id).then(() => {
          getTableData();
          ElMessage.success('删除成功');
        });
      })
      .catch(() => {});
};
// 重置搜索框
const resetQuery = () => {
  nextTick(() => {
    webCategorySearchRef.value.resetFields();
  });
}
// 页面加载时
onMounted(() => {
  getTableData();
});
</script>
<style scoped lang="scss">
.website-webCategory-container {
  :deep(.el-card__body) {
    display: flex;
    flex-direction: column;
    flex: 1;
    overflow: auto;
    .el-table {
      flex: 1;
    }
  }
}
</style>
