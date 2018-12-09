/**
 * 插件：
 * 定位            AMap.Geolocation
 * 地理位置         AMap.Geocoder
 * 3D罗盘控制       AMap.ControlBar
 * 输入框提示       AMap.Autocomplete
 * poi             AMap.PlaceSearch
 * 高级窗口         AMap.AdvancedInfoWindow
 * 鼠标，右键菜单    AMap.MouseTool
 */
//异步加载定位、3D罗盘插件
AMap.plugin([
    'AMap.Geolocation',
    'AMap.Geocoder',
    'AMap.ControlBar',
    'AMap.Autocomplete',
    'AMap.PlaceSearch',
    'AMap.AdvancedInfoWindow',
    'AMap.MouseTool'
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
            top: '31rem',
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
        var infoWindow = new AMap.InfoWindow({
            offset: new AMap.Pixel(0, -30)
        });


        var marker = new AMap.Marker({ //加点
            map: map,
            position: e.poi.location
        });
        marker.extData = {
            'getLng': e.poi.location['lng'],
            'getLat': e.poi.location['lat'],
            'name': e.poi.name,
            'address': e.poi.address
        }; //自定义想传入的参数
        marker.emit('click', {
            target: marker
        });

        marker.on("click", function (e) {
            infoWindow.setContent(String(e.target.extData['name']) + '<br />' + String(e.target.extData['address'])); //点击以后窗口展示的内容
            infoWindow.open(map, e.target.getPosition());
        });
        map.add(marker);





    }); //autoComplete, 'select', function (e)


}); //plugin function



//显示单击坐标
log.success("鼠标单击显示改点坐标");
map.on('click', function (e) {
    document.getElementById('lnglat').value = e.lnglat;
    regeoCode();
});



//逆地理编码（坐标->地址）
var marker;
var geocoder;

function regeoCode() {
    if (!geocoder) {
        geocoder = new AMap.Geocoder();
    }
    var lnglat = document.getElementById('lnglat').value.split(',');
    //添加标注
    // if (!marker) {
    //     marker = new AMap.Marker();
    //     map.add(marker);
    // }
    // marker.setPosition(lnglat);

    geocoder.getAddress(lnglat, function (status, result) {
        if (status === 'complete' && result.regeocode) {
            var address = result.regeocode.formattedAddress;
            document.getElementById('address').value = address;
        } else {
            alert(JSON.stringify(result))
        }
    });
} //function regeoCode()
function plugins(map) {




} //function plugins(map)