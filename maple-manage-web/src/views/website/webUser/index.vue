<template>
  <div class="website-webUser-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webUser-search">
        <el-form :inline="true" ref="webUserSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="用户账号" class="ml20" prop="userNo" size="default">
              <el-input v-model="state.tableData.param.query.userNo" placeholder="请输入用户账号" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="昵称" class="ml20" prop="nickName" size="default">
              <el-input v-model="state.tableData.param.query.nickName" placeholder="请输入昵称" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="用户状态" class="ml20" prop="status">
              <el-select v-model="state.tableData.param.query.status" placeholder="请选择用户状态" clearable>
                <el-option
                    v-for="dict in sys_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
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
        <el-table-column label="ID" prop="id" show-overflow-tooltip/>
        <el-table-column label="用户账号" prop="userNo" show-overflow-tooltip/>
        <el-table-column label="昵称" prop="nickName" show-overflow-tooltip/>
        <el-table-column label="性别" prop="sex" show-overflow-tooltip>
          <template #default="scope">
            <el-tag  v-if="scope.row.sex !== null" :type="sys_user_sex[scope.row.sex].elTagType">{{ sys_user_sex[scope.row.sex].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="用户状态" prop="status" show-overflow-tooltip>
          <template #default="scope">
            <el-tag v-if="scope.row.status !== null" :type="sys_status[scope.row.status].elTagType">{{ sys_status[scope.row.status].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="邮箱" prop="email" show-overflow-tooltip/>
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
    <WebUserDialog ref="webUserDialogRef" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="webUser">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebUserApi } from '/@/api/website/webUser';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 获取字典
  const { proxy } = getCurrentInstance();
  const { sys_user_sex, sys_status } = proxy.parseDict("sys_user_sex", "sys_status");
  
  // 引入组件
  const WebUserDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webUserDialogRef = ref();
  const webUserSearchRef = ref();
  const useWebUser = useWebUserApi();
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
          userNo: '',
          nickName: '',
          headPortraitLink: '',
          version: '',
        },
      },
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useWebUser.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增网站用户弹窗
  const onOpenAdd = (type: string) => {
    webUserDialogRef.value.openDialog(type);
  };
  // 打开修改网站用户弹窗
  const onOpenEdit = (type: string, row) => {
    webUserDialogRef.value.openDialog(type, row);
  };
  // 删除网站用户
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebUser.deleteWebUser(row.id).then(() => {
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
        webUserSearchRef.value.resetFields();
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
  .website-webUser-container {
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
