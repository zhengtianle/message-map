function balloon(map) {

    //增加3D图层
    var object3Dlayer = new AMap.Object3DLayer({
        zIndex: 110,
        opacity: 1
    });

    //map.add(object3Dlayer);

    //坐标转换
    function lnglatToG20(lnglat) {
        lnglat = map.lngLatToGeodeticCoord(lnglat);
        lnglat.x = AMap.Util.format(lnglat.x, 3);
        lnglat.y = AMap.Util.format(lnglat.y, 3);
        return lnglat;
    }

    var lines = new AMap.Object3D.Line();
    var lineGeo = lines.geometry;

    var points3D = new AMap.Object3D.RoundPoints();
    points3D.transparent = true;
    var pointsGeo = points3D.geometry;

    var pointArray = pointLnglat();
    for (var p = 0; p < pointArray.length; p++) {
        var libraryLnglat = new AMap.LngLat(pointArray[p][0], pointArray[p][1]);
        var center = lnglatToG20(libraryLnglat);
        var size = Math.max(10, Math.round(Math.random() * 40));
        var height = -size * 30;

        // 连线
        lineGeo.vertices.push(center.x, center.y, 0);
        lineGeo.vertexColors.push(0, 1, 1, 1);
        lineGeo.vertices.push(center.x, center.y, height);
        lineGeo.vertexColors.push(0, 1, 1, 1);

        pointsGeo.vertices.push(center.x, center.y, 0); // 尾部小点
        pointsGeo.pointSizes.push(5);
        pointsGeo.vertexColors.push(0, 0, 1, 1);

        pointsGeo.vertices.push(center.x, center.y, height); // 空中点
        pointsGeo.pointSizes.push(size);
        pointsGeo.vertexColors.push(p * 0.029, p * 0.015, p * 0.01, 1);
    }

    points3D.borderColor = [0.4, 0.8, 1, 1];
    points3D.borderWeight = 3;
    object3Dlayer.add(lines);
    object3Dlayer.add(points3D);


    return object3Dlayer;
}
