<script setup>
import { onMounted } from "vue";

// tooltip
import setTooltip from "@/assets/js/tooltip";

// store
import { useAppStore } from "@/stores";
const store = useAppStore();

defineProps({
  route: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    default: "",
  },
  subtitle: {
    type: String,
    default: "",
  },
  pro: {
    type: Boolean,
    default: false,
  },
});

onMounted(() => {
  setTooltip(store.bootstrap);
});
</script>
<script>
export default {
  inheritAttrs: false,
};
</script>
<template>
  <RouterLink :to="{ name: route }">
    <div
      class="card move-on-hover"
      v-bind="$attrs"
      :data-bs-toggle="pro ? 'tooltip' : null"
      :data-bs-placement="pro ? 'top' : null"
      :title="pro ? '小枫精选' : null"
    >
      <img
        class="img-160"
        :src="image"
        :alt="title"
        loading="lazy"
      />
    </div>
    <div class="mt-2 ms-2">
      <h6 class="mb-0">{{ title }}</h6>
      <p class="text-secondary text-sm font-weight-normal">
        {{ subtitle }}
      </p>
    </div>
  </RouterLink>
</template>
