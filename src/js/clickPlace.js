function clickPlace(map) {
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
        var info = [];
        info.push(String(poi.name) + String(poi.address) + String(poi.location.lng) + String(poi.location.lat));
        infoWindow = new AMap.InfoWindow({
            offset: new AMap.Pixel(0, 20),
            isCustom: false,
            content: info.join('<br>') //使用默认信息窗体框样式，显示信息内容
        });
        infoWindow.open(map, poi.location);
    }
    //构造地点查询类,随便点击某一个点 结束======
}