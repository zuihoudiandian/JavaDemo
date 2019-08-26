/**
 * Created by codedrinker on 2019/6/1.
 */

/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
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
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=ffa2b92dc0c79ab1fb1f&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
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
                        min: 6,
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
                },
                password1: {
                    message: '密码无效',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '密码长度必须在6到30之间'
                        },
                        different: {  //比较
                            field: 'username', //需要进行比较的input name值
                            message: '密码不能与用户名相同'
                        },
                        identical: {  //比较是否相同
                            field: 'password',  //需要进行比较的input name值
                            message: '两次密码不一致'
                        }
                    }
                },

                email: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    }
                }
            }
        }
    });
});

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
                        max: 18,
                        message: '密码在6-18个字符内'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '含有非法字符'
                    },
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
                console.log(response)
                if (response.success) {
                    /*登录成功之后的跳转*/
                    location.href = '/';
                } else {
                    // 登录失败
//              	登录按钮点击后,默认不允许再次点击;登录失败要恢复登录按钮的点击
//					form.data('bootstrapValidator').disableSubmitButtons(false);
                    form.bootstrapValidator('disableSubmitButtons', false);
//					指定触发某一个表单元素的的错误提示函数
                    if (response.error == 1000) { // 后台接口如果返回error=1000表示name错误
//						form.data('bootstrapValidator').updateStatus('username', 'INVALID', 'fun'); // 不能触发
// 						可以触发
                        form.data('bootstrapValidator').updateStatus('username', 'INVALID', 'callback');
//						form.data('bootstrapValidator').updateStatus('username', 'INVALID').validateField('username');
//						form.data('bootstrapValidator').updateStatus('username', 'INVALID', 'notEmpty');
                    } else if (response.error == 1001) { // 后台接口如果返回error=1001表示密码错误
                        form.data('bootstrapValidator').updateStatus('password', 'INVALID', 'callback');
                    }
                }
            }
        });
    });
//	重置功能
    $(".pull-left[type='reset']").on('click', function () {
        $('#login').data('bootstrapValidator').resetForm();
    });
});