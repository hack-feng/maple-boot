<template>
	<div class="gen-basic-dialog-container">
		<el-form :model="props.data" :rules="state.rules" ref="basicInfoFormRef" size="default" label-width="120px">
			<el-row :gutter="35">
				<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
					<el-form-item label="表名称" prop="tableName">
						<el-input v-model="props.data.tableName" placeholder="请输入表名称" clearable></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
					<el-form-item label="表描述" prop="tableComment">
						<el-input v-model="props.data.tableComment" placeholder="请输入表描述" clearable></el-input>
					</el-form-item>
				</el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="实体类名称" prop="className">
            <el-input v-model="props.data.className" placeholder="请输入实体类名称" clearable></el-input>
          </el-form-item>
        </el-col>
				<el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
					<el-form-item label="作者" prop="functionAuthor">
						<el-input v-model="props.data.functionAuthor" placeholder="请输入作者" clearable></el-input>
					</el-form-item>
				</el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item label="备注" prop="autograph">
            <el-input type="textarea" :rows="3"  v-model="props.data.autograph" placeholder="请输入备注" clearable></el-input>
          </el-form-item>
        </el-col>
			</el-row>
		</el-form>
	</div>
</template>

<script setup lang="ts" name="basicInfoForm">
import { reactive } from 'vue';

// 定义父组件传过来的值
const props = defineProps({
  data: {
    type: Object,
    default: () => {},
  },
});

// 定义变量内容
const state = reactive({
	rules: {
    tableName: { required: true, message: '请输入表名称', trigger: 'blur' },
    tableComment: { required: true, message: '请输入表描述', trigger: 'blur' },
    className: { required: true, message: '请输入实体类名称', trigger: 'blur' },
    functionAuthor: { required: true, message: '请选择作者', trigger: 'blur' },
	},
});
</script>
