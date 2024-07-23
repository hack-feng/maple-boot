<template>
  <div class="website-webMenu-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webMenu-search mb15">
        <el-form :inline="true" ref="webMenuSearchRef" :model="state.tableData.param" size="default">
          <el-row>
            <el-form-item label="菜单名称" class="ml10" size="default">
              <el-input v-model="state.tableData.param.title" placeholder="请输入菜单名称" clearable
                        style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="菜单状态" class="ml10" prop="status">
              <el-select v-model="state.tableData.param.status" placeholder="请选择菜单状态" clearable>
                <el-option
                    v-for="dict in sys_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
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
        <el-table-column label="菜单名称" prop="title" show-overflow-tooltip width="260"/>
        <el-table-column label="菜单类型" prop="menuType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag v-if="website_menu_type[scope.row.menuType]" :type="website_menu_type[scope.row.menuType].elTagType">{{ website_menu_type[scope.row.menuType].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="显示顺序" prop="sortNum" show-overflow-tooltip/>
        <el-table-column label="路由地址" prop="path" show-overflow-tooltip/>
        <el-table-column label="是否外链" prop="isLink" show-overflow-tooltip>
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.isLink">是</el-tag>
            <el-tag type="info" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="菜单状态" prop="status" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="sys_status[scope.row.status].elTagType">{{ sys_status[scope.row.status].label }}</el-tag>
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
    <WebMenuDialog ref="webMenuDialogRef" @refresh="getTableData()" :webMenuOptions="state.tableData.data"/>
  </div>
</template>

<script setup lang="ts" name="webMenu">
  import { defineAsyncComponent, ref, reactive, nextTick, onMounted, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebMenuApi } from '/@/api/website/webMenu';
  import { parseDateTime } from '/@/utils/formatTime';

  // 获取字典
  const { proxy } = getCurrentInstance();
  const { sys_status, website_menu_type } = proxy.parseDict("sys_status", "website_menu_type");

  // 引入组件
  const WebMenuDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webMenuDialogRef = ref();
  const webMenuSearchRef = ref();
  const useWebMenu = useWebMenuApi();
  const state = reactive({
    tableData: {
      data: [],
      loading: false,
      param: {
          title: '',
          menuType: '',
          status: '',
      }
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    state.tableData.data = [];
    useWebMenu.getTreeList(state.tableData.param).then(res => {
      state.tableData.data = res;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增菜单弹窗
  const onOpenAdd = (type: string, row) => {
      webMenuDialogRef.value.openDialog(type, row);
  };
  // 打开编辑菜单弹窗
  const onOpenEdit = (type: string, row) => {
    webMenuDialogRef.value.openDialog(type, row);
  };
  // 删除当前行
  const onTableRowDel = (row) => {
    ElMessageBox.confirm(`此操作将永久删除数据：${row.deptName}, 是否继续?`, '提示', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebMenu.deleteWebMenu(row.id).then(() => {
            getTableData();
            ElMessage.success('删除成功');
          });
        })
        .catch(() => {});
  };
  // 重置搜索框
  const resetQuery = () => {
    nextTick(() => {
            webMenuSearchRef.value.resetFields();
    });
  }
  // 页面加载时
  onMounted(() => {
    getTableData();
  });
</script>
<style scoped lang="scss">
    .website-webMenu-container {
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
