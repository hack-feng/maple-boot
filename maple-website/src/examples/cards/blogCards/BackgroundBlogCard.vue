<script setup>
defineProps({
  image: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    requierd: true,
  },
  description: {
    type: String,
    required: true,
  },
  action: {
    type: Object,
    route: String,
    label: String,
    color: String,
    default: () => ({
      route: "/",
      label: "点击前往",
      color: "white",
    }),
  },
});
</script>
<template>
  <div class="card card-blog card-background">
    <div
      class="full-background"
      :style="{ backgroundImage: `url(${image})` }"
      loading="lazy"
    ></div>
    <div class="card-body">
      <div class="text-start my-auto py-4">
        <h4 class="card-title text-white">{{ title }}</h4>
        <p class="card-description text-white text-sm">
          {{ description }}
        </p>
        <a
          :href="action.route"
          class="text-sm icon-move-right"
          :class="`text-${action.color}`"
          >{{ action.label }}
          <i class="fas fa-arrow-right text-xs ms-1"></i>
        </a>
      </div>
    </div>
  </div>
</template>
