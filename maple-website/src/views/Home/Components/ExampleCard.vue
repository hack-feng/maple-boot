<script setup>
import { onMounted } from "vue";

// tooltip
import setTooltip from "@/assets/js/tooltip";

// store
import { useAppStore } from "@/stores";
const store = useAppStore();

defineProps({
  id: {
    type: Number,
    required: true,
  },
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
  description: {
    type: String,
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
  <a :href="'/category/'+ id">
    <div
      class="card move-on-hover"
      v-bind="$attrs"
      :title="description"
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
  </a>
</template>
