$(document).ready(function () {
    $('.selectType').change(function(){
        $(this).parent().parent().find('.selectInput').val($(this).val())
    })
})

$(document).ready(function () {
    if (document.getElementById("UploadPicPath")) {
        let uploadUrl = $('#UploadPicPath').val();
        $('.uploadPicBtn').fileupload({
            url: uploadUrl,
            done: function (e, resp) {
                let result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $(this).parent().parent().parent().find('.uploadPicInput').val(result.data);
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }
    if (document.getElementById("UploadAudioPath")) {
        let uploadUrl = $('#UploadAudioPath').val();
        $('.uploadAudioBtn').fileupload({
            url: uploadUrl,
            done: function (e, resp) {
                let result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $(this).parent().parent().parent().find('.uploadAudioInput').val(result.data);
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }
    if (document.getElementById("UploadVideoPath")) {
        let uploadUrl = $('#UploadVideoPath').val();
        $('.uploadVideoBtn').fileupload({
            url: uploadUrl,
            done: function (e, resp) {
                let result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $(this).parent().parent().parent().find('.uploadVideoInput').val(result.data);
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }
    if (document.getElementById("UploadFilePath")) {
        let uploadUrl = $('#UploadFilePath').val();
        $('.uploadFileBtn').fileupload({
            url: uploadUrl,
            done: function (e, resp) {
                let result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $(this).parent().parent().parent().find('.uploadFileInput').val(result.data);
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }
})

$(document).ready(function () {
    if (document.getElementById("citySelect") && document.getElementById("cityVal")) {
        let cityVal = $('#cityVal').val();
        $.get('/fps/json/admin/city', function (data, status) {
            if (status == 'success') {
                if(typeof data == 'string'){
                    data = JSON.parse(data)
                }
                $("#selectCity").html('');
                for (let i = 0; i < 26; i++) {
                    let initial = String.fromCharCode(65 + i);
                    let optionHtml = '';
                    data.forEach(item => {
                        if(initial == item.initial){
                            optionHtml += '<option value="' + item.name + '">' + item.name + '</option>'
                        }
                    })
                    if(optionHtml){
                        let groupHtml = '<optgroup label="' + initial + '">';
                        groupHtml += optionHtml;
                        groupHtml += '</optgroup>';
                        $('#citySelect').append(groupHtml);
                    }

                }
                let $citySelect = $('#citySelect')
                $citySelect.select2({
                    allowClear: true,
                    multiple: true
                })
                $citySelect.val(cityVal.split(',')).trigger('change');
                $citySelect.on('select2:select', function (e) {
                    let cityVal = $citySelect.val() || [];
                    $('#cityVal').val(cityVal.join(','));
                });
                $citySelect.on('select2:unselect', function (e) {
                    let cityVal = $citySelect.val() || [];
                    $('#cityVal').val(cityVal.join(','));
                });
            }
        });
    }
})

$(document).ready(function () {
    if (document.getElementById("ckEditor") || document.getElementById("UploadCkeditorPath")) {
        let uploadUrl = $('#UploadCkeditorPath').val()
        ClassicEditor.create(document.querySelector('#ckEditor'), {
            language : 'zh-cn',
            ckfinder: {
                uploadUrl: uploadUrl
            }
        }).then(editor => {
            console.log(editor.getData());
        }).catch(error => {
            console.log(error);
        });
    }
})