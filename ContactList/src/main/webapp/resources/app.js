var app = angular.module('app',['ngResource']);

//Factory
app.factory('Service',  function($resource) {
  var data = $resource("http://localhost:8080/ContactList/contact\\/:id", {id:"@id"}, {
	  update:{
          method:'PUT'
          }
      });
      return data;
  }); // Note the full endpoint address


//Controller
app.controller('ResourceController', function($scope, Service) {

	// Get a Contact - search on ID
    $scope.getContact = function(){
          $scope.contact = Service.get({ id: $scope.iid }).$promise.then(function(cc){
                  console.log(cc);
                  $scope.name = cc.name;
                  $scope.address = cc.address;
                  $scope.phone = cc.phone;
                  $scope.comments = cc.comments;
          }, function(error){
                  console.error('ERROR:', error);
            });
    };
    
    // Save a Contact
    $scope.add = function(){
    	var c = {name:$scope.name,address:$scope.address,phone:$scope.phone,comments:$scope.comments};
    	Service.save( c, function(){
            	console.log("Contact Saved", c);
        	}, function(error){
        		console.error('ERROR:', error);
        });
    };

    // Update a contact
    $scope.update = function(){
    	var c = {id:$scope.iid,name:$scope.name,address:$scope.address,phone:$scope.phone,comments:$scope.comments};
    	Service.update( c, function(){
            	console.log("Contact Updated", c);
        	}, function(error){
        		console.error('ERROR:', error);
        });
    };
    
    // Delete a contact
    $scope.del = function(){
    	Service.remove( { id: $scope.iid }, function(){
            	console.log("Contact Deleted");
        	}, function(error){
        		console.error('ERROR:', error);
        });
    };
    
    // Reset the form
    $scope.reset = function(){
          $scope.name = "";
          $scope.address = "";
          $scope.phone = "";
          $scope.iid = "";
    };

    $scope.reset();

});
