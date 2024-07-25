<script setup>
import defaultImg from "@/assets/img/bg/b2.jpg";

defineProps({
  image: {
    type: String,
    default: defaultImg,
  },
  title: {
    type: [String, Object],
    default: "",
    text: String,
    variant: {
      validator(value) {
        return ["h1", "h2", "h3", "h4", "h5", "h6"].includes(value);
      },
    },
    class: {
      type: String,
      default: "",
    },
  },
  description: {
    type: [String, Object],
    default: { text:'', class:'' },
    text: String,
    class: String,
  },
  mask: {
    type: String,
    default: "mask bg-gradient-dark opacity-1",
  },
  center: {
    type: Boolean,
    default: true,
  },
  minHeight: {
    type: String,
    default: " min-height-400",
  },
  fullWidth: {
    type: Boolean,
    default: false,
  },
});
</script>
<template>
  <header class="header-2">
    <div
      class="page-header"
      :class="minHeight"
      :style="image ? `background-image: url(${image})`: `background-image: url(${defaultImg})`"
      loading="lazy"
      v-if="title"
    >
      <span :class="mask"></span>
      <div :class="fullWidth ? 'w-100' : 'container'">
        <div class="row">
          <div
            :class="`${$attrs.class ?? 'col-lg-7'} ${center ? 'text-center mx-auto position-relative' : ''}`"
          >
            <component
              :is="title.variant ? title.variant : 'h1'"
              class="text-white"
              :class="title.class"
            >
              {{ typeof title == "object" ? title.text : title }}
            </component>
            <p class="lead text-white" :class="!description ? '': description.class">
              {{
                description && typeof description == "object" ? description.text : description
              }}
            </p>
            <slot />
          </div>
        </div>
      </div>
    </div>
    <slot v-else />
  </header>
</template>
