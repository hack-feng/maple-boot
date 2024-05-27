<template>
  <span :class="['iconfont', `${name}`]"></span>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';

defineProps({
  name: {
    type: String ,
    required: true,
  }
});
</script>

<style scoped>

</style>