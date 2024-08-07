<template>
  <div class="website-webArticle-container layout-padding">
    <el-card shadow="hover" class="layout-padding-auto">
      <div class="website-webArticle-search">
        <el-form :inline="true" ref="webArticleSearchRef" :model="state.tableData.param.query" size="default">
          <el-row>
            <el-form-item label="标题" class="ml20" prop="title" size="default">
              <el-input v-model="state.tableData.param.query.title" placeholder="请输入标题" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item prop="status" label="文章类目" class="ml20" size="default">
              <el-select v-model="state.tableData.param.query.categoryId" filterable clearable placeholder="请选择文章类目">
                <el-option
                    v-for="(value, key) in state.categoryOption"
                    :key="key"
                    :label="value"
                    :value="key"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="作者" class="ml20" prop="author" size="default">
              <el-input v-model="state.tableData.param.query.author" placeholder="请输入作者" clearable
                      style="max-width: 180px"></el-input>
            </el-form-item>
            <el-form-item label="状态" class="ml20" prop="status">
              <el-select v-model="state.tableData.param.query.status" placeholder="请选择状态" clearable>
                <el-option
                  v-for="dict in approve_status"
                    :key="Number(dict.value)"
                    :label="dict.label"
                    :value="Number(dict.value)"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="来源" class="ml20" prop="source">
              <el-select v-model="state.tableData.param.query.source" placeholder="请选择来源" clearable>
                <el-option
                  v-for="dict in web_article_source"
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
        <el-table-column label="所属类目" prop="categoryId" show-overflow-tooltip>
          <template #default="scope">
            <span v-text="state.categoryOption[scope.row.categoryId] != null ? state.categoryOption[scope.row.categoryId] : ''"></span>
          </template>
        </el-table-column>
        <el-table-column label="标题" prop="title" show-overflow-tooltip width="260"/>
        <el-table-column label="排序" prop="sortNum" show-overflow-tooltip/>
        <el-table-column label="状态" prop="status" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="approve_status[scope.row.status].elTagType">{{ approve_status[scope.row.status].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="来源" prop="source" show-overflow-tooltip>
          <template #default="scope">
            <el-tag :type="web_article_source[scope.row.source].elTagType">{{ web_article_source[scope.row.source].label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="阅读" prop="readNum" show-overflow-tooltip/>
        <el-table-column label="点赞" prop="likeNum" show-overflow-tooltip/>
        <el-table-column label="收藏" prop="collectNum" show-overflow-tooltip/>
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
    <WebArticleDialog ref="webArticleDialogRef" :categoryOption="state.categoryOption" @refresh="getTableData()" />
  </div>
</template>

<script setup lang="ts" name="webArticle">
  import { defineAsyncComponent, reactive, onMounted, ref, nextTick, getCurrentInstance } from 'vue';
  import { ElMessageBox, ElMessage } from 'element-plus';
  import { useWebArticleApi } from '/@/api/website/article';
  import { useWebCategoryApi } from '/@/api/website/category';
  import { parseDateTime } from '/@/utils/formatTime';
  
  // 获取字典
  const { proxy } = getCurrentInstance();
  const { approve_status,web_article_source } = proxy.parseDict("approve_status","web_article_source");
  
  // 引入组件
  const WebArticleDialog = defineAsyncComponent(() => import('./dialog.vue'));

  // 定义变量内容
  const webArticleDialogRef = ref();
  const webArticleSearchRef = ref();
  const useWebArticle = useWebArticleApi();
  const useWebCategory = useWebCategoryApi();
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
          categoryId: '',
          title: '',
          keywords: '',
          author: '',
          originalUrl: '',
          isTop: '',
          isHot: '',
          status: '',
          source: '',
        },
      },
    },
    categoryOption: {},
  });

  // 初始化表格数据
  const getTableData = () => {
    state.tableData.loading = true;
    useWebArticle.getPageList(state.tableData.param).then(res => {
      state.tableData.records = res.records;
      state.tableData.total = res.total;
    });
    setTimeout(() => {
      state.tableData.loading = false;
    }, 500);
  };
  // 打开新增网站文章弹窗
  const onOpenAdd = (type: string) => {
    webArticleDialogRef.value.openDialog(type);
  };
  // 打开修改网站文章弹窗
  const onOpenEdit = (type: string, row) => {
    webArticleDialogRef.value.openDialog(type, row);
  };
  // 删除网站文章
  const onRowDel = (row) => {
    ElMessageBox.confirm('此操作将永久删除数据，是否继续?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
        .then(() => {
          useWebArticle.deleteWebArticle(row.id).then(() => {
            getTableData();
            ElMessage.success('删除成功');
          });
        })
        .catch(() => {
          ElMessage.error('删除失败');
        });
  };

  const initOption = () => {
    useWebCategory.getCategoryList({"parentIdList":[]}).then(res => {
      state.categoryOption = res.reduce((acc, item) => {
        acc[item.id] = item.name;
        return acc;
      }, {});
    });
  }
  
  // 重置搜索框
  const resetQuery = () => {
    nextTick(() => {
        webArticleSearchRef.value.resetFields();
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
    initOption();
  });
</script>

<style scoped lang="scss">
  .website-webArticle-container {
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
