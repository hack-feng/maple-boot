import { createRouter, createWebHistory } from "vue-router";




const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import('../views/Home/HomeView.vue'),
    },
    {
      path: "/author",
      name: "author",
      component: () => import('../views/Author/AuthorView.vue'),
    },
    {
      path: "/login",
      name: "signin-basic",
      component: () => import('../views/SignIn/BasicView.vue'),
    },
    {
      path: "/blog/:menu?",
      name: "blog",
      component: () => import('../views/Blog/BlogView.vue'),
    },
    {
      path: "/categoryList",
      name: "categoryList",
      component: () => import('../views/Blog/BlogCategoryList.vue'),
    },
    {
      path: "/category/:id",
      name: "category",
      component: () => import('../views/Blog/BlogCategory.vue'),
    },
    {
      path: "/article/:id",
      name: "article",
      component: () => import('../views/Blog/BlogArticleInfo.vue'),
    },
    {
      path: "/archives/:id",
      name: "archives",
      component: () => import('../views/Blog/BlogView.vue'),
    },
    {
      path: "/download",
      name: "download",
      component: () => import('../views/Download/DownloadView.vue'),
    },
    {
      path: "/resource/:id",
      name: "resource",
      component: () => import('../views/Download/ResourceInfo.vue'),
    },
    {
      path: "/links",
      name: "links",
      component: () => import('../views/Links/LinksView.vue'),
    },
    {
      path: "/my",
      name: "my",
      component: () => import('../views/My/MyView.vue'),
    },
    {
      path: "/search",
      name: "search",
      component: () => import('../views/Search/SearchView.vue'),
    },
    {
      path: "/404",
      name: "404",
      component: () => import('../components/error/404.vue'),
    },
    {
      path: "/:catchAll(.*)",
      redirect: "404",
    },
  ],
});

export default router;
