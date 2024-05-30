<template>
  <div class="website-webFriendlyLink-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webFriendlyLink-search">
        <el-form :inline="true" ref="webFriendlyLinkSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="链接类型" class="ml20" prop="linkType">
              <el-select v-model="state.tableData.param.query.linkType" placeholder="请选择链接类型" clearable>
                <el-option
                  v-for="dict in web_friendly_link_type"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="名称" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.name" placeholder="请输入名称" clearable
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
        <el-table-column label="链接类型" prop="linkType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="web_friendly_link_type[scope.row.linkType].elTagType">{{ web_friendly_link_type[scope.row.linkType].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name" show-overflow-tooltip/>
        <el-table-column label="图标" prop="logo" show-overflow-tooltip/>
        <el-table-column label="链接地址" prop="url" show-overflow-tooltip/>
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
        <el-table-column label="链接次数" prop="linkNum" show-overflow-tooltip/>
        <el-table-column label="描述" prop="description" show-overflow-tooltip/>
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
    <WebFriendlyLinkDialog ref="webFriendlyLinkDialogRef" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="webFriendlyLink">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebFriendlyLinkApi } from '/@/api/website/friendlyLink';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 获取字典
  const { proxy } = getCurrentInstance();
  const { web_friendly_link_type } = proxy.parseDict("web_friendly_link_type");
  
  // 引入组件
  const WebFriendlyLinkDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webFriendlyLinkDialogRef = ref();
  const webFriendlyLinkSearchRef = ref();
  const useWebFriendlyLink = useWebFriendlyLinkApi();
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
          linkType: '',
          name: '',
          isTop: '',
          isHot: '',
        },
      },
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useWebFriendlyLink.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增网站链接弹窗
  const onOpenAdd = (type: string) => {
    webFriendlyLinkDialogRef.value.openDialog(type);
  };
  // 打开修改网站链接弹窗
  const onOpenEdit = (type: string, row) => {
    webFriendlyLinkDialogRef.value.openDialog(type, row);
  };
  // 删除网站链接
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebFriendlyLink.deleteWebFriendlyLink(row.id).then(() => {
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
        webFriendlyLinkSearchRef.value.resetFields();
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
  .website-webFriendlyLink-container {
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
