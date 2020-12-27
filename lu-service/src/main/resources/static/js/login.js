$(document).ready(function(){
    $('#loginForm').submit(function (e) {
        submitForm(e);
        }
    );
});

function submitForm(e) {
    e.preventDefault();
    const actionUrl = e.currentTarget.action;
   // console.log($('form#loginForm').serialize());
    $.ajax({
        url: actionUrl,
        type: 'post',
        contentType: "application/json;charset=UTF-8",
        data: objectifyForm($('form#loginForm').serializeArray()),
        success: function(data){
            console.log(data);
            setLocalstorageItem( 'token', data.accessToken);
            setLocalstorageItem('userId', data.userId);
        },
        error: function(response){
            alert(response.responseText);
        }
    });
}

function setLocalstorageItem(name, value) {
    sessionStorage.setItem(name, value);
}

function objectifyForm(formArray) {
    //serialize data function
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}