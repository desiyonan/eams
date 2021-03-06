/**
 * 说明：
 * 创建人：pluto
 * 创建时间：2018/8/3
 */

angular.module('app.rbac_user',['ui.router','ui.bootstrap', 'w5c.validator'])
    .config(['w5cValidatorProvider','$stateProvider',function (w5cValidatorProvider, $stateProvider) {
        //验证规则
        w5cValidatorProvider.setRules({
            name: {
                required : '编号不能为空',
                maxlength: '编号长度不能大于{maxlength}'
            },
            pwd: {
                required : '描述不能为空',
                pattern  : '年龄在1-100之间',
            },
        });
        $stateProvider.state('main.rbac_user',{
            url: '/rbac/user',
            templateUrl: 'pages/rbac_user/view/view.html',
            controller: 'RBACUserCtrl',
            controllerAs: 'rbac_user',
            resolve: {},
            ncyBreadcrumb: {
                label: '用户管理',
                parent: "main"
            }
        });
    }])
    .controller("RBACUserCtrl", RBACUserCtrl)
    .controller('RBACUserModalCtrl',RBACUserModalCtrl)
    .controller("RBACUserRoleModalCtrl", RBACUserRoleModalCtrl)
    .service('RBACDeptService',RBACDeptService)
    .service('RBACRoleService',RBACRoleService)
    .service('RBACUserService', RBACUserService);
