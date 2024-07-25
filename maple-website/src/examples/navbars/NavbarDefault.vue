<script setup>
import { RouterLink } from "vue-router";
import {onMounted, reactive, ref, watch} from "vue";
import { useWindowsWidth } from "../../assets/js/useWindowsWidth";
import { getToken } from '@/utils/auth'
import { useRouter } from "vue-router";
import { getSiteConfig } from '@/stores/website';
import { getHeaderMenu } from '../../api/common';
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

const state = reactive({
  websiteName: '笑小枫',
  navbarList: [],
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

onMounted(() => {
  getSiteConfig("website_name").then(res => {
    state.websiteName = res;
  });

  getHeaderMenu().then(res => {
    state.navbarList = res;
  });
})
</script>
<template>
  <div class="container position-sticky z-index-sticky top-0 opacity-8">
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
          :to="{ name: 'home' }"
          rel="tooltip"
          title="Designed and Coded by Creative Tim"
          data-placement="bottom"
        >
          {{ state.websiteName }}
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
          {{ state.websiteName }}
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
              <span class="iconfont icon-sousuo" style="font-size: 0.7rem;"/>
            </button>
          </div>
          </ul>
          <ul class="navbar-nav navbar-nav-hover ms-auto">
            <li class="nav-item dropdown dropdown-hover mx-2"  v-for="navbar in state.navbarList">
              <div v-if="navbar.children.length === 0" class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()">
                <RouterLink v-if="!navbar.isLink"
                            :to="{ name: navbar.uiTemplate ? navbar.uiTemplate : navbar.path, params: { menu: navbar.path }  }"
                            class="dropdown-item border-radius-md"
                >
                  <span> {{ navbar.title }} </span>
                </RouterLink>
                <a
                    v-if="navbar.isLink"
                    :href="navbar.linkUrl"
                    class="dropdown-item py-2 ps-3 border-radius-md"
                    target="_blank"
                >{{ navbar.title }}
                </a>
              </div>
              
              <div v-if="navbar.children.length > 0">
                <a
                    role="button"
                    class="nav-link ps-2 d-flex cursor-pointer align-items-center"
                    :class="getTextColor()"
                    id="dropdownMenuPages"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                >
                  {{ navbar.title }}
                  <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-2 d-lg-block d-none"/>
                  <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-1 d-lg-none d-block ms-auto"/>
                </a>
                <div
                    class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3"
                    aria-labelledby="dropdownMenuPages"
                >
                  <div class="row d-none d-lg-block" v-for="navbarChildren in navbar.children">
                    <div class="col-12 px-4 py-1">
                      <div class="row">
                        <div v-if="navbarChildren.children.length === 0" class="dropdown-header d-flex align-items-center px-0">
                          <RouterLink v-if="!navbarChildren.isLink"
                                      :to="{ name: navbarChildren.uiTemplate ? navbarChildren.uiTemplate : navbarChildren.path, params: { menu: navbarChildren.path }  }"
                                      class="dropdown-item border-radius-md"
                          >
                            <span class="text-dark font-weight-bolder"> {{ navbarChildren.title }} </span>
                          </RouterLink>
                          <a
                              v-if="navbarChildren.isLink"
                              :href="navbarChildren.linkUrl"
                              class="dropdown-item py-2 ps-3 border-radius-md text-dark font-weight-bolder"
                              target="_blank"
                          >{{ navbarChildren.title }}
                          </a>
                        </div>
                        
                        <div v-if="navbarChildren.children.length > 0" class="position-relative">
                          <div class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1">
                            {{ navbarChildren.title }}
                          </div>
                          <div v-for="navbarGrandchildren in navbarChildren.children">
                            <RouterLink v-if="!navbarGrandchildren.isLink"
                                        :to="{ name: navbarGrandchildren.uiTemplate ? navbarGrandchildren.uiTemplate : navbarGrandchildren.path, params: { menu: navbarGrandchildren.path }  }"
                                        class="dropdown-item border-radius-md"
                            >
                              <span> {{ navbarGrandchildren.title }} </span>
                            </RouterLink>
                            <a 
                                v-if="navbarGrandchildren.isLink" 
                                :href="navbarGrandchildren.linkUrl"  
                                class="dropdown-item py-2 ps-3 border-radius-md" 
                                target="_blank"
                            >{{ navbarGrandchildren.title }}
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="d-lg-none" v-for="navbarChildren in navbar.children">
                    <div v-if="navbarChildren.children.length === 0" class="dropdown-header d-flex align-items-center px-0">
                      <RouterLink v-if="!navbarChildren.isLink"
                                  :to="{ name: navbarChildren.uiTemplate ? navbarChildren.uiTemplate : navbarChildren.path, params: { menu: navbarChildren.path }  }"
                                  class="border-radius-md"
                      >
                        <span class="text-dark font-weight-bolder">
                          {{ navbarChildren.title }}
                        </span>
                      </RouterLink>
                      <a
                          v-if="navbarChildren.isLink"
                          :href="navbarChildren.linkUrl"
                          class="py-2 border-radius-md text-dark font-weight-bolder"
                          target="_blank"
                      >{{ navbarChildren.title }}
                      </a>
                    </div>
                    <div v-if="navbarChildren.children.length > 0">
                      <div class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-0">
                        {{ navbarChildren.title }}
                      </div>
                      <div v-for="navbarGrandchildren in navbarChildren.children">
                        <RouterLink v-if="!navbarGrandchildren.isLink"
                                    :to="{ name: navbarGrandchildren.uiTemplate ? navbarGrandchildren.uiTemplate : navbarGrandchildren.path, params: { menu: navbarGrandchildren.path }  }"
                                    class="dropdown-item border-radius-md"
                        >
                          <span>{{ navbarGrandchildren.title }}</span>
                        </RouterLink>
                        <a
                            v-if="navbarGrandchildren.isLink"
                            :href="navbarGrandchildren.linkUrl"
                            class="dropdown-item py-2 ps-3 border-radius-md"
                            target="_blank"
                        >{{ navbarGrandchildren.title }}
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
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
  </div>
  <!-- End Navbar -->
</template>
