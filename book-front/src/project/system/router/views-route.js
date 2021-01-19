const views = [
    {
        path: '/404',
        name: '404',
        meta: {auth: false},
        component: () => import('../views/404')
    },
    {
        path: '/502',
        name: '502',
        meta: {auth: false},
        component: () => import('../views/502')
    }
]
export default views

views.push({
    path: '/sys-admin',
    name: 'sys-admin',
    component: require('../views/sys-admin')
})

views.push({
    path: '/base-employee',
    name: 'base-employee',
    component: require('../views/base-employee')
})

views.push({
    path: '/base-train-record',
    name: 'base-train-record',
    component: require('../views/base-train-record')
})

views.push({
    path: '/base-train-plan',
    name: 'base-train-plan',
    component: require('../views/base-train-plan')
})

views.push({
    path: '/base-teacher',
    name: 'base-teacher',
    component: require('../views/base-teacher')
})

views.push({
    path: '/base-course',
    name: 'base-course',
    component: require('../views/base-course')
})
