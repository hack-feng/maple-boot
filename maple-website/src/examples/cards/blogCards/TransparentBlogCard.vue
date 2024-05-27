<script setup>
defineProps({
  image: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  action: {
    type: Object,
    route: String,
    color: String,
    label: String,
    default: () => ({
      route: "javascript:;",
      color: "success",
      label: "前往阅读",
    }),
  },
});
</script>
<template>
  <div class="card card-plain">
    <div class="card-header p-0 position-relative ">
      <a class="d-block blur-shadow-image">
        <img
          :src="image"
          :alt="title"
          class="shadow border-radius-lg img-160"
          loading="lazy"
        />
      </a>
    </div>
    <div class="card-body px-0">
      <h5 class="more-omit-1">
        <a :href="action.route" class="text-dark font-weight-bold" target="_blank">{{
          title
        }}</a>
      </h5>
      <p class="more-omit-3 text-sm">
        {{ description === null || description === undefined ? "连点介绍都没有，直接去看详情吧" : description}}
      </p>
      <a
        :href="action.route"
        class="text-sm icon-move-right"
        :class="`text-${action.color}`"
        target="_blank"
        >{{ action.label }}
        <i class="fas fa-arrow-right text-xs ms-1"></i>
      </a>
    </div>
  </div>
</template>
