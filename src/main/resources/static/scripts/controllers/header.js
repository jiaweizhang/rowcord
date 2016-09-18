/**
 * Created by alanguo on 9/18/16.
 */
angular.module('rowcordApp')
    .controller('HeaderCtrl', function ($scope, $location) {
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    })