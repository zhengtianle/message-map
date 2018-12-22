//构造地点查询类,随便点击某一个点  开始======
var placeSearch = new AMap.PlaceSearch();
var infoWindow = new AMap.AdvancedInfoWindow({});
map.on('hotspotclick', function (result) {
    placeSearch.getDetails(result.id, function (status, result) {
        if (status === 'complete' && result.info === 'OK') {
            placeSearch_CallBack(result);
        }
    });
});

//回调函数
function placeSearch_CallBack(data) { //infoWindow.open(map, result.lnglat);
    var poiArr = data.poiList.pois;
    createContent(poiArr[0]);
}


function createContent(poi) { //信息窗体内容
    clickLocation = String(poi.location.lng) + ' ' + String(poi.location.lat)
    //实例化信息窗体
    var title = String(poi.name),
        content = [];
    content.push("<img src='../images/sdu.png'>地址：" + String(poi.address));
    content.push("经度：" + String(poi.location.lng));
    content.push("纬度：" + String(poi.location.lat));
    content.push("<button class='layui-btn layui-btn-normal layui-btn-xs' onclick='comments()'>查看此处留言</button><button class='layui-btn layui-btn-normal layui-btn-xs' onclick='writeAddressMessage()' style='margin-left:50px;'>写留言</button>");
    infoWindow = new AMap.InfoWindow({
        isCustom: true, //使用自定义窗体
        content: createInfoWindow(title, content.join("<br/>")),
        offset: new AMap.Pixel(16, -45)
    });
    infoWindow.open(map, poi.location);


}

function comments(){
    worldComments(clickLocation);
}

function writeAddressMessage() {
    //用当前坐标的经纬度当做location
    writeMessage(clickLocation);
}

//构建自定义信息窗体
function createInfoWindow(title, content) {
    var info = document.createElement("div");
    info.className = "custom-info input-card content-window-card";

    //可以通过下面的方式修改自定义窗体的宽高
    info.style.width = "300px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "https://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "https://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}

//关闭信息窗体
function closeInfoWindow() {
    map.clearInfoWindow();
}
//构造地点查询类,随便点击某一个点 结束======