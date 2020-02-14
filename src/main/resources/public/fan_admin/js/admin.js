(function ($) {
    var menuParent = $("#menuParent").val();
    var menuCurrent = $("#menuCurrent").val();
    var location = window.location.href.split('/')
    var url = '/' + location[3] + '/json/' + location[4] + '/left_menu';
    $.getJSON(url, function(result){
        if(result.code == 200){
            var lists = result.data;
            var html = '';
            if(lists.length > 0){
                $.each(lists, function(i, parentField){
                    if(!parentField.parent){
                        var activeParent = '';
                        var iconParent = 'fa fa-reorder';
                        if(menuParent == parentField.alias){
                            activeParent = 'active';
                        }
                        if(!!parentField.icon){
                            iconParent = parentField.icon;
                        }
                        html += '<li class="treeview ' + activeParent + '">\n';
                        if(!parentField.link){
                            html += '<a href="javascript:;">\n';
                            html += '<i class="' + iconParent + '"></i> <span>' + parentField.name + '</span>\n';
                            html += '<i class="fa fa-angle-left pull-right"></i>\n';
                            html += '</a>\n';

                            html += '<ul class="treeview-menu">\n';
                            $.each(lists, function(j, currentField){
                                if(currentField.parent == parentField.alias && currentField.type != 'admin_inc'){
                                    var activeCurrent = '';
                                    var iconParent = 'fa fa-circle-o';
                                    if(menuCurrent == currentField.alias){
                                        activeCurrent = "active";
                                    }
                                    if(!!currentField.icon){
                                        iconCurrent = currentField.icon;
                                    }
                                    html += '<li class="' + activeCurrent + '">\n';
                                    html += '<a href="' + currentField.link + '">\n';
                                    html += '<i class="' + iconParent + '"></i> ' + currentField.name + '\n';
                                    html += '</a>\n';
                                    html += '</li>\n';
                                }
                            });
                            html += '</ul>\n';
                        }else{
                            html += '<a href="' + parentField.link + '">\n';
                            html += '<i class="fa fa-reorder"></i> <span>' + parentField.name + '</span>\n';
                            html += '</a>\n';
                        }
                        $('#left-menu').append('</li>\n');
                    }
                });
            }
            $('#left-menu').append(html);
        }
    });
})(jQuery);

var routeHome = function () {
    var location = window.location.href.split('/')
    var url = '/' + location[3] + '/' + location[4] + '/home'
    window.location.href = url
}

var routeProfile = function () {
    var location = window.location.href.split('/')
    var url = '/' + location[3] + '/' + location[4] + '/home/profile'
    window.location.href = url
}

var routePasswd = function () {
    var location = window.location.href.split('/')
    var url = '/' + location[3] + '/' + location[4] + '/home/passwd'
    window.location.href = url
}

var routeLogout = function () {
    var location = window.location.href.split('/')
    var url = '/' + location[3] + '/' + location[4] + '/home/logout'
    window.location.href = url
}

function goPage(page){
    form_list.page.value = page;
    form_list.submit();
}

function goPagesize(pagesize){
    form_list.pagesize.value = pagesize;
    form_list.submit();
}

function post(url){
    var tempform = document.createElement("form");
    tempform.action = url;
    tempform.method = "post";
    tempform.style.display="none";
    var opt = document.createElement("input");
    opt.type = "submit";
    tempform.appendChild(opt);
    document.body.appendChild(tempform);
    tempform.submit();
    document.body.removeChild(tempform);
}

function goDel(url){
    if(confirm('确认要删除吗？')){
        post(url);
        return true;
    }
    return false;
}

function goPost(url){
    if(confirm('确认要操作吗？')){
        post(url);
        return true;
    }
    return false;
}

function createModal(){
    if($('#aModal').length > 0){
        $('#aModal').remove();
    }
    var url = arguments[0] || '';
    var title = arguments[1] || '详情';
    var size = arguments[2] || 'modal-lg';
    var height = arguments[3] || '530';
    var html = '<div class="modal fade" id="aModal" tabindex="-1" role="dialog" aria-labelledby="aModalLabel">\n' +
        '    <div class="modal-dialog {{size}}" role="document">\n' +
        '        <div class="modal-content">\n' +
        '            <div class="modal-header">\n' +
        '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\n' +
        '                <h4 class="modal-title">{{title}}</h4>\n' +
        '            </div>\n' +
        '            <iframe width="100%" height="{{height}}" frameborder="0" src="{{url}}"></iframe>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>';
    html = html.replace(/{{url}}/g, url);
    html = html.replace(/{{title}}/g, title);
    html = html.replace(/{{size}}/g, size);
    html = html.replace(/{{height}}/g, height);
    $('body').append(html);
}

function createExample(){
    var url = $('#exampleUrl').val();
    var title = $('#exampleTitle').val();
    if($("#example").length > 0) {
        var json = {
            title: title || '示例模版',
            head: [],
            body: []
        }
        $("#example tr").each(function(i, tr){
            if(i == 0){
                $(tr).find("th").each(function(j, th){
                    json.head.push($(th).html())
                })
            }else{
                var bodyArr = []
                $(tr).find("td").each(function(j, td){
                    bodyArr.push($(td).html())
                })
                json.body.push(bodyArr);
            }
        })
        var oForm = document.createElement("form");
        oForm.action = url;
        oForm.method = "post";
        oForm.style.display="none";
        oForm.target = "_blank";
        var oText = document.createElement("textarea");
        oText.name = "json";
        oText.value = JSON.stringify(json);
        var oButton = document.createElement("input");
        oButton.type = "submit";
        oForm.appendChild(oText);
        oForm.appendChild(oButton);
        document.body.appendChild(oForm);
        oForm.submit();
        document.body.removeChild(oForm);
    }
}