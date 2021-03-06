/**
 * 说明：
 * 创建人：pluto
 * 创建时间：2018/8/3
 */

angular.module('app.rbac_dept',['ui.router','ui.bootstrap', 'w5c.validator'])
    .config(['w5cValidatorProvider','$stateProvider',function (w5cValidatorProvider, $stateProvider) {
        //验证规则
        w5cValidatorProvider.setRules({
            name: {
                required : '部门名称不能为空',
            },
            // pid: {
            //     required : '描述不能为空',
            //     pattern  : '年龄在1-100之间',
            // }
        });
        $stateProvider.state('main.rbac_dept',{
            url: '/rbac/dept',
            templateUrl: 'pages/rbac_dept/view/view.html',
            controller: 'RBACDeptCtrl',
            controllerAs: 'rbac_dept',
            resolve: {},
            ncyBreadcrumb: {
                label: '部门管理',
                parent: "main"
            }
        });
    }])
    .controller("RBACDeptCtrl", RBACDeptCtrl)
    .controller('RBACDeptModalCtrl',RBACDeptModalCtrl)
    .service('RBACDeptService', RBACDeptService);
