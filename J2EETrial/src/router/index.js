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
import ForumModule from "../components/ForumModule";
import ForumDetailComponent from "../components/ForumDetailComponent";
import Treenode from "../components/Treenode";
import StudentListModule from "../components/StudentListModule";
import ScoreDetailComponent from "../components/ScoreDetailComponent";
import SelectCourse from "../components/SelectCourse";
import InfoStatistic from "../components/InfoStatistic";
import PastCourse from "../components/PastCourse";
import Admin from "../components/Admin";
import AdminMain from "../components/AdminMain";
import AdminNew from "../components/AdminNew";
import AdminPub from "../components/AdminPub";
import AdminStatistics from "../components/AdminStatistics";
import InfoStatMain from "../components/InfoStatMain";
import InfoAttend from "../components/InfoAttend";
import InfoDropout from "../components/InfoDropout";
import InfoScore from "../components/InfoScore";
import InfoEnrollment from "../components/InfoEnrollment";
import InfoWork from "../components/InfoWork";
import InfoPublished from "../components/InfoPublished";

Vue.use(Router);
Vue.use(VueCookies);
Vue.use(Treenode);

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
        },
        {
          path: '/forum',
          name: 'ForumModule',
          component: ForumModule
        },
        {
          path: '/forumDetail',
          name: 'ForumDetailComponent',
          component: ForumDetailComponent
        },
        {
          path:'/stuList',
          name: 'StudentListModule',
          component: StudentListModule
        },
        {
          path: '/scoreDetail',
          name: 'ScoreDetailComponent',
          component: ScoreDetailComponent
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
          path: '/info/statistic',
          name: 'InfoStatMain',
          component : InfoStatMain,
          children : [
            {
              path: '/attend',
              name : 'InfoAttend',
              component:InfoAttend
            },
            {
              path: '/dropout',
              name : 'InfoDropout',
              component: InfoDropout
            },
            {
              path: '/score',
              name : 'InfoScore',
              component: InfoScore
            },
            {
              path:'/enrollment',
              name:'InfoEnrollment',
              component: InfoEnrollment
            },
            {
              path : '/work',
              name : 'InfoWork',
              component : InfoWork
            },
            {
              path:'/published',
              name:'InfoPublished',
              component : InfoPublished
            }
          ]
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
        },
        {
          path:'/course/select',
          name:'SelectCourse',
          component:SelectCourse
        },
        {
          path:'/course/past',
          name:'PastCourse',
          component: PastCourse
        }
      ]
    },

    {
      path: '/error',
      name: 'Error',
      component: Error
    },

    {
      path:'/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path:'/admin/main',
      name: 'AdminMain',
      component : AdminMain,
      children :
        [{
        path: '/new',
        name : 'AdminNew',
        component : AdminNew
      },
      {
        path: '/pub',
        name : 'AdminPub',
        component : AdminPub
      },
          {
            path : '/statistics',
            name : 'AdminStatistics',
            component: AdminStatistics
          }
      ]
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
