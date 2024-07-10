import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/Home/HomeView.vue";
import AuthorView from "../views/Author/AuthorView.vue";
import SignInBasicView from "../views/SignIn/BasicView.vue";

import BlogView from "../views/Blog/BlogView.vue";
import BlogArticle from "../views/Blog/BlogArticleInfo.vue";
import BlogCategory from "../views/Blog/BlogCategory.vue";
import BlogCategoryList from "../views/Blog/BlogCategoryList.vue";

import DownloadView from "../views/Download/DownloadView.vue";
import ResourceInfo from "../views/Download/ResourceInfo.vue";

import LinksView from "../views/Links/LinksView.vue";

import MyView from "../views/My/MyView.vue";
import SearchView from "../views/Search/SearchView.vue";

import NotFound from "../components/error/404.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/author",
      name: "author",
      component: AuthorView,
    },
    {
      path: "/login",
      name: "signin-basic",
      component: SignInBasicView,
    },
    {
      path: "/blog/:menu?",
      name: "blog",
      component: BlogView,
    },
    {
      path: "/categoryList",
      name: "categoryList",
      component: BlogCategoryList,
    },
    {
      path: "/category/:id",
      name: "category",
      component: BlogCategory,
    },
    {
      path: "/article/:id",
      name: "article",
      component: BlogArticle,
    },
    {
      path: "/archives/:id",
      name: "archives",
      component: BlogView,
    },
    {
      path: "/download",
      name: "download",
      component: DownloadView,
    },
    {
      path: "/resource/:id",
      name: "resource",
      component: ResourceInfo,
    },
    {
      path: "/links",
      name: "links",
      component: LinksView,
    },
    {
      path: "/my",
      name: "my",
      component: MyView,
    },
    {
      path: "/search",
      name: "search",
      component: SearchView,
    },
    {
      path: "/404",
      name: "404",
      component: NotFound,
    },
    {
      path: "/:catchAll(.*)",
      redirect: "404",
    },
  ],
});

export default router;
