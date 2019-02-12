import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login'
import VueCookies from 'vue-cookies'
import Register from "../components/Register";
import Error from '../components/Error'
import Main from "../components/Main";
import Account from "../components/Account";
import NewCourse from "../components/NewCourse";
import PubCourse from "../components/PubCourse";
import CurrentCourse from "../components/CurrentCourse";
import HeaderForCur from "../components/HeaderForCur";
import LessonMain from "../components/LessonMain";
import Courseware from "../components/CoursewareModule";
import HomeworkModule from "../components/HomeworkModule";

Vue.use(Router)
Vue.use(VueCookies)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/:role/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/lesson',
      name: 'LessonMain',
      component: LessonMain,
      children:[
        {
          path: '/courseware',
          name: 'Courseware',
          component: Courseware
        },
        {
          path: '/homework',
          name: 'HomeworkModule',
          component: HomeworkModule
        }
      ]
    },
    {
      path: '/main',
      name: 'Main',
      component: Main,
      children:[
        {
          path: '/course/new',
          name: 'NewCourse',
          component: NewCourse
        },
        {
          path: '/info/account',
          name: 'Account',
          component: Account
        },
        {
          path: '/course/pub',
          name: 'PubCourse',
          component: PubCourse
        },
        {
          path:'/course/current',
          name: 'CurrentCourse',
          component: CurrentCourse
        }
      ]
    },
    {
      path: '/error',
      name: 'Error',
      component: Error
    }

  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.length === 0) { //匹配前往的路由不存在
    from.name ? next({
      name: from.name
    }) : next('/error'); //判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
  } else if (!sessionStorage.role && to.path != '/'){
    //如果未登陆，跳转到登录页面
    next(
      {
        path: '/'
      }
    )

  }
  else {
    next(); //如果匹配到正确跳转
  }
});

export default router;
