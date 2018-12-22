/**
 * 插件：
 * 定位            AMap.Geolocation
 * 地理位置         AMap.Geocoder
 * 3D罗盘控制       AMap.ControlBar
 * 输入框提示       AMap.Autocomplete
 * poi             AMap.PlaceSearch
 * 高级窗口         AMap.AdvancedInfoWindow
 * 鼠标，右键菜单    AMap.MouseTool
 * 不行导航         AMap.Walking
 */
var walking;
var clickLocation;
//异步加载定位、3D罗盘插件
AMap.plugin([
    'AMap.Geolocation',
    'AMap.Geocoder',
    'AMap.ControlBar',
    'AMap.Autocomplete',
    'AMap.PlaceSearch',
    'AMap.AdvancedInfoWindow',
    'AMap.MouseTool',
    'AMap.Walking'
], function () {
    /**
     * 定位
     */
    var geolocation = new AMap.Geolocation({
        enableHighAccuracy: true, //是否使用高精度定位，默认:true
        timeout: 10000, //超过10秒后停止定位，默认：5s
        buttonPosition: 'RB', //定位按钮的停靠位置
        buttonOffset: new AMap.Pixel(38, 20), //定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        zoomToAccuracy: true //定位成功后是否自动调整地图视野到定位点

    });
    map.addControl(geolocation);
    /**
     * 3D 罗盘控制
     */
    map.addControl(new AMap.ControlBar({
        position: {
            top: '20rem',
            right: '10px'
        }
    }));
    //初始化时候不自动定位
    //geolocation.getCurrentPosition();


    /**
     * 输入提示
     */
    var autoComplete = new AMap.Autocomplete({
        input: "filterinput",
        city: "威海",
        citylimit: true
    });

    map.addControl(autoComplete);
    //点击提示栏里面的地址会自动定位到所在地点并标注
    AMap.event.addListener(autoComplete, 'select', function (e) {
        /**
         * 返回值e为一个对象，包含两个属性值：对象poi和字符串type。以北京西站为例,其poi为
        poi:{
          adcode:"110106",
          address:"莲花池东路118号",
          district:"北京市丰台区",
          id:"B000A83M61",
          name:"北京西站",
          typecode:"150200",
          location:{
              I:116.32167900000002,
              L:39.895045,
              lat:39.895045,
              lng:116.321679,
          }
        }
         */
        if (e.poi && e.poi.location) {
            map.setZoom(17);
            map.setCenter(e.poi.location);
        }
        //信息窗口
        var infoWindo
        // 使用clearMap方法删除所有覆盖物
        map.clearMap();
        var marker = new AMap.Marker({ //加点
            position: e.poi.location
        });
        marker.extData = {
            'getLng': e.poi.location['lng'],
            'getLat': e.poi.location['lat'],
            'name': e.poi.name,
            'address': e.poi.address
        }; //自定义想传入的参数

        marker.on("click", function (e) {
            clickLocation = String(e.target.extData['getLng']) + ' ' + String(e.target.extData['getLat']);
            //实例化信息窗体
            var title = String(e.target.extData['name']),
                content = [];
            content.push("<img src='../images/sdu.png'>地址：" + String(e.target.extData['address']));
            content.push("经度：" + String(e.target.extData['getLng']));
            content.push("纬度：" + String(e.target.extData['getLat']));
            content.push("<button class='layui-btn layui-btn-normal layui-btn-xs' onclick='comments()'>查看此处留言</button><button class='layui-btn layui-btn-normal layui-btn-xs' onclick='writeAddressMessage()' style='margin-left:50px;'>写留言</button>");
            infoWindow = new AMap.InfoWindow({
                isCustom: true, //使用自定义窗体
                content: createInfoWindow(title, content.join("<br/>")),
                offset: new AMap.Pixel(16, -45)
            });
            infoWindow.open(map, e.target.getPosition());
        });
        map.add(marker);





    }); //autoComplete, 'select', function (e)

    walking = new AMap.Walking({
        panel: 'panel',
        autoFitView: true,
        map: map
    });


}); //plugin function