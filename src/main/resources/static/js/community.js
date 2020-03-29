function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId);
    console.log(content);

    $.ajax({
        type: "POST",
        contentType:"application/json",
        url: "/comment",
        data: JSON.stringify(
            {
                "parentId":questionId,
                "content":content,
                "type":1
            }
        ),
        success: function (response) {
            console.log(response);
            if(response.code == 200){
                $("#comment_dialog_box").hide();
            }else {
                if(response.code==2003){ //未登录引起错误
                    var isAccepted = confirm(response.message);
                    if(isAccepted){ //用户点击“确定”
                        window.open("https://github.com/login/oauth/authorize?client_id=2c2a7501d5c6e9d1fbeb&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        var localStorage = window.localStorage;
                        localStorage.setItem("closePage","yes");

                    }
                }
                else{
                    alert(response.message)
                }
            }
        },
        dataType: "json"
    });


}