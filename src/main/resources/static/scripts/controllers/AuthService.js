/**
 * Created by alanguo on 9/18/16.
 */
angular.module('rowcordApp')
.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
    $rootScope.$on('$routeChangeStart', function (event) {

        if (!Auth.isLoggedIn()) {
            console.log('DENY');
            event.preventDefault();

            $location.path('/login');
            console.log($location.path());
        }
        else {
            console.log('ALLOW');
            $location.path('/home');
        }
    });
}])

.factory('Auth', function(){
    var user;

    return{
        setUser : function(aUser){
            user = aUser;
        },
        isLoggedIn : function(){
            return(user)? user : false;
        }
    }
});
