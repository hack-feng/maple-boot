<template>
  <el-table ref="editTableRef" :data="props.columns" row-key="columnId" :max-height="tableHeight">
    <el-table-column label="序号" type="index" min-width="5%" class-name="allowDrag" />
    <el-table-column
        label="字段列名"
        prop="columnName"
        min-width="10%"
        :show-overflow-tooltip="true"
    />
    <el-table-column label="字段描述" min-width="11%">
      <template v-slot="scope">
        <el-input v-model="scope.row.columnComment"></el-input>
      </template>
    </el-table-column>
    <el-table-column
        label="物理类型"
        prop="columnType"
        min-width="10%"
        :show-overflow-tooltip="true"
    />
    <el-table-column label="Java类型" min-width="10%">
      <template v-slot="scope">
        <el-select v-model="scope.row.javaType">
          <el-option label="Long" value="Long" />
          <el-option label="String" value="String" />
          <el-option label="Integer" value="Integer" />
          <el-option label="Double" value="Double" />
          <el-option label="BigDecimal" value="BigDecimal" />
          <el-option label="Date" value="Date" />
          <el-option label="Boolean" value="Boolean" />
        </el-select>
      </template>
    </el-table-column>
    <el-table-column label="java属性" min-width="10%">
      <template v-slot="scope">
        <el-input v-model="scope.row.javaField"></el-input>
      </template>
    </el-table-column>

    <el-table-column label="编辑" min-width="5%">
      <template v-slot="scope">
        <el-checkbox true-label=true false-label=false v-model="scope.row.isInsert"></el-checkbox>
      </template>
    </el-table-column>
    <el-table-column label="列表" min-width="5%">
      <template v-slot="scope">
        <el-checkbox true-label=true false-label=false v-model="scope.row.isList"></el-checkbox>
      </template>
    </el-table-column>
    <el-table-column label="查询" min-width="5%">
      <template v-slot="scope">
        <el-checkbox true-label=true false-label=false v-model="scope.row.isQuery"></el-checkbox>
      </template>
    </el-table-column>
    <el-table-column label="查询方式" min-width="10%">
      <template v-slot="scope">
        <el-select v-model="scope.row.queryType">
          <el-option label="=" value="EQ" />
          <el-option label="!=" value="NE" />
          <el-option label=">" value="GT" />
          <el-option label=">=" value="GTE" />
          <el-option label="<" value="LT" />
          <el-option label="<=" value="LTE" />
          <el-option label="LIKE" value="LIKE" />
          <el-option label="BETWEEN" value="BETWEEN" />
        </el-select>
      </template>
    </el-table-column>
    <el-table-column label="必填" min-width="5%">
      <template v-slot="scope">
        <el-checkbox true-label=true false-label=false v-model="scope.row.isRequired"></el-checkbox>
      </template>
    </el-table-column>
    <el-table-column label="显示类型" min-width="12%">
      <template v-slot="scope">
        <el-select v-model="scope.row.htmlType">
          <el-option label="文本框" value="input" />
          <el-option label="文本域" value="textarea" />
          <el-option label="是否滑块" value="switch" />
          <el-option label="下拉框" value="select" />
          <el-option label="单选框" value="radio" />
          <el-option label="复选框" value="checkbox" />
          <el-option label="日期控件" value="datetime" />
          <el-option label="图片上传" value="imageUpload" />
          <el-option label="文件上传" value="fileUpload" />
          <el-option label="富文本控件" value="mdEditor" />
        </el-select>
      </template>
    </el-table-column>
    <el-table-column label="字典类型" min-width="12%">
      <template v-slot="scope">
        <el-select v-model="scope.row.dictType" filterable clearable placeholder="请选择">
          <el-option
              v-for="(value, key) in state.dictOptions"
              :key="key"
              :label="value"
              :value="key"
          />
        </el-select>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts" name="editTable">
import { reactive, onMounted } from 'vue';
import { useDictApi } from '/@/api/system/dict';

// 定义父组件传过来的值
const props = defineProps({
  columns: {
    type: Array,
    default: () => [],
  },
});

const useDict = useDictApi();

const state = reactive({
  dictOptions: {}
});

const initOption = () => {
  useDict.getDictTypeList().then(res => {
    state.dictOptions = res.reduce((acc, item) => {
      acc[item.dictCode] = item.dictName;
      return acc;
    }, {});
  });
}

// 页面加载时
onMounted(() => {
  initOption();
});
</script>