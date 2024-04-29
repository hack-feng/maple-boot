<template>
  <div class="system-config-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="system-config-search">
        <el-form :inline="true" ref="configSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="参数名称" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.configName" placeholder="请输入参数名称" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="参数键名" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.configKey" placeholder="请输入参数键名" clearable
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
        <el-table-column label="参数主键" prop="id" show-overflow-tooltip/>
        <el-table-column label="参数名称" prop="configName" show-overflow-tooltip/>
        <el-table-column label="参数键名" prop="configKey" show-overflow-tooltip/>
        <el-table-column label="参数键值" prop="configValue" show-overflow-tooltip/>
        <el-table-column label="是否内置" prop="configType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.configType">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" prop="updateTime" show-overflow-tooltip>
          <template #default="scope">
            <span>{{ parseDateTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="remark" show-overflow-tooltip/>
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
    <ConfigDialog ref="configDialogRef" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="config">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useConfigApi } from '/@/api/system/config';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 引入组件
  const ConfigDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const configDialogRef = ref();
  const configSearchRef = ref();
  const useConfig = useConfigApi();
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
          configName: '',
          configKey: '',
          configValue: '',
          configType: '',
        },
      },
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useConfig.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增系统管理-参数配置弹窗
  const onOpenAdd = (type: string) => {
    configDialogRef.value.openDialog(type);
  };
  // 打开修改系统管理-参数配置弹窗
  const onOpenEdit = (type: string, row) => {
    configDialogRef.value.openDialog(type, row);
  };
  // 删除系统管理-参数配置
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useConfig.deleteConfig(row.id).then(() => {
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
        configSearchRef.value.resetFields();
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
  .system-config-container {
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
