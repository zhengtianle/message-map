function viewAuthor() {
    layui.use('layer', function () {
        var layer = layui.layer;

        layer.open({
            title: '关于作者',
            content: "<div>学校：山东大学威海校区<br />年级：2016级<br />专业：软件工程<br />github：<a href='https://github.com/zhengtianle' title='https://github.com/zhengtianle'>zhengtianle</a><br />个人网站：<a href='https://zhengtianle.github.io' title='https://zhengtianle.github.io'>郑天乐的个人网站</a></div>"
        });
    });
}

function worldComments() {
    layui.use(['layer', 'flow'], function () {
        var layer = layui.layer;
        var flow = layui.flow;

        layer.open({
            type: 1,
            title: '世界留言',
            closeBtn: false,
            id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,
            offset: 'auto',
            btn: ['关闭'],
            //content: '<ul style="padding: 20px; height: 300px; line-height: 22px; font-size:15px; font-weight: 500;" class="flow-default" id="LAY_demo1"></ul>',
            content: '<div id="LAY_scroll" style="height:350px;"><table class="layui-table" lay-even="" lay-skin="nob"><thead><tr><th>昵称</th><th>时间</th><th>给山威的留言</th><th>点赞</th></tr> </thead><tbody id="LAY_demo1"></tbody></table></div>'
        });

        flow.load({
            elem: '#LAY_demo1' //流加载容器
                ,
            scrollElem: '#LAY_scroll' //滚动条所在元素，一般不用填，此处只是演示需要。
                ,
            isAuto: true,
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
                $.ajax({
                    url: 'http://localhost:8080/getMessages',
                    type: "post",
                    data: {
                        "page": page,
                        "location": "sduwh"
                    },
                    success: function (data) {
                        var json = eval("(" + data + ")");
                        console.log("返回的数据：" + json);
                        if (json.result === "success") {
                            var message = eval("(" + json.content + ")");
                            for (var p in message) {
                                lis.push('<tr><td>'+message[p].username+'</td><td>'+message[p].time+'</td><td>'+message[p].content+'</td><td>'+message[p].stars+'</td></tr>')
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