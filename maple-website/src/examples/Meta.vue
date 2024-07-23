<script setup>
import {onMounted} from "vue";
import {useMeta} from "vue-meta";

// 定义父组件传过来的值
const props = defineProps({
  webMenuInfo: {
    type: Object,
    default: () => {},
  }
});

onMounted(() => {
  useMeta({
    title: props.webMenuInfo.title + " - 笑小枫",
    meta: [
      {name: 'keywords', content: props.webMenuInfo.keywords},
      {name: 'description', content: props.webMenuInfo.keywords}
    ]
  });
});
</script>
<template>
</template>