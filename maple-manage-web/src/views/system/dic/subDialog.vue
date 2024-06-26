<template>
  <div class="layout-pd">
    <el-form ref="subDicDialogRef" :model="state.tableData" size="default">
      <el-table ref="tableRef" :data="props.columns" border class="module-table-uncollected" max-height="800">
        <el-table-column
            v-for="(item, index) in state.tableData.header"
            :key="index"
            show-overflow-tooltip
            :prop="item.prop"
            :width="item.width"
            :label="item.label"
        >
          <template v-slot:header>
            <span v-if="item.isRequired" class="color-danger">*</span>
            <span class="pl5">{{ item.label }}</span>
            <el-tooltip v-if="item.isTooltip" effect="dark" content="这是tooltip" placement="top">
              <i class="iconfont icon-quanxian" />
            </el-tooltip>
          </template>
          <template v-slot="scope">
            <el-form-item
                :prop="`data.${scope.$index}.${item.prop}`"
                :rules="[{ required: item.isRequired, message: '不能为空', trigger: `${item.type}` == 'input' ? 'blur' : 'change' }]"
            >
              <el-select v-if="item.type === 'select'" v-model="scope.row[item.prop]" placeholder="请选择">
                <el-option v-for="sel in state.tableData.option" :key="sel.value" :label="sel.label" :value="sel.value" />
              </el-select>
              <el-date-picker
                  v-else-if="item.type === 'datetime'"
                  v-model="scope.row[item.prop]"
                  type="datetime"
                  placeholder="选择日期"
                  style="width: 100%"
              />
              <el-switch v-else-if="item.type === 'radio'" v-model="scope.row[item.prop]" inline-prompt active-text="是" inactive-text="否"></el-switch>
              <el-input v-else-if="item.type === 'input'" v-model="scope.row[item.prop]" placeholder="请输入内容" />
              <el-input v-else-if="item.type === 'dialog'" v-model="scope.row[item.prop]" readonly placeholder="请输入内容">
                <template v-slot:suffix>
                  <i class="iconfont icon-shouye_dongtaihui" />
                </template>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button size="small" text type="primary" @click="onRowDel(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <el-row class="flex mt15">
      <div class="flex-margin">
        <el-button size="default" type="success" @click="onValidate(subDicDialogRef)">表格验证</el-button>
        <el-button size="default" type="primary" @click="onAddRow">新增一行</el-button>
      </div>
    </el-row>
  </div>
</template>

<script setup lang="ts" name="subDictDialog">
import {reactive, ref, nextTick, watch} from 'vue';
import { ElMessage } from 'element-plus';
import type { FormInstance } from 'element-plus';

// 定义父组件传过来的值
const props = defineProps({
  columns: {
    type: Array,
    default: () => [],
  },
});

// 定义变量内容
const subDicDialogRef = ref<FormInstance>();
const tableRef = ref();
const state = reactive({
  tableData: {
    data: [],
    header: [
      { prop: 'dictLabel', width: '', label: '字典标签', isRequired: true, type: 'input' },
      { prop: 'dictValue', width: '', label: '字典键值', isRequired: true, type: 'input' },
      { prop: 'listClass', width: '', label: '表格回显样式', isRequired: true, type: 'select' },
      { prop: 'cssClass', width: '', label: '样式属性扩展', type: 'input' },
      { prop: 'isDefault', width: '', label: '是否默认', isRequired: true, type: 'radio' },
      { prop: 'status', width: '', label: '状态', type: 'radio' },
      { prop: 'dictSort', width: '', label: '排序', type: 'input' },
      { prop: 'remark', width: '', label: '备注', type: 'input' },
    ],
    option: [
      { value: 'primary', label: 'primary' },
      { value: 'success', label: 'success' },
      { value: 'info', label: 'info' },
      { value: 'warning', label: 'warning' },
      { value: 'danger', label: 'danger' },
    ],
  },
});

watch(() => props.columns, () => {
  if(!props.columns) {
    state.tableData.data = [];
  } else {
    state.tableData.data = props.columns;
  }
});


// 表格验证
const onValidate = (formEl: FormInstance | undefined) => {
  if (state.tableData.data <= 0) return ElMessage.warning('请先点击增加一行');
  if (!formEl) return;
  formEl.validate((valid) => {
    if (!valid) return ElMessage.warning('表格项必填未填');
    ElMessage.success('全部验证通过');
  });
};
// 新增一行
const onAddRow = () => {
  props.columns.push({
    dictLabel: '',
    dictValue: '',
    listClass: '',
    cssClass: '',
    isDefault: false,
    status: true,
    dictSort: '',
    remark: '',
  });
  tableRef.value.doLayout();
  nextTick(() => {
    tableRef.value.setScrollTop(1000000);
  });
  state.tableData.data = props.columns;
};

const onRowDel = (index) => {
  props.columns.splice(index, 1);
  state.tableData.data = props.columns;
}



</script>
