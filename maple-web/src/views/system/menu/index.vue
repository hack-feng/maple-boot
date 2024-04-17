<template>
  <div class="system-menu-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="system-menu-search mb15">
        <el-form :inline="true" ref="menuSearchRef" :model="state.tableData.param" size="default">
          <el-row>
            <el-form-item label="菜单名称" class="ml10" size="default">
              <el-input v-model="state.tableData.param.menuName" placeholder="请输入菜单名称" clearable
                        style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="菜单类型" class="ml10" prop="menuType">
              <el-select v-model="state.tableData.param.menuType" placeholder="请选择菜单类型" clearable>
                <el-option
                    v-for="dict in menu_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="菜单状态" class="ml20" prop="status">
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
        <el-table-column label="菜单名称" prop="menuName" show-overflow-tooltip width="220">
          <template #default="scope">
            <SvgIcon :name="scope.row.icon" />
            <span class="ml10">{{ scope.row.title }}</span>
          </template>
          </el-table-column>
        <el-table-column label="显示顺序" prop="sortNum" show-overflow-tooltip/>
        <el-table-column label="组件路径" prop="component" show-overflow-tooltip width="260"/>
        <el-table-column label="菜单类型" prop="menuType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="menu_type[scope.row.menuType].elTagType">{{ menu_type[scope.row.menuType].label }}</el-tag>
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
    <MenuDialog ref="menuDialogRef" @refresh="getTableData()" :menuOptions="state.tableData.data"/>
  </div>
</template>

<script setup lang="ts" name="menu">
import { defineAsyncComponent, ref, reactive, nextTick, onMounted, getCurrentInstance } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { useMenuApi } from '/@/api/system/menu';
import { parseDateTime } from '/@/utils/formatTime';

// 获取字典
const { proxy } = getCurrentInstance();
const { menu_type,sys_status,sys_show_hide } = proxy.parseDict("menu_type","sys_status","sys_show_hide");

// 引入组件
const MenuDialog = defineAsyncComponent(() => import('./dialog.vue'));

// 定义变量内容
const menuDialogRef = ref();
const menuSearchRef = ref();
const useMenu = useMenuApi();
const state = reactive({
  tableData: {
    data: [],
    loading: false,
    param: {
      menuName: '',
      menuType: '',
      status: '',
    }
  },
});

// 初始化表格数据
const getTableData = () => {
  state.tableData.loading = true;
  state.tableData.data = [];
  useMenu.getTreeList(state.tableData.param).then(res => {
    state.tableData.data = res;
  });
  setTimeout(() => {
    state.tableData.loading = false;
  }, 500);
};
// 打开新增菜单弹窗
const onOpenAdd = (type: string, row) => {
  menuDialogRef.value.openDialog(type, row);
};
// 打开编辑菜单弹窗
const onOpenEdit = (type: string, row) => {
  menuDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTableRowDel = (row) => {
  ElMessageBox.confirm(`此操作将永久删除数据：${row.path}, 是否继续?`, '提示', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        useMenu.deleteMenu(row.id).then(() => {
          getTableData();
          ElMessage.success('删除成功');
        });
      })
      .catch(() => {});
};
// 重置搜索框
const resetQuery = () => {
  nextTick(() => {
    menuSearchRef.value.resetFields();
  });
}
// 页面加载时
onMounted(() => {
  getTableData();
});
</script>
<style scoped lang="scss">
.system-menu-container {
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
