function nva() {
    // 导航条
    layui.use(['element', 'jquery', "layer"], function () {
        var layer = layui.layer;
        var element = layui.element; // 导航的hover效果、二级菜单等功能，需要依赖element模块
        var $ = layui.jquery;
        var parent = $('#showname').parentsUntil("ul").find("dl:first");
        try {
            var username = LocalStorage_Day.get("USER").nickName;
        } catch (err) {
            parent.removeClass("layui-nav-child");// 设置为展开之前的css，即不展开的样式
        }

        function errotoken(url) {
            try {
                let token = localStorage.getItem('TOKEN');
                if (token != null) {
                     window.location.href = url;
                } else {
                    layer.msg('没有登陆快去登陆吧', {
                        icon: 2,
                        time: 1000
                    }, function () {
                        popwindows('login');
                    });
                }
            } catch (err) {
                layer.msg('没有登陆快去登陆吧', {
                    icon: 2,
                    time: 1000
                }, function () {
                    popwindows('login');
                });
            }
        }

        element.on('nav(houseManagefilter)', function (elem) {
            let name = $(elem).html();
            switch (name) {
                case '房东预约查看': {
                    errotoken('/index/reserveHouseManage');
                    break;
                }
                case '租客预约查看': {
                    errotoken('/index/reserveTenantManage');
                    break;
                }
                case '发布房屋': {
                    errotoken('/houseadd');
                    break;
                }
            }
        });

        // 监听导航点击
        element.on('nav(loginfilter)', function (elem) {
            let id = $(elem).attr("id");
            debugger
            switch (id) {
                case "loginout_top": {
                    logout();
                    element.render('nav');
                    break;
                }
                case 'owner_order_manage_top': {
                    checkRole('/showOwnerManage')
                    break;
                    // errotoken('/showOwnerManage');
                    // break;
                }
                case 'tenant_order_manage_top': {
                    checkRole('/showTentantManage')
                    // errotoken("/showTentantManage");
                    break;
                }
                case 'house_manage_top': {
                    checkRole('/showRentManage')
                    // errotoken('/showRentManage');
                    break;
                }
                case 'house_review_top':{
                    let token = localStorage.getItem('TOKEN');
                    var user = JSON.parse(localStorage.getItem('USER'));
                    var userRole = '';
                    if (token != null) {
                        userRole = load_userRole(user.value.id);
                        if (userRole[0].roles[0].authority === 'USER') {
                            layer.msg('你没有权限');
                            return;
                        }else {
                            errotoken('/showHouseReview');
                        }
                    }
                    break;
                }
                case 'contract_review_top': {
                    let token = localStorage.getItem('TOKEN');
                    var user = JSON.parse(localStorage.getItem('USER'));
                    var userRole = '';
                    if (token != null) {
                        userRole = load_userRole(user.value.id);
                        if (userRole[0].roles[0].authority === 'USER') {
                            layer.msg('你没有权限');
                            return;
                        }else {
                            errotoken('/admin/showContractReview');
                        }
                    }
                    break;
                }
                case 'user_manage_top': {
                    errotoken('/showuser');
                    break;
                }
                case "btn_parm_top": {
                    element.render('nav');
                    let user = document.getElementById("showname").innerText.trim();
                    let oldlogin = $.trim(user) + "";
                    if (encodeURIComponent(oldlogin) === "%E7%99%BB%E5%BD%95") {
                        layer.closeAll();
                        popwindows("login");
                        element.render('nav');
                    } else {
                        console.log("失败");
                    }
                    break;
                }
            }
        });

        function checkRole(data) {
            let token = localStorage.getItem('TOKEN');
            var user = JSON.parse(localStorage.getItem('USER'));
            var userRole = '';
            if (token != null) {
                userRole = load_userRole(user.value.id);
                if (userRole[0].roles[0].authority === 'ADMIN') {
                    layer.msg('你没有权限');
                    return;
                }else {
                    errotoken(data);
                }
            }
        }
    });


    function logout() {
        localStorage.removeItem("TOKEN");
        localStorage.removeItem("USER");
        var user = document.getElementById("showname");
        user.innerHTML = "登陆";
        window.location.reload();
    }
}

function popwindows(id) {
    // 弹窗
    layui.use('layer', function () { // 独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; // 独立版的layer无需执行这一句
        layer.closeAll();
        layer.open({
            type: 1
            , title: '登陆窗口'
            , area: ['500px', '400px']
            , offset: '100px'
            , id: 'btn' + id // 防止重复弹出
            , content: $('#signform')
            , success: login()
            , closeBtn: 2
            , resize: false// 拉伸
            , shade: [0.8, '#393D49']// 遮罩
            , shadeClose: true // 点击遮罩关闭层
            , cancel: function (index, layero) {
                layer.close(index)
            }
            , end: function () {
                if (layer.index > 0) {
                    layer.close(layer.index);
                }
            }
        });
    });
}

// 登陆函数
function login() {
    layui.use(['jquery', 'layer'], function () {
        let $ = layui.jquery;
        let layer = layui.layer;
        $('#login').click(
            function () {
                var username = $.trim($("#user")
                    .val());// 获取用户名
                var password = $.trim($("#pwd")
                    .val());
                // var url = user_login_url;
                var url = 'http://localhost:8080/oauth/token?client_id=house&client_secret=house&grant_type=password';
                var param = {
                    "username": username,
                    "password": password
                };
                $.ajax({
                    async: true,
                    url: url,
                    headers: {
                        Accept: "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8;application/json; charset=utf-8"
                    },
                    type: 'POST',// method请求方式，get或者post
                    dataType: 'json',// 预期服务器返回的数据类型
                    data: param,// 表格数据序列化
                    // contentType:
                    // "application/json;
                    // charset=utf-8",
                    success: function (res) {
                        layer.close(layer.index);
                        success(res);
                    },// res为相应体,function为回调函数
                    error: error
                });
            });
    });
}

// 延迟加载区
function loadScript() {
    nva();
    try {
        var swap = LocalStorage_Day.get("USER").nickName;
    } catch (err) {
        // popwindows();
    }
    var username = {
        'username': swap
    };
    setname(username);
}

function success(res) {
    if (res != null) {
        updateUsername(res);
    }
    down();
    // console.log(JSON.stringify(data));
}

// 更新姓名
function updateUsername(res) {
    layui.use(['jquery','layer'], function () {
        // debugger;
        var $ = layui.jquery;
        var layer = layui.layer;
        var btnname = $('#btn');
        var name = btnname.text().trim();
        var username = null;
        token.set(res);
        // var swapToken = "Bearer" + "\xa0" + res.access_token;
        // var tokenhear = {"Authorization": swapToken};
        var tokenheaders = {
            'access_token': res.access_token
        };
        $.ajax({
            async: false,
            url: 'http://localhost:8080/user/info',
            type: 'GET',// method请求方式，get或者post
            data: tokenheaders,
            success: function (userdata) {
                if (userdata.code === 200) {
                    if(userdata.data[0].credit === 0 || userdata.data[0].close === 1 ){
                        localStorage.removeItem('USER');
                        localStorage.removeItem('TOKEN');
                        return false;
                    }
                    LocalStorage_Day.set("USER", userdata.data[0], 1 / 12);
                    username = userdata.data[0].nickName;
                }
            },
            error: function (res) {
                console.log("登陆失败")
            }
            // , beforeSend: function (XMLHttpRequest) {
            // XMLHttpRequest.setRequestHeader("Authorization", swapToken);
            // }
        });
        if (name !== username && username != null) {
            let userdata = {
                'username': username
            };
            setname(userdata);
            window.onload = function(){
                function fun(){
                    window.location.reload();
                }
                setTimeout("fun()",4000)
            }
        }
    });
}

// 设置姓名
function setname(userdata) {
    layui.use('laytpl', function () {
        var laytpl = layui.laytpl;
        laytpl.config({
            open: '{{',
            close: '}}'
        });
        var getTpl = usermodel.innerHTML;
        var view = document.getElementById('showname');
        laytpl(getTpl).render(userdata, function (html) {
            view.innerHTML = html;
        });
    });
}

// 关闭窗口
function down() {
    layui.use(['layer', 'jquery'], function () {
        var layer = layui.layer;
        var $ = layui.jquery;
        layer.close(layer.index);
        // 关闭窗口后，添加下拉效果
        var parent = $('#showname').parentsUntil("ul").find("dl:first");
        parent.addClass("layui-nav-child");
        let user = LocalStorage_Day.get('USER');
        if(user === null){
            layer.msg('你没有权限',{icon:2,time:2000},function(){
                localStorage.removeItem('TOKEN');
                localStorage.removeItem('USER');
            });
        }
    });
}

function error(data) {
    var user = document.getElementById('show_erro');
    user.innerHTML = "用户名或者密码错误";
    // console.log('操作失败！!');
}

// 延迟加载
if (window.addEventListener) {
    window.addEventListener("load", loadScript, false);
} else if (window.attachEvent) {
    window.attachEvent("onload", loadScript);
} else {
    window.onload = loadScript;
}

//睡眠
function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
}

// 查询用户Role
function load_userRole(user_id) {
    let $ = layui.jquery;
    let token = JSON.parse(localStorage.getItem('TOKEN'));
    var result = '';
    layui.use('jquery', function () {
        $.ajax({
            async: false,
            url: 'http://localhost:8080/user/getRole',
            type: 'POST',
            data: {"user_id":user_id,"access_token": token.value.access_token,},
            success: function (res) {
                result = res.data
            },
            error: function () {
                layer.msg('查询出错', {icon: 2, time: 1000});
            }
        });
    });
    return result;
}


