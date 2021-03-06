layui.use(['laytpl', 'jquery', 'layer', 'form'], function () {
    let laytpl = layui.laytpl,
        $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
    let houseid = localStorage.getItem("houseid");
    var token = get_LocalStorage('TOKEN');
    // "access_token": token.access_token
    let url = 'http://localhost:8080/house/list';
    $.ajax({
        async: false
        , url: url
        , type: 'POST'
        , data: {"id": houseid,'pay_b':0}
        , success: function (res) {
            // localStorage.setItem("house", JSON.stringify(res));
            det_tpl(res);

            function det_tpl(data) {
                let parm = data.data;
                let list = [];
                for (let i = 0, len = parm[0].list.length; i < len; i++) {
                    let image = null;
                    var imagess = null;
                    try {
                        imagess = parm[0].list[i].pics;
                        debugger
                        image = parm[0].list[i].pics[0].url
                    } catch (err) {
                        image = "/images/temp/property_01.jpg";
                    }
                    list[i] = {
                        "images": image,
                        "housename": parm[0].list[i].title,
                        "zent": parm[0].list[i].rent,
                        "houseid": parm[0].list[i].id,
                        "houseaddress": parm[0].list[i].addr_detail,
                        "style": parm[0].list[i].style,
                        "housearea": parm[0].list[i].addr_id,
                        "address": parm[0].list[i].addr_detail,
                        "status": parm[0].list[i].status >= '3' ? true : false,
                        "payway": parm[0].list[i].pay_a,
                        "attestation": parm[0].list[i].status >= '1' ? true : false,
                        "date": parm[0].list[i].create_time,
                        "area": parm[0].list[i].area,
                        "username": parm[0].list[i].nick_name,
                        "faci": parm[0].list[i].info,
                        "housestyle": parm[0].list[i].type_a * 1000 + parm[0].list[i].type_b * 100 + parm[0].list[i].type_c * 10 + parm[0].list[i].type_d,
                        "userid": parm[0].list[i].user_id,
                        "roleTypeList": [1,2,3],
                        "imagess": imagess
                    };

                }
                parming({"list": list});
            }

            function parming(data) {
                let search = get_LocalStorage("SEARCH");
                if (search == null) {
                    layui.use('jquery', function () {
                        let $ = layui.jquery;
                        $.getJSON("/json/search.json", function (search_data) {
                            toString_detil1(search_data, data);

                            function toString_detil1(search, data) {
                                for (let key in search.areas) {
                                    if (data.list[0].housearea == key) {
                                        data.list[0].housearea = search.areas[key];
                                    }
                                }
                                for (let key in search.style) {
                                    if (data.list[0].style == key) {
                                        data.list[0].style = search.style[key];
                                    }
                                }
                                for (let key in search.payway) {
                                    if (data.list[0].payway == key) {
                                        data.list[0].payway = search.payway[key];
                                    }
                                }
                                for (let key in search.housestyle) {
                                    if (data.list[0].housestyle == key)
                                        data.list[0].housestyle = search.housestyle[key];
                                }
                                showdetail1(data.list[0]);
                            }

                            function showdetail1(data) {
                                let getTpl = demo.innerHTML
                                    , view = document.getElementById('detail');
                                laytpl(getTpl).render(data, function (html) {
                                    view.innerHTML = html;
                                    // localStorage.removeItem("houseid");
                                });
                            }
                        });
                    });
                } else {
                    toString_detil(search, data);
                }
            }

            function toString_detil(search, data) {
                for (let key in search.areas) {
                    if (data.list[0].housearea == key) {
                        data.list[0].housearea = search.areas[key];
                    }
                }
                for (let key in search.style) {
                    if (data.list[0].style == key) {
                        data.list[0].style = search.style[key];
                    }
                }
                for (let key in search.payway) {
                    if (data.list[0].payway == key) {
                        data.list[0].payway = search.payway[key];
                    }
                }
                for (let key in search.housestyle) {
                    if (data.list[0].housestyle == key)
                        data.list[0].housestyle = search.housestyle[key];
                }
                showdetail(data.list[0]);
            }

            function showdetail(data) {
                let getTpl = demo.innerHTML
                    , view = document.getElementById('detail');
                laytpl(getTpl).render(data, function (html) {
                    view.innerHTML = html;
                    // localStorage.removeItem("houseid");
                });
            }
        }
    });
    $('#reservation_house').click(function () {
        let url = 'http://localhost:8080/reserve/add';
        try{
            token = get_LocalStorage('TOKEN').access_token;
        }catch (err){
            layer.msg('???????????????????????????');
            popwindows('reservation_house');
        }
        let houseid = localStorage.getItem("houseid");
        let userid = get_LocalStorage('USER').id;
        let house_user_id = $('#user_id').val();
        if (parseInt(house_user_id) === userid) {
            layer.msg('????????????????????????',{
                icon: 0,
            });
            return false;
        }
        let _content = '<div>' +
            '<span>??????????????????????????????2019-04-04</span><input class="layui-input timer" id="timer" placeholder="????????????"/>' +
            '</div>';
        layer.open({
            type: 1,
            btn: ['??????', '??????'],
            title: '??????????????????',
            content: _content,
            yes: function (index) {
                let time = $('#timer').val();
                let parm = {
                    'user_id': userid,
                    'house_id': houseid,
                    'create_time': new Date(),
                    'time': new Date(time),
                    'status': 100,
                    'access_token': token
                };
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: parm,
                    success: function (res) {
                        if (res.code === 200) {
                            layer.close(index);
                            layer.msg('????????????,???????????????', {
                                icon: 1,
                                time: 2000
                            }, function () {
                                window.location.href = '/index/reserveTenantManage';
                            });
                        }
                    },
                    error(res) {
                        layer.close(index);
                        layer.msg('??????????????????', {
                            icon: 2,
                            time: 2000 //2???????????????????????????????????????3??????
                        });
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        });

        layui.use('laydate', function () {
            let laydate = layui.laydate;
            laydate.render({
                elem: '#timer'
                , min: '2019-04-04 00:00:00'
                , max: '2099-06-16 23:59:59'
                , value: '2019-04-04 00:00:00'
                , istoday: true
                , choose: function (datas) {
                    end.min = datas; //???????????????????????????????????????????????????
                    end.start = datas //??????????????????????????????????????????
                }
                , type: 'datetime'
                , istime: true
                , format: 'yyyy-MM-dd HH:mm:ss'
                , btns: ['clear', 'confirm']
            });
        });


    });

    $('#order_add').click(function () {
        let url = 'http://localhost:8080/order/add';
        try{
            token = get_LocalStorage('TOKEN').access_token;
        }catch (err){
            layer.msg('???????????????????????????');
            popwindows('order_add');
        }

        let houseid = localStorage.getItem("houseid");
        let userid = get_LocalStorage('USER').id;
        let house_user_id = $('#user_id').val();
        if (parseInt(house_user_id) === userid) {
            layer.msg('???????????????????????????',{
                icon: 0,
            });
            return false;
        }
        layer.open({
            type: 1,
            id: 'ordertime_open',
            resize: false,
            btn: ['??????', '??????'],
            title: '??????',
            content: $('#order_time'),
            area: ['350px', '280px'],
            shadeClose: true,
            shade: [0.3, '#393D49'],
            scrollbar: false,
            yes: function (index) {
                debugger;
                let time = $("#order_time select[name='ordertime']").val();
                let desc = $("#order_time textarea[name='desc']").val();
                let now = new Date();
                switch (time) {
                    case '1':
                        now.setDate(now.getFullYear() + 1);
                        break;
                    case '2':
                        now.setDate(now.getFullYear() + 2);
                        break;
                    case '3':
                        now.setDate(now.getFullYear() + 3);
                        break;
                }
                let parm = {
                    'user_id': userid,
                    'create_time': new Date(),
                    'house_id': houseid,
                    'start_time': new Date(),
                    'end_time': now,
                    'status': 0,
                    'remark': desc == null ? "???" : desc,
                    'access_token': token
                };
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: parm,
                    success: function (res) {
                        console.log(res);
                        if (res.code === 200) {
                            layer.close(index);
                            layer.msg('????????????,???????????????', {
                                icon: 1,
                                time: 2000 //2???????????????????????????????????????3??????
                            }, function () {
                                window.location.href = '/showTentantManage';
                            });
                        }
                    },
                    error(res) {
                        layer.close(index);
                        layer.msg('???????????????????????????????????????????????????', {
                            icon: 2,
                            time: 2000 //2???????????????????????????????????????3??????
                        });
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    });

    function get_LocalStorage(key) {
        let data = JSON.parse(localStorage.getItem(key));
        if (data !== null) {
            // debugger
            if (data.expirse != null && data.expirse < new Date().getTime()) {
                localStorage.removeItem(key);
            } else {
                return data.value;
            }
        }
        return null;
    }

    Date.prototype.format = function (fmt) {
        let o = {
            "M+": this.getMonth() + 1,                 //??????
            "d+": this.getDate(),                    //???
            "h+": this.getHours(),                   //??????
            "m+": this.getMinutes(),                 //???
            "s+": this.getSeconds(),                 //???
            "q+": Math.floor((this.getMonth() + 3) / 3), //??????
            "S": this.getMilliseconds()             //??????
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (let k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

});
