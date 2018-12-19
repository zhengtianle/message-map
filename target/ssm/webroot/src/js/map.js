//初始化地图对象，加载地图
var map = new AMap.Map('container', {
    resizeEnable: true,
    //mapStyle: 'amap://styles/5397ce8e5d172a33366db0676aa61a5c', //设置地图的显示样式
    pitch: 60, //3D视图视角
    viewMode: '3D',
    zooms: [3, 19], //地图显示的缩放级别范围
    zoom: 16,
    center: [122.058248, 37.532283],
    expandZoomRange: true, //设置为true的时候，zooms的最大级别在PC上可以扩大到20级
    showBuildingBlock: true //设置地图显示3D楼块效果
});

/**
 * 设置边界，脱离边界自动回归
 * 通过 new AMap.Bounds(southWest:LngLat, northEast:LngLat) 
 * 或者 map.getBounds() 获得地图Bounds信息
 */
var mybounds = new AMap.Bounds([122.050482, 37.525571], [122.066082, 37.541141]);
map.setLimitBounds(mybounds);