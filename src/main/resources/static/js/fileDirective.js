app.directive("file",function(){
    return function(scope,elm,attrs){
        elm.bind("change",function(evt){
            scope.$apply(function(scope){
                scope.file = evt.target.files[0];
            })
        });
    };
});