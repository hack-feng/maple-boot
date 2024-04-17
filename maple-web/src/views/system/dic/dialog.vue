<template>
	<div class="system-dic-dialog-container">
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="85%">
      <el-collapse v-model="activeNames">
        <el-collapse-item title="字典基础信息" name="info">
          <el-form ref="dicDialogFormRef" :model="state.ruleForm" :rules="state.rules" size="default" label-width="90px">
            <el-row :gutter="35">
              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="字典名称" prop="dictName">
                  <el-input v-model="state.ruleForm.dictName" placeholder="请输入字典名称" clearable></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="字段编码" prop="dictCode">
                  <el-input v-model="state.ruleForm.dictCode" placeholder="请输入字段编码" clearable :disabled="state.dialog.type === 'edit'"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                <el-form-item label="角色状态" prop="status">
                  <el-select v-model="state.ruleForm.status" placeholder="请选择角色状态" clearable>
                    <el-option
                        v-for="dict in sys_status"
                        :key="Number(dict.value)"
                        :label="dict.label"
                        :value="Number(dict.value)"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-form-item label="字典描述" prop="remark">
                  <el-input v-model="state.ruleForm.remark" type="textarea" placeholder="请输入字典描述" maxlength="150"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
                <el-row :gutter="35" v-for="(v, k) in state.ruleForm.list" :key="k">
                  <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item :prop="`list[${k}].label`">
                      <template #label>
                        <el-button type="primary" circle size="small" @click="onAddRow" v-if="k === 0">
                          <el-icon>
                            <ele-Plus />
                          </el-icon>
                        </el-button>
                        <el-button type="danger" circle size="small" @click="onDelRow(k)" v-else>
                          <el-icon>
                            <ele-Delete />
                          </el-icon>
                        </el-button>
                        <span class="ml10">字段</span>
                      </template>
                      <el-input v-model="v.label" style="width: 100%" placeholder="请输入字段名"> </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
                    <el-form-item label="属性" :prop="`list[${k}].value`">
                      <el-input v-model="v.value" style="width: 100%" placeholder="请输入属性值"> </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-form>
        </el-collapse-item>
        <el-collapse-item title="字典明细信息" name="detail">
          <SubDicDialog ref="subDicDialogRef" :columns="state.ruleForm.dictDataModelList"/>
        </el-collapse-item>
      </el-collapse>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="onCancel" size="default">取 消</el-button>
					<el-button type="primary" @click="onSubmit" size="default">{{ state.dialog.submitTxt }}</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="dictDialog">
import {defineAsyncComponent, getCurrentInstance, nextTick, reactive, ref} from 'vue';
import { useDictApi } from '/@/api/system/dict';
import {ElMessage} from "element-plus";

// 获取字典
const { proxy } = getCurrentInstance();
const { sys_status } = proxy.parseDict("sys_status");

// 引入组件
const SubDicDialog = defineAsyncComponent(() => import('/@/views/system/dic/subDialog.vue'));

// 定义子组件向父组件传值/事件
const emit = defineEmits(['refresh']);

// 定义变量内容
const useDict = useDictApi();
const dicDialogFormRef = ref();
const subDicDialogRef = ref();
const activeNames = ref(['info', 'detail'])
const state = reactive({
	ruleForm: {
    dictName: '',
    dictCode: '',
    status: 1,
    dictDataModelList: [],
    remark: '',
	},
	dialog: {
		isShowDialog: false,
		type: '',
		title: '',
		submitTxt: '',
	},
  rules: {
    dictName: { required: true, message: '请输入字典名称', trigger: 'blur' },
    dictCode: { required: true, message: '请输入字段编码', trigger: 'blur' },
  },
});

// 打开弹窗
const openDialog = (type: string, row: RowDicType) => {
  resetForm();
	if (type === 'edit') {
    useDict.getDictById(row.id).then(res => {
      state.ruleForm = res;
      state.dialog.title = '修改字典';
      state.dialog.submitTxt = '修 改';
    });
	} else {
		state.dialog.title = '新增字典';
		state.dialog.submitTxt = '新 增';
	}
  state.dialog.type = type;
	state.dialog.isShowDialog = true;
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
  // 验证表单
  Promise.all([
    formRulesValidate(subDicDialogRef, 'subDicDialogRef'),
    currentValidate(dicDialogFormRef),
  ]).then(res => {
    const validateResult = res.every(item => !!item);
    if (validateResult) {
      if(state.dialog.type == 'add'){
        useDict.createDict(state.ruleForm).then(() => {
          ElMessage.success('创建成功');
          closeDialog();
          emit('refresh');
        });
      } else {
        useDict.updateDict(state.ruleForm).then(() => {
          ElMessage.success('修改成功');
          closeDialog();
          emit('refresh');
        });
      }
    } else {
      ElMessage.error("表单校验未通过，请重新检查提交内容");
    }
  });
};

// 主表-表单组件验证
const currentValidate = (pageRef: RefType) => {
  return new Promise((resolve) => {
    pageRef.value.validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};
// 子表-表单组件验证
const formRulesValidate = (pageRef: RefType, sonRef: string) => {
  return new Promise((resolve) => {
    pageRef.value.$refs[sonRef].validate((valid: boolean) => {
      if (valid) resolve(valid);
    });
  });
};

const resetForm = () => {
  state.ruleForm = {
    dictName: '',
    dictCode: '',
    status: 1,
    dictDataModelList: [],
    remark: '',
  }
}

// 暴露变量
defineExpose({
	openDialog,
});
</script>
