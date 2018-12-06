function plugins(map) {
    //异步加载定位、3D罗盘插件
    AMap.plugin([
        'AMap.Geolocation',
        'AMap.ControlBar',
        'AMap.Autocomplete'
    ], function () {
        var geolocation = new AMap.Geolocation({
            enableHighAccuracy: true, //是否使用高精度定位，默认:true
            timeout: 10000, //超过10秒后停止定位，默认：5s
            buttonPosition: 'RB', //定位按钮的停靠位置
            buttonOffset: new AMap.Pixel(10, 20), //定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true //定位成功后是否自动调整地图视野到定位点

        });
        //添加定位
        map.addControl(geolocation);
        // 添加 3D 罗盘控制
        map.addControl(new AMap.ControlBar());
        geolocation.getCurrentPosition();


        //输入提示
        map.addControl(new AMap.Autocomplete({
            input: "filterinput"
        }));


    });



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
        if (!marker) {
            marker = new AMap.Marker();
            map.add(marker);
        }
        marker.setPosition(lnglat);

        geocoder.getAddress(lnglat, function (status, result) {
            if (status === 'complete' && result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address').value = address;
            } else {
                alert(JSON.stringify(result))
            }
        });
    }



}