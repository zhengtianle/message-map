function viewAuthor() {
    layui.use('layer', function () {
        var layer = layui.layer;

        layer.open({
            title: '关于作者',
            content: "<div>学校：山东大学威海校区<br />年级：2016级<br />专业：软件工程<br />github：<a href='https://github.com/zhengtianle' title='https://github.com/zhengtianle'>zhengtianle</a><br />个人网站：<a href='https://zhengtianle.github.io' title='https://zhengtianle.github.io'>郑天乐的个人网站</a></div>"
        });
    });
}

function worldComments(location) {
    layui.use(['layer', 'flow'], function () {
        var layer = layui.layer;
        var flow = layui.flow;

        var world_comments = layer.open({
            type: 1,
            title: location + '留言',
            closeBtn: false,
            area: ['800px', '500px'],
            id: 'LAY_world_message' //设定一个id，防止重复弹出
                ,
            offset: 'auto',
            btn: ['写留言', '关闭'],
            yes: function (index, layero) {
                var userInfo = eval("(" + getCookie("userInfo") + ")");
                console.log(userInfo);
                if (userInfo) { //已登录
                    //按钮【写留言】的回调
                    writeMessage(location);
                } else {
                    console.log("请先登录");
                    layer.msg("请先登录");
                }
            },
            btn2: function (index, layero) {
                //按钮【关闭】的回调
                layer.close(world_comments);
            },
            //content: '<ul style="padding: 20px; height: 300px; line-height: 22px; font-size:15px; font-weight: 500;" class="flow-default" id="LAY_demo1"></ul>',
            content: '<div id="LAY_scroll"><table class="layui-table" lay-even="" lay-skin="nob"><thead><tr><th>昵称</th><th>时间</th><th>给山威的留言</th><th>点赞</th></tr> </thead><tbody id="LAY_demo1"></tbody></table></div>'
        });

        flow.load({
            elem: '#LAY_demo1' //流加载容器
                ,
            scrollElem: '#LAY_scroll' //滚动条所在元素，一般不用填，此处只是演示需要。
                ,
            done: function (page, next) { //执行下一页的回调

                // //模拟数据插入
                // setTimeout(function () {
                //     var lis = [];
                //     for (var i = 0; i < 8; i++) {
                //         //lis.push('<li>' + '2018-12-17: ' + '@' + '我不是冯宝宝' + ': ' + 'Always have, always will' + '</li>')
                //         lis.push('<tr><td>冯宝宝</td><td>2016-11-29</td><td>人生就像是一场修行</td><td>10</td></tr>')
                //     }

                //     //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                //     //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                //     next(lis.join(''), page < 2); //假设总页数为 10
                // }, 500);
                var lis = [];
                var uid;
                var userInfo = eval("(" + getCookie("userInfo") + ")");
                if(userInfo) {
                    uid = userInfo.uid;
                } else {
                    uid = -1;
                }
                $.ajax({
                    url: 'http://localhost:8080/getMessages',
                    type: "post",
                    data: {
                        "page": page,
                        "location": location,
                        "uid": uid
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        console.log("返回的数据：" + json);
                        if (json.result === "success") {
                            var message = eval("(" + json.content + ")");
                            for (var p in message) {
                                if(message[p].liked == true) {
                                    //已点赞，置为橙色
                                    lis.push('<tr><td>' + message[p].username + '</td><td>' + message[p].time + '</td><td>' + message[p].content + '</td><td><i class="layui-icon layui-icon-praise star" style="cursor:pointer;color:#E67E22;" onclick="star(this, ' + message[p].mid + ')">&nbsp;' + message[p].stars + '</i></td></tr>');
                                } else {
                                    lis.push('<tr><td>' + message[p].username + '</td><td>' + message[p].time + '</td><td>' + message[p].content + '</td><td><i class="layui-icon layui-icon-praise" style="cursor:pointer;" onclick="star(this, ' + message[p].mid + ')">&nbsp;' + message[p].stars + '</i></td></tr>');
                                }
                            }

                            //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                            //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                            next(lis.join(''), page < json.pages);
                        } else {
                            layer.msg("查询留言失败");
                        }

                    },
                    error: function (e) {
                        layer.msg("提交留言查询请求失败");
                    }


                }); //ajax
            }
        });

    });
}

/**
 * 写留言
 */

function writeMessage(location) {
    layui.use(['layer'], function () {
        var layer = layui.layer;


        var write_message = layer.open({
            type: 1,
            title: '写留言',
            closeBtn: false,
            area: ['500px', '300px'],
            id: 'LAY_write_message', //设定一个id，防止重复弹出
            offset: 'auto',
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                //按钮【确定】的回调
                var message = $("#writedMessage").val();
                if (message === "") {
                    layer.msg("请输入内容");
                } else {
                    $.ajax({
                        url: 'http://localhost:8080/leaveAMessage',
                        type: "post",
                        data: {
                            "uid": eval("(" + getCookie("userInfo") + ")").uid,
                            "content": message,
                            "location": location
                        },
                        success: function (data) {
                            var json = eval("(" + data + ")");
                            if (json.result === "success") {
                                layer.msg("留言成功");
                                layer.close(write_message);
                            } else {
                                layer.msg("留言失败");
                            }
                            console.log('提交留言成功！');
                        },
                        error: function (e) {
                            console.log('提交留言失败：' + e);
                        }
                    }); //ajax
                }
            },
            btn2: function (index, layero) {
                //按钮【取消】的回调
                layer.close(write_message);
            },
            content: '<textarea placeholder="请输入内容" id="writedMessage" class="layui-textarea" style="height:200px;"></textarea>'
        });
    });

}

/**
 * (当前元素, 留言id)
 */
function star(element, mid) {
    layui.use(['layer'], function () {
        var layer = layui.layer;

        var userInfo = eval("(" + getCookie("userInfo") + ")");
        if (!userInfo) {
            layer.open({
                content: "<div>请先登录！</div>",
                btn: ['登录', '关闭'],
                yes: function (index, layero) {
                    //跳转登录界面
                    window.location.href = "login.html";
                },
                btn2: function (index, layero) {
                    //关闭按钮
                }
            });
            return;
        }

        
        //已登录
        if (element.className == "layui-icon layui-icon-praise star") {
            //已点赞
            layer.msg("不可重复点赞!");
        } else {
            //未点赞
            $.ajax({
                url: 'http://localhost:8080/starAMessage',
                type: "post",
                data: {
                    "uid": eval("(" + getCookie("userInfo") + ")").uid,
                    "mid": mid
                },
                success: function (data) {
                    var json = data;
                    console.log("返回的数据：" + json);
                    if (json.result === "success") {
                        layer.msg("点赞数+1");
                        //element.className = "layui-icon layui-icon-praise star" //改变已点赞的颜色
                        element.style.cssTest = "cursor:pointer;color:#E67E22;";
                        element.className = "layui-icon layui-icon-praise star";
                        element.innerText = " " + (parseInt(element.innerText) + 1);
                    } else {
                        layer.msg("点赞失败");
                    }

                },
                error: function (e) {
                    layer.msg("提交留言查询请求失败");
                }


            }); //ajax
        }
    });
}