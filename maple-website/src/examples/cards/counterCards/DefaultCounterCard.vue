<script setup>
// 使用
import CountTo from "vue-count-to/src";
import { RouterLink } from "vue-router";

defineProps({
  link: {
    type: String,
    default: "javascript: ;",
  },
  count: {
    type: Number,
    required: true,
  },
  suffix: {
    type: String,
    default: "",
  },
  duration: {
    type: Number,
    default: 4000,
  },
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  color: {
    validator(value) {
      return [
        "primary",
        "secondary",
        "info",
        "success",
        "warning",
        "error",
        "light",
        "dark",
      ].includes(value);
    },
  },
  divider: {
    validator(value) {
      return ["vertical", "horizontal"].includes(value);
    },
  },
});
</script>
<template>
  <div class="text-center">
    <RouterLink :to="link">
      <h1 :class="`text-gradient text-${color ?? 'success'}`">
      <CountTo :start-val="0" :end-val="count" :duration="duration" />{{
        suffix
      }}
      </h1>
      <h5 class="mt-3">{{ title }}</h5>
      <p class="text-sm font-weight-normal">
        {{ description }}
      </p>
    </RouterLink>
  </div>
  <hr :class="`${divider ? divider : ''} dark`" />
</template>
