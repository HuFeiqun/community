/**
 * 一级回复-点击问题详情页底部的“回复”按钮提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment(questionId, content, 1);
}

/**
 * 二级回复-点击二级回复详情页的“评论”按钮提交回复
 * @param e
 */
function inner_comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    // console.log("input-" + commentId);
    // console.log(commentId);
    // console.log(content);
    var localStorage = window.localStorage;
    localStorage.setItem("reflashInnerComments", commentId);
    // console.log(localStorage.getItem("reflashInnerComments"));
    comment(commentId, content, 2);
}

/**
 * 提交回复(包括一级回复和二级回复)
 * @param parentId 被回复对象的id,当是提交一级回复时,表示所回复的问题的id,当提交二级回复时,表示被回复的评论的id
 * @param content
 * @param type 区分是一级回复还是二级回复
 */
function comment(parentId, content, type) {
    if (!content) {
        alert("回复内容不能为空，请输入回复内容！");
        return;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/comment",
        data: JSON.stringify(
            {
                "parentId": parentId,
                "content": content,
                "type": type
            }
        ),
        success: function (response) {
            console.log(response);
            if (response.code == 200) {
                // $("#comment_dialog_box").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) { //未登录引起错误
                    var isAccepted = confirm(response.message);
                    if (isAccepted) { //用户点击“确定”
                        window.open("https://github.com/login/oauth/authorize?client_id=2c2a7501d5c6e9d1fbeb&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        var localStorage = window.localStorage;
                        localStorage.setItem("closePage", "yes");

                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 折叠二级回复
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");  //当前选中的回复的id
    var innerCommentContainer = $("#comment-" + id);   //该回复对应的回复框div
    if (innerCommentContainer.hasClass("in")) { //当前是展开状态,折叠
        innerCommentContainer.removeClass("in");
        e.classList.remove("active");
    } else { //当前是折叠状态,展开
        if (innerCommentContainer.children().length > 1) { //查询前，子元素只有一个（二级评论表单），查询后，会插入别的子元素，这种情况就无需重复调用getJSON方法
            // console.log("已查询，无需再查");
        } else {
            // console.log("第一次查询");
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                var commentDtos = data.data;
                $.each(commentDtos.reverse(), function (commentIndex) {
                    var div = $('<div/>');
                    var media_left = $('<div/>');
                    media_left.addClass("media-left");
                    var img = $('<img src="#"/>');
                    img.addClass("media-object");
                    img.attr('src', commentDtos[commentIndex].user.avatarUrl);
                    console.log(img.src);
                    media_left.append(img);
                    div.append(media_left);
                    var media_body = $('<div/>');
                    media_body.addClass("media-body");
                    media_body.html(commentDtos[commentIndex].user.name + ":&nbsp;&nbsp;" + commentDtos[commentIndex].content);
                    div.append(media_body);
                    var time_span1 = $('<span>');
                    time_span1.addClass("pull-right text-muted");
                    time_span1.html("评论时间:");
                    var time_span2 = $('<span>');
                    time_span2.html(moment(commentDtos[commentIndex].gmtCreate).format('YYYY-MM-DD HH:mm:ss'));
                    // time_span2.html(timestampToTime(commentDtos[commentIndex].gmtCreate));
                    time_span1.append(time_span2);
                    div.append(time_span1);
                    div.append($('<hr class="custom_hr col-lg-12">'));
                    console.log(div);

                    innerCommentContainer.prepend(div);
                })

            });
        }
        innerCommentContainer.addClass("in"); //展开二级评论框
        e.classList.add("active"); //使得图标高亮
    }
}


/**
 * 展开特定的二级回复，用于问题详情页面加载时，展开刚添加二级回复列表
 */
function collapseParticularComments(id) {
    var innerCommentContainer=$("#comment-" + id);
    $.getJSON("/comment/" + id, function (data) {
        console.log(data);
        var commentDtos = data.data;
        $.each(commentDtos.reverse(), function (commentIndex) {
            var div = $('<div/>');
            var media_left = $('<div/>');
            media_left.addClass("media-left");
            var img = $('<img src="#"/>');
            img.addClass("media-object");
            img.attr('src', commentDtos[commentIndex].user.avatarUrl);
            console.log(img.src);
            media_left.append(img);
            div.append(media_left);
            var media_body = $('<div/>');
            media_body.addClass("media-body");
            media_body.html(commentDtos[commentIndex].user.name + ":&nbsp;&nbsp;" + commentDtos[commentIndex].content);
            div.append(media_body);
            var time_span1 = $('<span>');
            time_span1.addClass("pull-right text-muted");
            time_span1.html("评论时间:");
            var time_span2 = $('<span>');
            time_span2.html(moment(commentDtos[commentIndex].gmtCreate).format('YYYY-MM-DD HH:mm:ss'));
            // time_span2.html(timestampToTime(commentDtos[commentIndex].gmtCreate));
            time_span1.append(time_span2);
            div.append(time_span1);
            div.append($('<hr class="custom_hr col-lg-12">'));
            console.log(div);

            innerCommentContainer.prepend(div);
        })

    });

    innerCommentContainer.addClass("in"); //展开二级评论框
    // e.classList.add("active"); //使得图标高亮
}

/**
 * 在编辑问题页面，加载标签的样式
 */
function loadTagCss() {
    var tag_textarea_element = $("#tag");
    var tag_content = tag_textarea_element.text();
    if(tag_content=="")
        return;
    var tags = tag_content.substr(0,tag_content.length-1).split(",");
    for(index=0;index<tags.length;index++){
        // console.log(tags[index]);
        $("#tag-"+tags[index]).css("background-color","gray");   //将已选中标签标记为灰色
    }

}

/**
 *选中标签后，将标签添加至标签输入栏
 */
function selectTag(e){
    // console.log(e);
    var new_tag = e.getAttribute("data-tag");
    var tag_textarea_element = $("#tag");
    var tag_content = tag_textarea_element.text();
    if(tag_content.search(new_tag+",")==-1){ //如果标签输入栏没有这个标签就添加该标签(同时将该标签标记成灰色），否则取消该标签(同时将标签标记成蓝色）
        tag_textarea_element.text(tag_content+""+new_tag+",");
        $("#tag-"+new_tag).css("background-color","gray");
    }
    else{
        tag_textarea_element.text(tag_content.replace(new_tag+",",""));
        $("#tag-"+new_tag).css("background-color","#5bc0de");
        // tag_textarea_element.addClass()
    }
}

/**
 * 隐藏显示侧边栏,并且修改折叠图标的显示状态
 */
function hideOrShowRules() {
    // var id = e.getAttribute("data-id");
    // var span_element = $("#"+id);
    var edit_part_element = $("#edit-part");  //获取左边的编辑栏所在的div
    var rules_part_element = $("#rules-part");  //获取右边的问题发布规则所在的div
    var icon1_class="glyphicon glyphicon-chevron-right my_flod_icon";     //展开图标
    var icon2_class="glyphicon glyphicon-chevron-left my_flod_icon";     //折叠图标
    var current_icon_element = $("#hideOrShowIcon");               //当前图标
    if(current_icon_element.hasClass(icon1_class)){
        edit_part_element.removeClass("col-lg-9");
        edit_part_element.addClass("col-lg-12");
        rules_part_element.css("display","none");
        current_icon_element.removeClass(icon1_class);
        current_icon_element.addClass(icon2_class);
    }
    else {
        edit_part_element.removeClass("col-lg-12");
        edit_part_element.addClass("col-lg-9");
        rules_part_element.css("display","block");
        current_icon_element.removeClass(icon2_class);
        current_icon_element.addClass(icon1_class);
    }

}