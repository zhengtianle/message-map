/**
 * 右键菜单
 * 设为起点
 * 设为途经点
 * 设为终点
 */
//创建右键菜单
var contextMenu = new AMap.ContextMenu();
var contextMenuPositon;         //菜单显示位置
var startMarker;                //起点标记
var endMarker;                  //终点标记
var startLnglat = null;         //起点坐标
var endLnglat = null;           //终点坐标
var hasStartMarker = false;     //地图是否已有起点
var hasEndMarker = false;       //地图是否已有终点
var viewRoutePlan = false;      //显示起终点框
var viewMenu = false;           //显示右键菜单
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
    }); //startMarker

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
    }); //endMarker
}); //AMapUI.load


//右键菜单添加起点标记
contextMenu.addItem("设为起点", function (e) {
    if(viewRoutePlan == false){
        selectRoute()
    }
    if(hasStartMarker){
        map.remove(startMarker);
    }
    startMarker.setPosition(contextMenuPositon);
    map.add(startMarker);
    startLnglat = contextMenuPositon;
    routeIpToAddress(startLnglat, 0);
    hasStartMarker = true;
}, 3);

//右键添加终点标记
contextMenu.addItem("设为终点", function (e) {
    if(viewRoutePlan == false){
        selectRoute()
    }
    if(hasEndMarker){
        map.remove(endMarker);
    }
    endMarker.setPosition(contextMenuPositon);
    map.add(endMarker);
    endLnglat = contextMenuPositon;
    routeIpToAddress(endLnglat, 1);
    hasEndMarker = true;
}, 3);

//地图绑定鼠标右击事件——弹出右键菜单
map.on('rightclick', function (e) {
    contextMenu.open(map, e.lnglat);
    contextMenuPositon = e.lnglat;
    viewMenu = true;
});

map.on('click', function (e) {
    //点击地图左键关闭右键菜单
    if(viewMenu == true){
        contextMenu.close();
        viewMenu = false;
    }
});

/**
 * 根据起终点坐标规划步行路线
 */
function routePlan() {
    if (document.getElementById("startRouteOrNot").innerText == "开始规划") {
        if (startLnglat != null && endLnglat != null) {
            walking.search(startLnglat, endLnglat, function (status, result) {
                // result即是对应的步行路线数据信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_WalkingResult
                if (status != 'complete') {
                    alert('步行路线数据查询失败' + result)
                }
            });
            map.remove(startMarker);
            hasStartMarker = false;
            map.remove(endMarker);
            hasEndMarker = false;
            document.getElementById("startRouteOrNot").innerText = "结束规划";
        } else {
            alert("请选择正确的起点与终点！");
        }
    } else {
        map.add(startMarker);
        hasStartMarker = true;
        map.add(endMarker);
        hasEndMarker = true;
        document.getElementById("startRouteOrNot").innerText = "开始规划";
        walking.clear();
    }
}

var geocoder;

function routeIpToAddress(lnglat, where) {
    if (!geocoder) {
        geocoder = new AMap.Geocoder();
    }
    geocoder.getAddress(lnglat, function (status, result) {
        if (status === 'complete' && result.regeocode) {
            var address = result.regeocode.formattedAddress;
            //去掉字符串最后面的“山东大学威海校区”字样
            if (address.substring(address.length - 8) == "山东大学威海校区") {
                address = address.substring(0, address.length - 8);
            }
            if (where == 0) {
                $('#startPoint').val(address.substring(13));
            } else {
                $('#endPoint').val(address.substring(13));
            }
        } else {
            alert(JSON.stringify(result))
        }
    });

} //function regeoCode()

function deleteStartMarker() {
    if (startLnglat != null) {
        document.getElementById("startRouteOrNot").innerText = "开始规划";
        walking.clear();
        $('#startPoint').val("");
        map.remove(startMarker);
        hasStartMarker = false;
        startLnglat = null;
        if (endLnglat != null) {
            map.add(endMarker);
            hasEndMarker = true;
        }
    }
}

function deleteEndMarker() {
    if (endLnglat != null) {
        document.getElementById("startRouteOrNot").innerText = "开始规划";
        walking.clear();
        $('#endPoint').val("");
        map.remove(endMarker);
        hasEndMarker = false;
        endLnglat = null;
        if (startLnglat != null) {
            map.add(startMarker);
            hasStartMarker = true;
        }
    }
}

/**
 * 点击路径规划显示始终点input和开始规划按钮
 */
function selectRoute(){
    //没有显示路径规划菜单
    if(document.getElementById("routePlanDiv").style.display == 'none'){
        viewRoutePlan = true;
        document.getElementById("routePlanDiv").style.display = 'block';
    } else {
        viewRoutePlan = false;
        document.getElementById("routePlanDiv").style.display = 'none';
    }
}