import { fileURLToPath, URL } from "url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  server: {
    host: '0.0.0.0',
    port: '3000',
    open: false, // 服务启动后，自动在浏览器中打开，默认是不打开的
    hmr: true, // 为开发服务启用热更新，默认是不启用热更新的
    proxy: {
      '/api': {
        // 配置接口调用目标地址
        target: 'http://127.0.0.1:6666/web',
        // 当进行代理时，Host 的源默认会保留（即Host是浏览器发过来的host），如果将changeOrigin设置为true，则host会变成target的值。
        changeOrigin: true,
        // 前缀 /api 是否被替换为特定目标，不过大多数后端给到的接口都是以/api打头，这个没有完全固定的答案，根据自己的业务需求进行调整
        rewrite: path => path.replace(/^\/api/, ''),
      },
      '/wx': {
        target: 'https://api.weixin.qq.com',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/wx/, ''),
      },
    },
  },
  
});
