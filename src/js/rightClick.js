/**
 * 右键菜单
 * 设为起点
 * 设为途经点
 * 设为终点
 */
//创建右键菜单
var contextMenu = new AMap.ContextMenu();
var contextMenuPositon;
var startMarker;
var endMarker;
var startLnglat;
var endLnglat;
AMapUI.load(['ui/overlay/SimpleMarker'], function (SimpleMarker) {
    var theme = "default";
    //内置的样式
    var iconStyles = SimpleMarker.getBuiltInIconStyles(theme);

    //创建起点marker（不加入地图
    startMarker = new SimpleMarker({
        iconTheme: theme,
        //使用内置的iconStyle
        iconStyle: iconStyles[17],

        //图标文字
        iconLabel: {
            innerHTML: "起",
            style: {
                color: '#FFF'
            }
        }
    });//startMarker

    //创建终点marker（不加入地图
    endMarker = new SimpleMarker({
        iconTheme: theme,
        //使用内置的iconStyle
        iconStyle: iconStyles[17],

        //图标文字
        iconLabel: {
            innerHTML: "终",
            style: {
                //颜色, #333, red等等，这里仅作示例，取iconStyle中首尾相对的颜色
                color: '#FFF'
            }
        }
    });//endMarker
});//AMapUI.load


//右键菜单添加起点标记
contextMenu.addItem("设为起点", function (e) {
    map.remove(startMarker);
    startMarker.setPosition(contextMenuPositon);
    map.add(startMarker);
    startLnglat = contextMenuPositon;
    document.getElementById("startPoint").innerHTML = regeoCode(startLnglat);
}, 3);

//右键添加终点标记
contextMenu.addItem("设为终点", function (e) {
    map.remove(endMarker);
    endMarker.setPosition(contextMenuPositon);
    map.add(endMarker);
    endLnglat = contextMenuPositon;
    document.getElementById("endPoint").innerHTML = regeoCode(endLnglat);
}, 3);

//地图绑定鼠标右击事件——弹出右键菜单
map.on('rightclick', function (e) {
    contextMenu.open(map, e.lnglat);
    contextMenuPositon = e.lnglat;
});


/**
 * 根据起终点坐标规划步行路线
 */
function routePlan(){
    //console.log(walking);
    walking.search(startLnglat, endLnglat, function(status, result) {
        // result即是对应的步行路线数据信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_WalkingResult
        if (status === 'complete') {
            log.success('绘制步行路线完成')
        } else {
            log.error('步行路线数据查询失败' + result)
        } 
    });
    map.remove(startMarker);
    map.remove(endMarker);
}