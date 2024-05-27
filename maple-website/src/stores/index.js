import { reactive, readonly } from "vue";
import { getUserInfo, logout } from "../api/login";
import { setToken, removeToken } from "../utils/auth";

import { defineStore } from "pinia";
import bootstrap from "bootstrap/dist/js/bootstrap.min.js";
export const useAppStore = defineStore("storeId", {
  state: () => ({
    bootstrap,
  }),
});

// 创建默认的全局单例响应式数据，仅供该模块内部使用
const state = reactive({ user: null, isLogin: false, loading: false });

// 对外暴露的数据是只读的，不能直接修改
// 也可以进一步使用toRefs进行封装，从而避免解构或展开后响应式丢失
export const loginUserStore = readonly(state);


// 登录
export async function login(token) {
  setToken(token)
  state.loading = true;
  getUserInfo.then(res => {
    state.user = res;
    this.state.isLogin = true;
  });
  state.loading = false;
}

// 退出
export async function loginOut() {
  state.loading = true;
  logout();  
  state.loading = false;
  state.user = null;
  this.state.isLogin = false;
  removeToken();
}

