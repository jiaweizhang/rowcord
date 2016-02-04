/**
 * Created by jiawe on 1/18/2016.
 */
myApp.controller('registerController', ['httpService', '$scope', '$http', '$location',
    function (httpService, $scope, $http, $location) {
    $scope.emailTaken = false;
    $scope.register = function () {
        console.log("sending request");
        var data = {
            "email": $scope.email,
            "password": $scope.password,
            "firstName": $scope.firstName,
            "lastName": $scope.lastName
        };
        console.log(data);
        httpService.register(data).then(function (response) {
            console.log(response);
            console.log("response.data.error: "+response.data.error);
            if(response.data.code === 1001){
                $scope.emailTaken = true;
            }
            if(!response.data.error){
                $location.path('/login');
            }
        })
    }
}]);

