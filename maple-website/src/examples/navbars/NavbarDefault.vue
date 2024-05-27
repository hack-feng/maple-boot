<script setup>
import { RouterLink } from "vue-router";
import { ref, watch } from "vue";
import { useWindowsWidth } from "../../assets/js/useWindowsWidth";
import { getToken } from '@/utils/auth'
import { useRouter } from "vue-router";

// images
import ArrDark from "@/assets/img/down-arrow-dark.svg";
import downArrow from "@/assets/img/down-arrow.svg";
import DownArrWhite from "@/assets/img/down-arrow-white.svg";

const router = useRouter()

const props = defineProps({
  action: {
    type: Object,
    route: String,
    color: String,
    label: String,
    default: () => ({
      route: getToken() == undefined ? '/login' : '/my',
      color: "bg-gradient-success",
      label: getToken() == undefined ? '前往登录' : '个人中心'
    })
  },
  transparent: {
    type: Boolean,
    default: false
  },
  light: {
    type: Boolean,
    default: false
  },
  dark: {
    type: Boolean,
    default: false
  },
  sticky: {
    type: Boolean,
    default: false
  },
  darkText: {
    type: Boolean,
    default: false
  }
});

// set arrow  color
function getArrowColor() {
  if (props.transparent && textDark.value) {
    return ArrDark;
  } else if (props.transparent) {
    return DownArrWhite;
  } else {
    return ArrDark;
  }
}

// set text color
const getTextColor = () => {
  let color;
  if (props.transparent && textDark.value) {
    color = "text-dark";
  } else if (props.transparent) {
    color = "text-white";
  } else {
    color = "text-dark";
  }

  return color;
};

// set nav color on mobile && desktop

let textDark = ref(props.darkText);
const { type } = useWindowsWidth();

if (type.value === "mobile") {
  textDark.value = true;
} else if (type.value === "desktop" && textDark.value == false) {
  textDark.value = false;
}

watch(
  () => type.value,
  (newValue) => {
    if (newValue === "mobile") {
      textDark.value = true;
    } else {
      textDark.value = false;
    }
  }
);

let searchParam = ref(undefined);

const searchClick = () => {
  if(searchParam.value !== undefined){
    router.push("/search?title=" + searchParam.value);
  }
}
</script>
<template>
  <nav
    class="navbar navbar-expand-lg top-0"
    :class="{
      'z-index-3 w-100 shadow-none navbar-transparent position-absolute my-3':
        props.transparent,
      'my-3 blur border-radius-lg z-index-3 py-2 shadow py-2 start-0 end-0 mx-4 position-absolute mt-4':
        props.sticky,
      'navbar-light bg-white': props.light,
      ' navbar-dark bg-gradient-dark z-index-3 py-3': props.dark
    }"
  >
    <div
      :class="
        props.transparent || props.light || props.dark
          ? 'container'
          : 'container-fluid px-0'
      "
    >
      <RouterLink
        class="navbar-brand d-none d-md-block"
        :class="[
          (props.transparent && textDark.value) || !props.transparent
            ? 'text-dark font-weight-bolder ms-sm-3'
            : 'text-white font-weight-bolder ms-sm-3'
        ]"
        :to="{ name: 'presentation' }"
        rel="tooltip"
        title="Designed and Coded by Creative Tim"
        data-placement="bottom"
      >
        笑小枫
      </RouterLink>
      <RouterLink
        class="navbar-brand d-block d-md-none"
        :class="
          props.transparent || props.dark
            ? 'text-white'
            : 'font-weight-bolder ms-sm-3'
        "
        to="/"
        rel="tooltip"
        title="Designed and Coded by Creative Tim"
        data-placement="bottom"
      >
        笑小枫
      </RouterLink>
      <a
        :href="action.route"
        class="btn btn-sm bg-gradient-success mb-0 ms-auto d-lg-none d-block"
        >{{ action.label }}</a
      >
      <button
        class="navbar-toggler shadow-none ms-2"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navigation"
        aria-controls="navigation"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      
      <div
        class="collapse navbar-collapse w-100 pt-3 pb-2 py-lg-0"
        id="navigation"
      >
        <ul class="navbar-nav ms-auto">
          <div class="d-flex cursor-pointer align-items-center">
          <el-input 
            v-model="searchParam" 
            placeholder="请输入关键词" 
            maxlength="30"
            style="width: 300px"/>
          <button class="btn bg-gradient-success btn-sm mb-0 mx-1" v-on:click="searchClick(undefined)">
            <i class="fas fa-search"></i>
          </button>
        </div>
        </ul>
        <ul class="navbar-nav navbar-nav-hover ms-auto">
          <li class="nav-item dropdown dropdown-hover mx-2">
            <div class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
              <RouterLink
                  to="/"
                  class="dropdown-item border-radius-md"
              >
                <span>
                  首页
                </span>
              </RouterLink>
            </div>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <div class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
                <RouterLink
                  :to="{ name: 'blog' }"
                  class="dropdown-item border-radius-md"
                >
                  <span>小枫博客录</span>
              </RouterLink>
            </div>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <div class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
              <RouterLink
                  :to="{ name: 'download' }"
                  class="dropdown-item border-radius-md"
              >
                <span>小枫资源库</span>
              </RouterLink>
            </div>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <div class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
              <RouterLink
                  :to="{ name: 'links' }"
                  class="dropdown-item border-radius-md"
              >
                <span>小枫链接集</span>
              </RouterLink>
            </div>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <div class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
              <RouterLink
                  :to="{ name: 'author' }"
                  class="dropdown-item border-radius-md"
              >
                <span>关于笑小枫</span>
              </RouterLink>
            </div>
          </li>
        </ul>
        <ul class="navbar-nav d-lg-block d-none">
          <li class="nav-item">
            <a
              :href="action.route"
              class="btn btn-sm mb-0"
              :class="action.color"
              onclick="smoothToPricing('pricing-soft-ui')"
              >{{ action.label }}</a
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
</template>
