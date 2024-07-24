<script setup>
import {onMounted, ref} from "vue";
import setMaterialInput from "@/assets/js/material-input";

import { getUserInfo } from "@/api/login";

onMounted(() => {
  setMaterialInput();
  getUserInfoClick();
});

const userInfo = ref({});

const getUserInfoClick = () => {
  getUserInfo().then(res => {
    userInfo.value = res;
  });
}

</script>

<template>
  <section>
    <div class="container">
      <div class="row mt-5 text-center">
        <div class="row">
          <a href="javascript:;">
            <img
                class="avatar avatar-xxl"
                :src="userInfo.headPortraitLink === null ? 'https://image.xiaoxiaofeng.site/blog/image/noHead.jpg?xiaoxiaofeng' : userInfo.headPortraitLink"
                alt="头像呢？"
                style="background-color: rgb(175 150 130);"
            />
          </a>
          <span>账号：{{ userInfo.userNo }}</span>
          <span>昵称：{{ userInfo.nickName }}</span>
          <span>性别：{{ userInfo.sex === 1 ? "男" : userInfo.sex === 2 ? "女" : "保密" }}</span>
          <span>邮箱：{{ userInfo.email === null ? "无" : userInfo.email }}</span>
          <span>个人简介：{{ userInfo.userDesc === null ? "没有个人简介哟~" : userInfo.userDesc }}</span>
        </div>
      </div>
    </div>
  </section>
</template>