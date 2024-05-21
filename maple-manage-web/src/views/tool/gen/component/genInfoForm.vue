<template>
  <div class="gen-basic-dialog-container">
    <el-form :model="props.data" :rules="state.rules" ref="genInfoFormRef" size="default" label-width="150px">
      <el-row :gutter="35">
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成模板" prop="tplCategory">
            <el-select v-model="props.data.tplCategory" placeholder="请选择生成模板" clearable class="w100" @change="tplSelectChange">
              <el-option label="单表（增删改查）" value="crud" />
              <el-option label="树表（增删改查）" value="tree" />
              <el-option label="主子表（增删改查）" value="sub" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成包路径" prop="packageName">
            <el-input v-model="props.data.packageName" placeholder="请输入生成包路径" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成模块名" prop="moduleName">
            <el-input v-model="props.data.moduleName" placeholder="请输入生成模块名" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成业务名" prop="businessName">
            <el-input v-model="props.data.businessName" placeholder="请输入生成业务名" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成功能名" prop="functionName">
            <el-input v-model="props.data.functionName" placeholder="请输入生成功能名" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="生成代码方式" prop="genType">
            <el-select v-model="props.data.genType" placeholder="请选择生成代码方式" clearable class="w100">
              <el-option label="zip压缩包" value="0"></el-option>
              <el-option label="自定义路径" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="上级菜单" prop="parentMenuId">
            <el-tree-select
                :props="{ value: 'id', label: 'title' }"
                v-model="props.data.parentMenuId"
                :data="state.menuTreeData"
                check-strictly
                filterable
                :render-after-expand="false"
                class="w100"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="35" v-show="props.data.tplCategory == 'tree'">
        <el-col class="mb20">
          <h4 class="mb20">树表信息</h4>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="树编码字段" prop="treeCode">
            <el-select v-model="props.data.treeCode" placeholder="请选择" clearable  class="w100">
              <el-option
                  v-for="(table, index) in props.data.columns"
                  :key="index"
                  :label="table.columnName + '：' + table.columnComment"
                  :value="table.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="树父编码字段" prop="treeParentCode">
            <el-select v-model="props.data.treeParentCode" placeholder="请选择" clearable class="w100">
              <el-option
                  v-for="(column, index) in props.data.columns"
                  :key="index"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="树名称字段" prop="treeName">
            <el-select v-model="props.data.treeName" placeholder="请选择" clearable class="w100">
              <el-option
                  v-for="(column, index) in props.data.columns"
                  :key="index"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="35" v-show="props.data.tplCategory == 'sub'">
        <el-col class="mb20">
          <h4 class="mb20">关联信息</h4>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="关联子表的表名" prop="subTableName">
            <el-select v-model="props.data.subTableName" placeholder="请选择" clearable class="w100" @change="subSelectChange">
              <el-option
                  v-for="(table, index) in props.data.tables"
                  :key="index"
                  :label="table.tableName + '：' + table.tableComment"
                  :value="table.tableName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="子表关联的外键名" prop="subTableFkName">
            <el-select v-model="props.data.subTableFkName" placeholder="请选择" clearable class="w100">
              <el-option
                  v-for="(column, index) in state.subColumns"
                  :key="index"
                  :label="column.columnName + '：' + column.columnComment"
                  :value="column.columnName"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
  </div>
</template>

<script setup lang="ts" name="genInfoForm">
import {onMounted, reactive, watch} from 'vue';
import { useMenuApi } from '/@/api/system/menu';

// 定义父组件传过来的值
const props = defineProps({
  data: {
    type: Object,
    default: () => {},
  },
});

const useMenu = useMenuApi();

// 定义变量内容
const state = reactive({
  subColumns: [],
  menuTreeData: [],
  rules: {
    tplCategory: [
      { required: true, message: "请选择生成模板", trigger: "blur" }
    ],
    packageName: [
      { required: true, message: "请输入生成包路径", trigger: "blur" }
    ],
    moduleName: [
      { required: true, message: "请输入生成模块名", trigger: "blur" }
    ],
    businessName: [
      { required: true, message: "请输入生成业务名", trigger: "blur" }
    ],
    functionName: [
      { required: true, message: "请输入生成功能名", trigger: "blur" }
    ],
  },
});

watch(() => props.data.subTableName, (newValue) => {
  setSubTableColumns(newValue);
});

/** 选择子表名触发 */
const subSelectChange = (value) => {
  props.data.subTableFkName = '';
};
/** 选择生成模板触发 */
const tplSelectChange = (value) => {
  if(value !== 'sub') {
    state.subColumns = [];
    props.data.subTableName = '';
    props.data.subTableFkName = '';
  }
};
/** 设置关联外键 */
const setSubTableColumns = (value) => {
  for (var item in props.data.tables) {
    const name = props.data.tables[item].tableName;
    if (value === name) {
      state.subColumns = props.data.tables[item].columns;
      break;
    }
  }
}

function getMenuList() {
  useMenu.getTreeList({}).then(res => {
    state.menuTreeData = res;
  });
}

// 页面加载时
onMounted(() => {
  getMenuList();
});


</script>
