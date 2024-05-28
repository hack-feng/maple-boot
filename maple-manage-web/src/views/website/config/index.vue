<template>
  <div class="website-webConfig-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webConfig-search">
        <el-form :inline="true" ref="webConfigSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="网站标题" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.title" placeholder="请输入网站标题" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-button size="default" type="primary" class="ml20" @click="getTableData">
              <el-icon><ele-Search /></el-icon> 查询
            </el-button>
            <el-button size="default" class="ml20" @click="resetQuery">
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
      <el-table :data="state.tableData.records" v-loading="state.tableData.loading" style="width: 100%">
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column label="ID" prop="id" show-overflow-tooltip/>
        <el-table-column label="网站标题" prop="title" show-overflow-tooltip/>
        <el-table-column label="网站描述" prop="description" show-overflow-tooltip/>
        <el-table-column label="网站图标" prop="webImg" show-overflow-tooltip>
          <template #default="scope">
            <el-image
                :src="scope.row.webImg"
                :zoom-rate="1.2"
                :preview-src-list="[scope.row.webImg]"
                preview-teleported
                fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="备案号" prop="icp" show-overflow-tooltip/>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button size="small" text type="primary" @click="onOpenEdit('edit', scope.row)">修改</el-button>
            <el-button size="small" text type="primary" @click="onRowDel(scope.row)">删除</el-button>
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
    <WebConfigDialog ref="webConfigDialogRef" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="webConfig">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebConfigApi } from '/@/api/website/config';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 引入组件
  const WebConfigDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webConfigDialogRef = ref();
  const webConfigSearchRef = ref();
  const useWebConfig = useWebConfigApi();
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
          title: '',
        },
      },
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useWebConfig.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增网站用户弹窗
  const onOpenAdd = (type: string) => {
    webConfigDialogRef.value.openDialog(type);
  };
  // 打开修改网站用户弹窗
  const onOpenEdit = (type: string, row) => {
    webConfigDialogRef.value.openDialog(type, row);
  };
  // 删除网站用户
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebConfig.deleteWebConfig(row.id).then(() => {
            getTableData();
            ElMessage.success('删除成功');
          });
        })
        .catch(() => {
          ElMessage.error('删除失败');
        });
  };
  // 重置搜索框
  const resetQuery = () => {
    nextTick(() => {
        webConfigSearchRef.value.resetFields();
    });
  }
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
  .website-webConfig-container {
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
