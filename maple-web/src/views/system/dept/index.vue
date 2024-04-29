<template>
	<div class="system-dept-container layout-padding">
		<el-card shadow="hover" class="layout-padding-auto">
			<div class="system-dept-search mb15">
				<el-input size="default" placeholder="请输入部门名称" style="max-width: 180px"> </el-input>
				<el-button size="default" type="primary" class="ml10"  @click="getTableData">
					<el-icon>
						<ele-Search />
					</el-icon>
					查询
				</el-button>
				<el-button size="default" type="success" class="ml10" @click="onOpenAddDept('add')">
					<el-icon>
						<ele-FolderAdd />
					</el-icon>
					新增部门
				</el-button>
			</div>
			<el-table
				:data="state.tableData.data"
				v-loading="state.tableData.loading"
				style="width: 100%"
				row-key="id"
				:tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			>
				<el-table-column prop="deptName" label="部门名称" show-overflow-tooltip> </el-table-column>
        <el-table-column prop="leader" label="负责人" show-overflow-tooltip> </el-table-column>
        <el-table-column prop="phone" label="联系电话" show-overflow-tooltip> </el-table-column>
        <el-table-column prop="email" label="邮箱" show-overflow-tooltip> </el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip width="80"></el-table-column>
				<el-table-column prop="status" label="部门状态" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="sys_status[scope.row.status].elTagType">{{ sys_status[scope.row.status].label }}</el-tag>
          </template>
				</el-table-column>
        <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
          <template #default="scope">{{ parseDateTime(scope.row.createTime) }}</template>
        </el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="140">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onOpenAddDept('add', scope.row)">新增</el-button>
						<el-button size="small" text type="primary" @click="onOpenEditDept('edit', scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onTableRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<DeptDialog ref="deptDialogRef" @refresh="getTableData()" :deptOptions="state.tableData.data"/>
	</div>
</template>

<script setup lang="ts" name="systemDept">
import {defineAsyncComponent, ref, reactive, onMounted, getCurrentInstance} from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { parseDateTime } from '/@/utils/formatTime';
import { useDeptApi } from '/@/api/system/dept';

// 获取字典
const { proxy } = getCurrentInstance();
const { sys_status } = proxy.parseDict("sys_status");

// 引入组件
const DeptDialog = defineAsyncComponent(() => import('./dialog.vue'));

// 定义变量内容
const deptDialogRef = ref();
const useDept = useDeptApi();
const state = reactive({
	tableData: {
		data: [],
		loading: false,
    param: {
		  
    }
	},
});

// 初始化表格数据
const getTableData = () => {
	state.tableData.loading = true;
	state.tableData.data = [];
  useDept.getTreeList(state.tableData.param).then(res => {
    state.tableData.data = res;
  });
	setTimeout(() => {
		state.tableData.loading = false;
	}, 500);
};
// 打开新增菜单弹窗
const onOpenAddDept = (type: string, row: DeptTreeType) => {
	deptDialogRef.value.openDialog(type, row);
};
// 打开编辑菜单弹窗
const onOpenEditDept = (type: string, row: DeptTreeType) => {
	deptDialogRef.value.openDialog(type, row);
};
// 删除当前行
const onTableRowDel = (row: DeptTreeType) => {
	ElMessageBox.confirm(`此操作将永久删除部门：${row.deptName}, 是否继续?`, '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
      useDept.deleteDept(row.id).then(() => {
        getTableData();
        ElMessage.success('删除成功');
      });
    })
		.catch(() => {});
};
// 页面加载时
onMounted(() => {
	getTableData();
});
</script>
