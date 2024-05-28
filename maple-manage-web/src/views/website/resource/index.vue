<template>
  <div class="website-webResource-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webResource-search">
        <el-form :inline="true" ref="webResourceSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="资源类型" class="ml20" prop="resourceType">
              <el-select v-model="state.tableData.param.query.resourceType" placeholder="请选择资源类型" clearable>
                <el-option
                  v-for="dict in web_resource_type"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="模板名称" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.resourceName" placeholder="请输入模板名称" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="下载类型 1:又拍云  2：百度云" class="ml20" prop="downType">
              <el-select v-model="state.tableData.param.query.downType" placeholder="请选择下载类型 1:又拍云  2：百度云" clearable>
                <el-option
                  v-for="dict in web_resource_type"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="下载地址" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.downUrl" placeholder="请输入下载地址" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="文件后缀" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.fileSuffix" placeholder="请输入文件后缀" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="预览地址" class="ml20" size="default">
              <el-input v-model="state.tableData.param.query.visitUrl" placeholder="请输入预览地址" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="状态" class="ml20" prop="status">
              <el-select v-model="state.tableData.param.query.status" placeholder="请选择状态" clearable>
                <el-option
                  v-for="dict in sys_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
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
        <el-table-column type="index" label="序号" width="60" />
        <el-table-column label="ID" prop="id" show-overflow-tooltip/>
        <el-table-column label="资源类型" prop="resourceType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="web_resource_type[scope.row.resourceType].elTagType">{{ web_resource_type[scope.row.resourceType].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="模板名称" prop="resourceName" show-overflow-tooltip/>
        <el-table-column label="模板描述" prop="resourceDesc" show-overflow-tooltip/>
        <el-table-column label="图片" prop="resourceImg" show-overflow-tooltip>
          <template #default="scope">
            <el-image
                :src="scope.row.resourceImg"
                :zoom-rate="1.2"
                :preview-src-list="[scope.row.resourceImg]"
                preview-teleported
                fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="下载类型 1:又拍云  2：百度云" prop="downType" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="web_resource_type[scope.row.downType].elTagType">{{ web_resource_type[scope.row.downType].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="下载地址" prop="downUrl" show-overflow-tooltip/>
        <el-table-column label="文件后缀" prop="fileSuffix" show-overflow-tooltip/>
        <el-table-column label="预览地址" prop="visitUrl" show-overflow-tooltip/>
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
        <el-table-column label="状态" prop="status" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="sys_status[scope.row.status].elTagType">{{ sys_status[scope.row.status].label }}</el-tag>
          </template>
        </el-table-column>
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
    <WebResourceDialog ref="webResourceDialogRef" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="webResource">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebResourceApi } from '/@/api/website/resource';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 获取字典
  const { proxy } = getCurrentInstance();
  const { web_resource_type,sys_status } = proxy.parseDict("web_resource_type","sys_status");
  
  // 引入组件
  const WebResourceDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webResourceDialogRef = ref();
  const webResourceSearchRef = ref();
  const useWebResource = useWebResourceApi();
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
          resourceType: '',
          resourceName: '',
          downType: '',
          downUrl: '',
          fileSuffix: '',
          visitUrl: '',
          isTop: '',
          isHot: '',
          status: '',
        },
      },
    },
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useWebResource.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增网站资源弹窗
  const onOpenAdd = (type: string) => {
    webResourceDialogRef.value.openDialog(type);
  };
  // 打开修改网站资源弹窗
  const onOpenEdit = (type: string, row) => {
    webResourceDialogRef.value.openDialog(type, row);
  };
  // 删除网站资源
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebResource.deleteWebResource(row.id).then(() => {
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
        webResourceSearchRef.value.resetFields();
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
  .website-webResource-container {
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
