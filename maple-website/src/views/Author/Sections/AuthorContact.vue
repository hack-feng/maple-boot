<script setup>
import { onMounted,ref } from "vue";
//Maple Blog components
import MaterialButton from "@/components/MaterialButton.vue";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialTextArea from "@/components/MaterialTextArea.vue";

// image
import bgContact from "@/assets/img/examples/blog2.jpg";
import wxgzh from "@/assets/img/wxgzh.png";
import applet from "@/assets/img/applet.jpg";

// tooltip
import setTooltip from "@/assets/js/tooltip";

// store
import { useAppStore } from "@/stores";
const store = useAppStore();

const ocials= ref([
      {
        icon: '<i class="iconfont icon-shang" style="color: #ef990b; font-size: 2rem;"></i>',
        link: "/article/1038"
      },
      {
        icon: '<i class="iconfont icon-csdn" style="color: red; font-size: 2rem;"></i>',
        link: "https://zhangfz.blog.csdn.net/"
      },
      {
        icon: '<i class="iconfont icon-github" style="color: #000000; font-size: 2rem;"></i>',
        link: "https://github.com/hack-feng"
      },
      {
        icon: '<i class="iconfont icon-weixin" style="color: #0abc64; font-size: 2rem;"></i>',
        link: "https://github.com/creativetimofficial"
      },
      {
        icon: '<i class="iconfont icon-shejiaotubiao-46" style="color: #49c0fb; font-size: 2rem;"></i>',
        link: "https://www.zhihu.com/people/xiaofengJava"
      }
    ]
);

onMounted(() => {
  setTooltip(store.bootstrap);
});
</script>
<template>
  <section class="py-lg-5">
    <div class="container">
      <div class="row">
        <div class="col">
          <div class="card box-shadow-xl overflow-hidden mb-4">
            <div class="row">
              <div
                class="col-lg-5 position-relative bg-cover px-0"
                :style="{ backgroundImage: `url(${bgContact})` }"
                loading="lazy"
              >
                <div
                  class="z-index-2 text-center d-flex h-100 w-100 d-flex m-auto justify-content-center"
                >
                  <div class="mask bg-gradient-dark opacity-8"></div>
                  <div
                    class="p-5 ps-sm-8 position-relative text-start my-auto z-index-2"
                  >
                    <h3 class="text-white">笑小枫的信息</h3>
                    <p class="text-white opacity-8 mb-4">
                      欢迎各种商务合作<br>
                      广告投放、相互推广、系统开发、技术指导、面试指导皆可联系我<br>
                      如遇见任何问题，可以随时联系笑小枫哟，也欢迎小伙伴加我好友<br>
                      部分资源文章内容来自网络，如有侵犯您的权益，请联系我删除<br>
                    </p>
                    <div class="d-flex p-2 text-white">
                      <div>
                        <i class="iconfont icon-weixin"></i>
                      </div>
                      <div class="ps-3">
                        <span class="text-sm opacity-8">xiaoxiaofeng910</span>
                      </div>
                    </div>
                    <div class="d-flex p-2 text-white">
                      <div>
                        <i class="fas fa-envelope text-sm"></i>
                      </div>
                      <div class="ps-3">
                        <span class="text-sm opacity-8"
                          >1150640979@qq.com</span
                        >
                      </div>
                    </div>
                    <div class="d-flex p-2 text-white">
                      <div>
                        <i class="fas fa-map-marker-alt text-sm"></i>
                      </div>
                      <div class="ps-3">
                        <span class="text-sm opacity-8"
                          >上海市浦东新区</span
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-3 mx-5">
                <form id="contact-form" method="post">
                  <div class="card-header px-4 py-sm-5 py-3">
                    <h4>《笑小枫》微信公众号</h4>
                    <img :src="wxgzh"
                        alt="图片去找笑小枫啦"
                        class="shadow border-radius-lg mt-3"
                        loading="lazy"
                        style="width: 260px;"
                    />
                  </div>
                  <div class="card-body pt-1">
                    <div class="row">
                      
                    </div>
                  </div>
                </form>
              </div>
              <div class="col-lg-3">
                <form id="contact-form" method="post">
                  <div class="card-header px-4 py-sm-5 py-3">
                    <h4>《笑小枫》小程序</h4>
                    <img :src="applet"
                        alt="图片去找笑小枫啦"
                        class="shadow border-radius-lg mt-3"
                        loading="lazy"
                        style="width: 260px;"
                    />
                  </div>
                  <div class="card-body pt-1">
                    <div class="row">
                      
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
