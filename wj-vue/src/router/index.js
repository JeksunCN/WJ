import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '../components/home/Index.vue'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import LibraryIndex from '../components/library/LibraryIndex.vue'
import Register from '../components/Register.vue'

Vue.use(Router)

export default new Router({
  // mode 为路由模式设定 hash为带 # 号的，可以检测 # 号后面数据，history为历史记录模式
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      redirect: '/index',
      component: AppIndex,
      meta: {
        requireAuth: true
      }
    },
    // 下面都是固定的写法
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      // home 页面不需要被直接访问
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: AppIndex,
          meta: {
            requireAuto: true
          }
        },
        {
          path: '/library',
          name: 'LibraryIndex',
          component: LibraryIndex,
          mate: {
            requireAuto: true
          }
        }
      ]
    }
  ]
})
