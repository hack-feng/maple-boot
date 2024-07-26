<template>
  <div class="personal layout-pd">
    <el-row>
      <!-- 个人信息 -->
      <el-col :xs="24" :sm="16">
        <el-card shadow="hover" header="个人信息">
          <div class="personal-user">
            <div class="personal-user-left">
              <el-upload class="h100 personal-user-left-upload" multiple :limit="1">
                <img src="https://image.xiaoxiaofeng.site/blog/2024/04/26/xxf-20240426111323.png?xxfjava" />
              </el-upload>
            </div>
            <div class="personal-user-right">
              <el-row>
                <el-col :span="24" class="personal-title mb18">欢迎来到 Maple Boot，简单编程，快速搭建管理系统，从这里开始！ </el-col>
                <el-col :span="24">
                  <el-row>
                    <el-col :xs="24" :sm="8" class="personal-item mb6">
                      <div class="personal-item-label">昵称：</div>
                      <div class="personal-item-value">笑小枫</div>
                    </el-col>
                    <el-col :xs="24" :sm="16" class="personal-item mb6">
                      <div class="personal-item-label">身份：</div>
                      <div class="personal-item-value">超级管理</div>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="24">
                  <el-row>
                    <el-col :xs="24" :sm="8" class="personal-item mb6">
                      <div class="personal-item-label">登录IP：</div>
                      <div class="personal-item-value">192.168.1.1</div>
                    </el-col>
                    <el-col :xs="24" :sm="16" class="personal-item mb6">
                      <div class="personal-item-label">登录时间：</div>
                      <div class="personal-item-value">2024-03-26 18:47:26</div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 消息通知 -->
      <el-col :xs="24" :sm="8" class="pl15 personal-info">
        <el-card shadow="hover">
          <template #header>
            <span>消息通知</span>
            <span class="personal-info-more">更多</span>
          </template>
          <div class="personal-info-box">
            <ul class="personal-info-ul">
              <li v-for="(v, k) in state.newsInfoList" :key="k" class="personal-info-li">
                <a :href="v.link" target="_block" class="personal-info-li-title">{{ v.title }}</a>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <!-- 营销推荐 -->
      <el-col :span="24">
        <el-card shadow="hover" class="mt15" header="推荐中心">
          <el-row :gutter="15" class="personal-recommend-row">
            <el-col :sm="6" v-for="(v, k) in state.recommendList" :key="k" class="personal-recommend-col">
              <div class="personal-recommend" :style="{ 'background-color': v.bg }">
                <SvgIcon :name="v.icon" :size="70" :style="{ color: v.iconColor }" />
                <div class="personal-recommend-auto">
                  <div>{{ v.title }}</div>
                  <div class="personal-recommend-msg"><a :href="v.href" target="_blank" class="personal-recommend-msg">{{ v.msg }}</a></div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <!-- 赞赏我们 -->
      <el-col :span="24">
        <el-card shadow="hover" class="mt15" header="笑小枫周边">
          <el-row :gutter="15">
            <el-col :sm="6" v-for="(v, k) in state.sponsorList" :key="k">
              <div class="sponsor">
                <img class="sponsor-img" :src="v.img" /><br>
                <span>{{v.title}}</span>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script setup lang="ts" name="home">
import { reactive } from 'vue';
import { storeToRefs } from 'pinia';
import { useThemeConfig } from '/@/stores/themeConfig';
import { useTagsViewRoutes } from '/@/stores/tagsViewRoutes';

// 定义变量内容
const storesTagsViewRoutes = useTagsViewRoutes();
const storesThemeConfig = useThemeConfig();
const { themeConfig } = storeToRefs(storesThemeConfig);
const { isTagsViewCurrenFull } = storeToRefs(storesTagsViewRoutes);
const state = reactive({
  recommendList: [
    {
      title: '代码仓库',
      msg: 'Github、Gitee仓库同步更新，只需切换域名即可，点击立即前往',
      href: 'https://github.com/hack-feng/maple-boot',
      icon: 'ele-ShoppingCart',
      bg: '#F95959',
      iconColor: '#F86C6B',
    },
    {
      title: '项目文档',
      msg: '如果使用项目，点击查看文档，上手简单',
      href: 'https://www.xiaoxiaofeng.com/category/49',
      icon: 'ele-Food',
      bg: '#48D18D',
      iconColor: '#64d89d',
    },
    {
      title: '更多体验',
      msg: '前往笑小枫官网首页，更多项目体验',
      href: 'https://www.xiaoxiaofeng.com/',
      icon: 'ele-AlarmClock',
      bg: '#FEBB50',
      iconColor: '#FDC566',
    },
    {
      title: '联系我们',
      msg: '添加作者微信：q1150640979',
      href: 'https://www.xiaoxiaofeng.com/author',
      icon: 'ele-School',
      bg: '#8595F4',
      iconColor: '#92A1F4',
    },
  ],
  sponsorList: [
    {
      title: 'Java面试助手',
      img: 'https://image.xiaoxiaofeng.site/blog/2024/07/26/xxf-20240726093828.jpeg?xxfjava',
    },
    {
      title: '微信公众号',
      img: 'https://image.xiaoxiaofeng.site/blog/2024/07/26/xxf-20240726093828.png?xxfjava',
    },
    {
      title: '微信赞赏',
      img: 'https://image.xiaoxiaofeng.site/blog/2024/06/11/xxf-20240611134313.png?xxfjava',
    },
    {
      title: '支付宝赞赏',
      img: 'https://image.xiaoxiaofeng.site/blog/2024/06/11/xxf-20240611134311.png?xxfjava',
    },
  ],
  personalForm: {
    name: '',
    email: '',
    autograph: '',
    occupation: '',
    phone: '',
    sex: '',
  },
	global: {
		dispose: [null, '', undefined],
	} as any,
  newsInfoList:[
    {
      title: '笑小枫网站端即将上线，风格同《笑小枫》，点击抢先预览~',
      link: 'https://xiaoxiaofeng.com'
    },
    {
      title: '笑小枫Java面试助手微信小程序已上线，下方扫码即刻体验~',
      link: ''
    },
    {
      title: '笑小枫基础管理端框架已上线，欢迎体验~',
      link: 'https://maple.xiaoxiaofeng.com'
    }
  ]
});

</script>

<style scoped lang="scss">
$homeNavLengh: 8;
.home-container {
	overflow: hidden;
	.home-card-one,
	.home-card-two,
	.home-card-three {
		.home-card-item {
			width: 100%;
			height: 130px;
			border-radius: 4px;
			transition: all ease 0.3s;
			padding: 20px;
			overflow: hidden;
			background: var(--el-color-white);
			color: var(--el-text-color-primary);
			border: 1px solid var(--next-border-color-light);
			&:hover {
				box-shadow: 0 2px 12px var(--next-color-dark-hover);
				transition: all ease 0.3s;
			}
			&-icon {
				width: 70px;
				height: 70px;
				border-radius: 100%;
				flex-shrink: 1;
				i {
					color: var(--el-text-color-placeholder);
				}
			}
			&-title {
				font-size: 15px;
				font-weight: bold;
				height: 30px;
			}
		}
	}
	.home-card-one {
		@for $i from 0 through 3 {
			.home-one-animation#{$i} {
				opacity: 0;
				animation-name: error-num;
				animation-duration: 0.5s;
				animation-fill-mode: forwards;
				animation-delay: calc($i/4) + s;
			}
		}
	}
	.home-card-two,
	.home-card-three {
		.home-card-item {
			height: 400px;
			width: 100%;
			overflow: hidden;
			.home-monitor {
				height: 100%;
				.flex-warp-item {
					width: 25%;
					height: 111px;
					display: flex;
					.flex-warp-item-box {
						margin: auto;
						text-align: center;
						color: var(--el-text-color-primary);
						display: flex;
						border-radius: 5px;
						background: var(--next-bg-color);
						cursor: pointer;
						transition: all 0.3s ease;
						&:hover {
							background: var(--el-color-primary-light-9);
							transition: all 0.3s ease;
						}
					}
					@for $i from 0 through $homeNavLengh {
						.home-animation#{$i} {
							opacity: 0;
							animation-name: error-num;
							animation-duration: 0.5s;
							animation-fill-mode: forwards;
							animation-delay: calc($i/10) + s;
						}
					}
				}
			}
		}
	}
}

@import '../../theme/mixins/index.scss';
.personal {
  .personal-user {
    height: 130px;
    display: flex;
    align-items: center;
    .personal-user-left {
      width: 100px;
      height: 130px;
      border-radius: 3px;
      :deep(.el-upload) {
        height: 100%;
      }
      .personal-user-left-upload {
        img {
          width: 100%;
          height: 100%;
          border-radius: 3px;
        }
        &:hover {
          img {
            animation: logoAnimation 0.3s ease-in-out;
          }
        }
      }
    }
    .personal-user-right {
      flex: 1;
      padding: 0 15px;
      .personal-title {
        font-size: 18px;
        @include text-ellipsis(1);
      }
      .personal-item {
        display: flex;
        align-items: center;
        font-size: 13px;
        .personal-item-label {
          color: var(--el-text-color-secondary);
          @include text-ellipsis(1);
        }
        .personal-item-value {
          @include text-ellipsis(1);
        }
      }
    }
  }
  .personal-info {
    .personal-info-more {
      float: right;
      color: var(--el-text-color-secondary);
      font-size: 13px;
      &:hover {
        color: var(--el-color-primary);
        cursor: pointer;
      }
    }
    .personal-info-box {
      height: 130px;
      overflow: hidden;
      .personal-info-ul {
        list-style: none;
        .personal-info-li {
          font-size: 13px;
          padding-bottom: 10px;
          .personal-info-li-title {
            display: inline-block;
            @include text-ellipsis(1);
            color: var(--el-text-color-secondary);
            text-decoration: none;
          }
          & a:hover {
            color: var(--el-color-primary);
            cursor: pointer;
          }
        }
      }
    }
  }
  .personal-recommend-row {
    .personal-recommend-col {
      .personal-recommend {
        position: relative;
        height: 100px;
        border-radius: 3px;
        overflow: hidden;
        cursor: pointer;
        &:hover {
          i {
            right: 0px !important;
            bottom: 0px !important;
            transition: all ease 0.3s;
          }
        }
        i {
          position: absolute;
          right: -10px;
          bottom: -10px;
          font-size: 70px;
          transform: rotate(-30deg);
          transition: all ease 0.3s;
        }
        .personal-recommend-auto {
          padding: 15px;
          position: absolute;
          left: 0;
          top: 5%;
          color: var(--next-color-white);
          .personal-recommend-msg {
            font-size: 12px;
            margin-top: 10px;
            a {
              color: var(--next-color-white);
              text-decoration: none;
            }
            a:hover {
              color: #ad6517;
            }
          }
        }
      }
    }
  }
  .personal-edit {
    .personal-edit-title {
      position: relative;
      padding-left: 10px;
      color: var(--el-text-color-regular);
      &::after {
        content: '';
        width: 2px;
        height: 10px;
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        background: var(--el-color-primary);
      }
    }
    .personal-edit-safe-box {
      border-bottom: 1px solid var(--el-border-color-light, #ebeef5);
      padding: 15px 0;
      .personal-edit-safe-item {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .personal-edit-safe-item-left {
          flex: 1;
          overflow: hidden;
          .personal-edit-safe-item-left-label {
            color: var(--el-text-color-regular);
            margin-bottom: 5px;
          }
          .personal-edit-safe-item-left-value {
            color: var(--el-text-color-secondary);
            @include text-ellipsis(1);
            margin-right: 15px;
          }
        }
      }
      &:last-of-type {
        padding-bottom: 0;
        border-bottom: none;
      }
    }
  }
}

.sponsor {
  text-align: center;
  .sponsor-img{
    height: 300px;
    width: auto;
  }
}
</style>
