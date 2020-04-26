$("#avatar_file").fileinput({
    language: 'zh',
    theme: 'fas',
    uploadUrl: '/userInfo/uploadImg',
    allowedFileExtensions: ['jpg', 'png', 'gif'],
    uploadAsync: true,
    maxFileSize: 1000,
    maxFilesNum: 10,
    enctype: 'multipart/form-data',
    browseClass: "btn btn-primary", //文件选择器/浏览按钮的CSS类。默认为btn btn-primary
    fileActionSettings: { // 在预览窗口中为新选择的文件缩略图设置文件操作的对象配置
        showRemove: true, // 显示删除按钮
        showUpload: true, // 显示上传按钮
        showDownload: false, // 显示下载按钮
        showZoom: true, // 显示预览按钮
        showDrag: true, // 显示拖拽
        removeIcon: '<i class="fa fa-trash"></i>', // 删除图标
        uploadIcon: '<i class="fa fa-upload"></i>', // 上传图标
    },
    }).on("fileuploaded", function(event, data) {
            if(data.response.code==200){
                $("#avatar_img").attr("src",data.response.message);
                $("#login_img").attr("src",data.response.message);
                toastr.success("操作成功");
                $(event.target) .fileinput('clear').fileinput('unlock');
                $('#uploadModal').modal('hide');
            }
    });

function update() {
        var info=$("#userInfo").serializeJSON();
        var str=JSON.stringify(info);
        $('#exampleModal').modal('hide');
        $.ajax({
            type: "post",
            url: "/userInfo/updateInfo",
            data: str,
            contentType:"application/json;charset=UTF-8",
            dataType: "json",
            success: function (data) {
                if (data.code==200)
                {
                    var newData =JSON.stringify(data.data);
                    newData = eval("(" + newData + ")");
                    $("#sname").text(newData.name);
                    if (newData.sex=="1"){
                        $("#ssex").text("女");
                    }
                    else
                        $("#ssex").text("男");
                    $("#semail").text(newData.email);
                    $("#sbio").text(newData.bio);
                    toastr.success("操作成功")

                }
            }
        });
}
