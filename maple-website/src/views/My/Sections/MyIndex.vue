<script setup>
import My from "./My.vue";
import MyEdit from "./MyEdit.vue";
import Message from "./Message.vue";
import Collect from "./Collect.vue";
import Download from "./Download.vue";
import Read from "./Read.vue";

</script>
<template>
  <div class="card card-body blur shadow-blur mx-md-4 mt-n6">
    <section>
      <div class="container">
        <el-tabs tab-position="left">
          <el-tab-pane label="我的信息"><My/></el-tab-pane>
          <el-tab-pane label="资料修改"><MyEdit/></el-tab-pane>
          <el-tab-pane label="消息中心"><Message/></el-tab-pane>
          <el-tab-pane label="我的收藏"><Collect/></el-tab-pane>
          <el-tab-pane label="我的下载"><Download/></el-tab-pane>
          <el-tab-pane label="浏览记录"><Read/></el-tab-pane>
        </el-tabs>
      </div>
    </section>
  </div>
</template>

<style scoped>
.el-tab-pane {
  height: calc(100vh - 100px);
  overflow-y: auto;
}

</style>

