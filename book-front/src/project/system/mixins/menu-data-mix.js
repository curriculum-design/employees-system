export default {
    data() {
        return {
            menus: [],
            lodingMenu: false,
        }
    },
    computed: {
        menuData() {
            const menusMap = {}
            const menus = this.menus.map(menu => {
                let m = {
                    id: menu.menuId,
                    label: menu.menuName,
                    ...menu,
                    children: [],
                }
                menusMap[menu.menuId] = m
                return m
            })
            const root = []
            menus.forEach(m => {
                if (m.parentId && menusMap[m.parentId]) {
                    menusMap[m.parentId].children.push(m)
                } else {
                    root.push(m)
                }
            })
            return root
        },
        treeData() {
            const menusMap = {}
            const menus = this.menus.map(menu => {
                let m = {
                    id: menu.menuId,
                    label: menu.menuName,
                    ...menu,
                    children: [],
                }
                menusMap[menu.menuId] = m
                return m
            })
            const root = []
            menus.forEach(m => {
                if (m.parentId && menusMap[m.parentId]) {
                    menusMap[m.parentId].children.push(m)
                } else {
                    root.push(m)
                }
            })
            return root
        },
    },
    async created() {
        await this.getTreeData()
    },
    methods: {
        async getTreeData() {
            this.menus = [{
                'id': '1',
                'menuName': '员工信息',
                'url': 'base-employee',
            }, {
                'id': '2',
                'menuName': '员工培训计划',
                'url': 'base-train-plan',
            }, {
                'id': '3',
                'menuName': '员工培训记录',
                'url': 'base-train-record',
            }, {
                'id': '4',
                'menuName': '讲师信息',
                'url': 'base-teacher',
            }]
        },
    },
}
