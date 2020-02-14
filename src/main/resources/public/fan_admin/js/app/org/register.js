var get_mobile_code = function(){
    var mobile = $("#mobile").val();
    var verify_code = $("#verify_code").val();
    if (!isMobile(mobile)) {
        alert('请输入正确手机号码');
        $("#mobile").focus();
        return false;
    }
    if (verify_code == '') {
        alert('请输入验证码');
        $("#verify_code").focus();
        return false;
    }

    var data = {
        mobile: mobile,
        verify_code: verify_code
    }

    $("#get_mobile_code").attr("disabled", "disabled");
    $("#get_mobile_code").unbind("click", get_mobile_code);

    $.ajax({
        type: "post",
        url: "/fps/api/verify/mobile",
        data: data,
        dataType: "json",
        success: function (result) {
            if (result.code == 200) {
                alert("验证码发送成功！");
                fun_timedown();
            } else if (result.code == 400) {
                alert(result.msg);
                $("#get_mobile_code").removeAttr("disabled");
                $("#get_mobile_code").bind("click", get_mobile_code);
            } else {
                alert('系统错误，请联系管理员！');
                $("#get_mobile_code").removeAttr("disabled");
                $("#get_mobile_code").bind("click", get_mobile_code);
            }
        },
        error: function (e) {
            alert('系统错误，请联系管理员！');
            $("#get_mobile_code").removeAttr("disabled");
        }
    });
}

function isMobile(mobile) {
    if(!mobile) return false;
    var re = false;
    var mobile_pattern = /^0?(1[0-9])[0-9]{9}$/;
    if(!mobile || !mobile_pattern.test(mobile) || mobile.length != 11){
        re = false;
    }else{
        re = true;
    }
    return re;
}

function fun_timedown(time)  {
    if(!time && time != 0) time = 60;
    $("#get_mobile_code").html("("+time+")"+"重新获取");
    time = time-1;
    if(time>=0){
        setTimeout(function(){fun_timedown(time)},1000);
        $("#get_mobile_code").css('opacity',0.5);
        $("#get_mobile_code").attr("disabled", "disabled");
        $("#get_mobile_code").unbind("click", get_mobile_code);
    }else {
        $("#get_mobile_code").css('opacity',1);
        $("#get_mobile_code").removeAttr("disabled");
        $("#get_mobile_code").bind("click", get_mobile_code);
        $("#get_mobile_code").html("重新获取");
    }
}

(function ($) {
    $("#get_mobile_code").bind("click", get_mobile_code);
})(jQuery);

(function ($) {
    var class_type = $("#class_type").val();
    var class_name = $("#class_name").val();
    var upload_url = $("#file_upload_path").val();
    upload_url = upload_url + "?class_type=" + class_type + "&class_name=" + class_name ;

    if(document.getElementById("fileupload")){
        $("#fileupload").fileupload({
            url: upload_url + "&code=" + $("#mobile_code").val(),
            formData: {},
            done: function (e, resp) {
                var result = resp.result;
                if (result.code == 200) {
                    $("#upload_pic").val(result.data);
                    $("#upload_img").attr("src", result.data);
                    $("#upload_img").attr("alt", result.data);
                    $("#upload_group").show();
                    $("#upload_group").removeClass("hidden");
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert("服务器错误,上传失败!");
                }
            }
        });
    }
})(jQuery);