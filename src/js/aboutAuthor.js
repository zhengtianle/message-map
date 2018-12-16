function viewAuthor(){
    layui.use('layer', function(){
        var layer = layui.layer;
        
        layer.open({
            title: '关于作者'
            ,content: "<div>学校：山东大学威海校区<br />年级：2016级<br />专业：软件工程<br />github：<a href='https://github.com/zhengtianle' title='https://github.com/zhengtianle'>zhengtianle</a><br />个人网站：<a href='https://zhengtianle.github.io' title='https://zhengtianle.github.io'>郑天乐的个人网站</a></div>"
          });   
      });
}