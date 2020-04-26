/**
 * Created by codedrinker on 2019/6/1.
 */

/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var userType =$("#userType").text();
    if (userType=="游客用户"){
        toastr.error("请登录，回复！！！");
    }
    else {
        comment2target(questionId, 1, content);
    }
}

function comment2target(targetId, type, content) {
    if (!content) {
        toastr.error("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                window.location.reload();
            } else {
                    toastr.error("请登录，回复！！！");
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

$(function () {
    $('#register').bootstrapValidator({
        live:"disabled",
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 5,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    },
                    remote: { // ajax校验，获得一个json数据（{'valid': true or false}）
                        url: '/checkUsername',       //验证地址
                        message: '用户已存在',   //提示信息
                        type: 'POST',          //请求方式
                        dataType: "json",
                        data: {
                            username: function () {  //自定义提交数据，默认为当前input name值
                                return $("input[name='username']").val();
                            }
                        },
                        delay: 500
                    }
                }
            },
            code:{
                validators:{
                    remote: { // ajax校验，获得一个json数据（{'valid': true or false}）
                        url: '/checkVerify',       //验证地址
                        message: '验证码错误',   //提示信息
                        type: 'POST',          //请求方式
                        dataType: "json",
                        data: {
                            code: function () {
                                return $("#code").val();
                            }
                        },
                        delay: 500
                    }
                }
            },
            password:{
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {  //长度限制
                        min: 6,
                        message: '用户名长度必须大于6位'
                    },
                }
            },
            password1:{
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {  //长度限制
                            min: 6,
                            message: '用户名长度必须大于6位'
                        },
                        identical: {  //比较是否相同
                            field: 'password',  //需要进行比较的input name值
                            message: '两次密码不一致'
                        },
                    }
            },
        },
    });
});

$('#x').click(function() {
    $('#register').data('bootstrapValidator').resetForm(true);
});

function registerbtn(){
    $("#register").data('bootstrapValidator').validate();//相当于触发一次所有的验证
    if($("#register").data('bootstrapValidator').isValid()){//判断验证是否已经通过
        var form=$("#register");
        $.ajax({
            type: "post",
            url: "/register",
            data: form.serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code==200) {
                    $('#myModal1').modal('hide');
                    toastr.success(response.message);
                    /*注册成功之后的跳转*/
                    location.href = '/';
                } else {
                    toastr.error(response.message);}
            }
        });
    }
}


$(function () {
    $('#login').bootstrapValidator({
//		提示的图标
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',			// 有效的
            invalid: 'glyphicon glyphicon-remove',		// 无效的
            validating: 'glyphicon glyphicon-refresh'	// 刷新的
        },
//		属性对应的是表单元素的名字
        fields: {
//			匹配校验规则
            username: {
                // 规则
                validators: {
                    message: '用户名无效',	// 默认提示信息
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '只能是数字字母_.'
                    },
                    /*设置错误信息 和规则无关 和后台校验有关系*/
                    callback: {
                        message: '用户名错误'
                    },
                    fun: {
                        message: 'fun函数无效的示例'
                    }
                }
            },
            password: {
                validators: {
                    message: '密码无效',
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        message: '密码不能少于6位'
                    },
                   /* regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '含有非法字符'
                    },*/
                    callback: {
                        message: '密码不正确'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) { // 表单校验成功
        /*禁用默认提交的事件 因为要使用ajax提交而不是默认的提交方式*/
        e.preventDefault();
        /*获取当前的表单*/
        var form = $(e.target);	// 可以通过选择器直接选择
        console.log(form.serialize());	// username=root&password=123456
        $.ajax({
            type: "post",
            url: "/login",
            data: form.serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code==200) {
                    $('#myModal2').modal('hide');
                    toastr.success(response.message);
                    /*登录成功之后的跳转*/
                   location.href = '/';
                } else {
                    console.log()
                    toastr.error(response.message);}
                }
            })
    });
});

$('#loginX').click(function() {
    $('#login').data('bootstrapValidator').resetForm(true);
});




