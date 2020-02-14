var multiInit = function(){
    var val = $('#FileUploadMultiVal').val() || ''
    var count = $('#FileUploadMultiCount').val() || 0
    var arr = val.split(',')

    $('#FileUploadMultiGroup .item').remove()
    for(var i in arr){
        if(!arr[i]) continue
        var itemHtml = '<div class="item">\n' +
            '<a href="' + arr[i] + '" target="_blank">\n' +
            '<img src="' + arr[i] + '">\n' +
            '</a>\n' +
            '<div class="remove">\n' +
            '<i class="fa fa-times"></i>\n' +
            '</i>\n' +
            '</div>\n';
        $('#FileUploadMultiGroup .add').before(itemHtml)
    }

    if(arr.length < count){
        $('#FileUploadMultiGroup .add').removeClass('hidden')
    }else{
        $('#FileUploadMultiGroup .add').addClass('hidden')
    }
    $('#FileUploadMultiGroup .item .remove').bind('click', multiRemove)
}

var multiRemove = function(e){
    var thisVal = $(this).parent().find('img').attr('src')
    var val = $('#FileUploadMultiVal').val()
    var arr = val.split(',')
    var valArr = []
    for(var i in arr){
        if(thisVal != arr[i]){
            valArr.push(arr[i])
        }
    }
    val = valArr.join(',')
    $('#FileUploadMultiVal').val(val)
    multiInit();
}

$('#FileUploadMultiGroup .item .remove').on('click', multiRemove);
multiInit();

(function ($) {
    var url = $('#PicUploadPath').val();

    if(document.getElementById('FileUpload')){
        $('#FileUpload').fileupload({
            url: url,
            done: function (e, resp) {
                var result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $('#FileUploadInput').val(result.data);
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }

    if(document.getElementById('FileUploadMulti')){
        $('#FileUploadMulti').fileupload({
            url: url,
            done: function (e, resp) {
                var result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    var thisVal = result.data
                    var val = $('#FileUploadMultiVal').val()
                    val = (val == '') ? thisVal : val + ',' + thisVal
                    $('#FileUploadMultiVal').val(val)
                    multiInit()
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }

    if(document.getElementById('upload1')){
        $('#upload1').fileupload({
            url: url,
            done: function (e, resp) {
                var result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $('#uploadPic1').val(result.data);
                    $('#uploadImg1').attr('src', result.data);
                    $('#uploadGroup1').show();
                    $('#uploadGroup1').removeClass('hidden');
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }

    if(document.getElementById('upload2')){
        $('#upload2').fileupload({
            url: url,
            done: function (e, resp) {
                var result = resp.result;
                if(typeof result == 'string'){
                    result = JSON.parse(result);
                }
                if (result.code == 200) {
                    $('#uploadPic2').val(result.data);
                    $('#uploadImg2').attr('src', result.data);
                    $('#uploadGroup2').show();
                    $('#uploadGroup2').removeClass('hidden');
                }else if (result.code == 400) {
                    alert(result.msg);
                }else{
                    alert('服务器错误,上传失败!');
                }
            }
        });
    }
})(jQuery);