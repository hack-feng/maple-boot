<template>
  <div class="system-user-dialog-container">
		<el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="70%">
      <div class="system-user-search mb15">
          <el-row :gutter="35">
          <el-form-item label="表名称" class="ml10" size="default">
              <el-input v-model="state.tableData.query.tableName" placeholder="请输入表名称" style="max-width: 180px"></el-input>
          </el-form-item>
          <el-form-item label="表描述" class="ml10" size="default">
              <el-input v-model="state.tableData.query.tableComment" placeholder="请输入表描述" style="max-width: 180px"></el-input>
          </el-form-item>
          <el-button size="default" type="primary" class="ml10" @click="getTableData">
              <el-icon>
              <ele-Search />
              </el-icon>
              查询
          </el-button>
          </el-row>
      </div>
      <el-table :data="state.tableData.records" v-loading="state.tableData.loading" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="tableName" label="表名称" show-overflow-tooltip></el-table-column>
          <el-table-column prop="tableComment" label="表描述" show-overflow-tooltip></el-table-column>
          <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
          <template #default="scope">{{ parseDateTime(scope.row.createTime) }}</template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip>
          <template #default="scope">{{ parseDateTime(scope.row.updateTime) }}</template>
          </el-table-column>
      </el-table>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">导入</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>
  
<script setup lang="ts" name="dbDialog">
  import { reactive, ref } from 'vue';
  import { ElMessage } from 'element-plus';
  import { useGenerateApi } from '/@/api/tool/generate';
  import { parseDateTime } from '/@/utils/formatTime';

  // 定义子组件向父组件传值/事件
  const emit = defineEmits(['refresh']);
  
  // 定义变量内容
  const state = reactive({
    tableData: {
      records: [],
      loading: false,
      query: {
        tableName: '',
        tableComment: '',
      },
    },
    dialog: {
      isShowDialog: false,
      type: '',
      title: '',
    },
    multipleSelection: []
  });

  const handleSelectionChange = (val) => {
    state.multipleSelection = val
  }
  
  const useGenerate = useGenerateApi();
  
  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useGenerate.getDbList(state.tableData.query).then(res => {
      state.tableData.records = res;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开弹窗
  const openDialog = () => {
      state.dialog.title = '导入表';
      state.dialog.isShowDialog = true;
      getTableData();
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
    let tableNames = state.multipleSelection.map(item => {
      return item.tableName;
    })
    useGenerate.importTable({"tableNames" : tableNames}).then(() => {
      ElMessage.success('导入成功');
      closeDialog();
      emit('refresh');
    });
  };

  defineExpose({
      openDialog,
  });
</script>
  
  