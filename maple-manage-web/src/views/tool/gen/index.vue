<template>
  <div class="tool-gen-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="tool-gen-search mb15">
        <el-row :gutter="35">
          <el-form-item label="表名称" class="ml10" size="default">
            <el-input v-model="state.tableData.param.query.tableName" placeholder="请输入表名称"
                      style="max-width: 180px"></el-input>
          </el-form-item>
          <el-form-item label="表描述" class="ml10" size="default">
            <el-input v-model="state.tableData.param.query.tableComment" placeholder="请输入表描述"
                      style="max-width: 180px"></el-input>
          </el-form-item>
          <el-button size="default" type="primary" class="ml10" @click="getTableData">
            <el-icon>
              <ele-Search/>
            </el-icon>
            查询
          </el-button>
        </el-row>
        <el-button size="default" type="primary" class="ml10" @click="generateCode()" plain>
          <el-icon>
            <ele-FolderAdd/>
          </el-icon>
          生成
        </el-button>
        <el-button size="default" type="success" class="ml10" @click="onOpenDb()" plain>
          <el-icon>
            <ele-FolderAdd/>
          </el-icon>
          导入
        </el-button>
      </div>
      <el-table :data="state.tableData.records" v-loading="state.tableData.loading" @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="60"/>
        <el-table-column prop="tableName" label="表名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="tableComment" label="表描述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="className" label="实体类" show-overflow-tooltip></el-table-column>
        <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
          <template #default="scope">{{ parseDateTime(scope.row.createTime) }}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip>
          <template #default="scope">{{ parseDateTime(scope.row.updateTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button size="small" text type="primary"
                       @click="onOpenPreview(scope.row)">预览 
            </el-button>
            <el-button size="small" text type="primary"
                       @click="download(scope.row)">生成
            </el-button>
            <el-button size="small" text type="primary"
                       @click="onOpenEdit(scope.row)">修改
            </el-button>
            <el-button size="small" text type="primary"
                       @click="onRowDel(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="onHandleSizeChange"
          @current-change="onHandleCurrentChange"
          class="mt15"
          :pager-count="5"
          :page-sizes="[10, 20, 30]"
          v-model:current-page="state.tableData.param.page.current"
          background
          v-model:page-size="state.tableData.param.page.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="state.tableData.total"
      >
      </el-pagination>
    </el-card>
    <DbDialog ref="dbDialogRef" @refresh="getTableData()"/>
    <EditDialog ref="editDialogRef" @refresh="getTableData()"/>
    <PreviewDialog ref="previewDialogRef"/>
  </div>
</template>

<script setup lang="ts" name="genTable">
import {defineAsyncComponent, reactive, onMounted, ref} from 'vue';
import {ElMessageBox, ElMessage} from 'element-plus';
import { useGenerateApi } from '/@/api/tool/generate';
import { parseDateTime } from '/@/utils/formatTime';

// 引入组件
const DbDialog = defineAsyncComponent(() => import('/@/views/tool/gen/dbDialog.vue'));
const EditDialog = defineAsyncComponent(() => import('/@/views/tool/gen/editDialog.vue'));
const PreviewDialog = defineAsyncComponent(() => import('/@/views/tool/gen/viewDialog.vue'));

// 定义变量内容
const dbDialogRef = ref();
const editDialogRef = ref();
const previewDialogRef = ref();

const state = reactive({
  tableData: {
    records: [],
    total: 0,
    loading: false,
    param: {
      page: {
        current: 1,
        size: 10,
      },
      query: {
        tableName: '',
        tableComment: '',
      },
    },
  },
  multipleSelection: []
});

const useGenerate = useGenerateApi();

// 初始化表格数据
const getTableData = () => {
  state.tableData.loading = true;
  useGenerate.getPageList(state.tableData.param).then(res => {
    state.tableData.records = res.records;
    state.tableData.total = res.total;
  });
  setTimeout(() => {
    state.tableData.loading = false;
  }, 500);
};

const onOpenPreview = (row) => {
  previewDialogRef.value.openDialog(row.id);
}

const generateCode = () => {
  let tableNames = state.multipleSelection.map(item => {
    return item.tableName;
  })
  console.log("tableNames", tableNames)
  useGenerate.batchDownload({"tableNames": tableNames}).then(() => {
    ElMessage.success('生成成功');
  });
}

const download = (row) => {
  useGenerate.download(row.tableName).then(() => {
    ElMessage.success('生成成功');
  });
}

const handleSelectionChange = (val) => {
  state.multipleSelection = val
}

// 打开修改生成配置的弹窗
const onOpenEdit = (row) => {
  editDialogRef.value.openDialog(row);
}
// 打开导入表的弹窗
const onOpenDb = () => {
  dbDialogRef.value.openDialog();
};
// 删除
const onRowDel = (row) => {
  ElMessageBox.confirm(`此操作将永久删除账户名称：“${row.tableName}”，是否继续?`, '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        useGenerate.deleteById(row.id).then(() => {
          getTableData();
          ElMessage.success('删除成功');
        });
      })
      .catch(() => {
      });
};
// 分页改变
const onHandleSizeChange = (val: number) => {
  state.tableData.param.page.size = val;
  getTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
  state.tableData.param.page.current = val;
  getTableData();
};
// 页面加载时
onMounted(() => {
  getTableData();
});
</script>

<style scoped lang="scss">
.tool-gen-container {
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
