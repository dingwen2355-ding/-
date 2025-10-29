import * as echarts from 'echarts'
//import 'echarts-liquidfill';

/**
 * 图表颜色配置
 */
const oChartColorConf = {
    main: "#8FDCE2", //主色：用于 tooltip 的 lineStyle
    legendLabel: '#8FDCE2', //图例说明文字
    lengendInativeLabel: '#91AEAF', //禁用的图例说明文字
    axisLabel: '#8FDCE2', //坐标轴刻度文字
    axisName: "#8FDCE2", //坐标轴文字（单位）
    gridLine: "rgba(168,252,255,.2)", //网格线
    aData: [
        '#A8FCFF', '#FFBE34', '#49D2CE', '#BDDB68', '#57ADF8', '#FFED94', '#FF7E7E', '#97BDFF', '#C1C1C1', '#BC98FF',
    ],
    aGradient: [
        //渐变色
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(168,252,255,1)' },
            { offset: 0.6, color: 'rgba(168,252,255,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(255,190,52,1)' },
            { offset: 0.6, color: 'rgba(255,190,52,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(73,210,206,1)' },
            { offset: 0.6, color: 'rgba(73,169,64,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(189,219,104,1)' },
            { offset: 0.6, color: 'rgba(189,219,104,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(87,173,248,1)' },
            { offset: 0.6, color: 'rgba(87,173,248,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(255,237,148,1)' },
            { offset: 0.6, color: 'rgba(255,237,148,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(255,126,126,1)' },
            { offset: 0.6, color: 'rgba(255,126,126,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(151,189,255,1)' },
            { offset: 0.6, color: 'rgba(151,189,255,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(193,193,193,1)' },
            { offset: 0.6, color: 'rgba(193,193,193,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(188,152,255,1)' },
            { offset: 0.6, color: 'rgba(188,152,255,.36)' },
        ]),
    ],
    aGradientHor: [
        //渐变色(横向)
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(168,252,255,1)' },
            { offset: 0.6, color: 'rgba(168,252,255,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(255,190,52,1)' },
            { offset: 0.6, color: 'rgba(255,190,52,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(73,210,206,1)' },
            { offset: 0.6, color: 'rgba(73,169,64,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(189,219,104,1)' },
            { offset: 0.6, color: 'rgba(189,219,104,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(87,173,248,1)' },
            { offset: 0.6, color: 'rgba(87,173,248,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(255,237,148,1)' },
            { offset: 0.6, color: 'rgba(255,237,148,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(255,126,126,1)' },
            { offset: 0.6, color: 'rgba(255,126,126,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(151,189,255,1)' },
            { offset: 0.6, color: 'rgba(151,189,255,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(193,193,193,1)' },
            { offset: 0.6, color: 'rgba(193,193,193,.36)' },
        ]),
        new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: 'rgba(188,152,255,1)' },
            { offset: 0.6, color: 'rgba(188,152,255,.36)' },
        ]),
    ],
};

/**
 * e-charts 初始化封装框架
 * @param {object} el 容器
 * @param {object} opts 配置参数
 * @param {string} opts.type 图表类型
 * @param {*=} opts.data 数据
 * @param {object} opts.conf 与数据无关的绘制配置参数
 * - notMerge {boolean=} 是否不跟之前设置的 option 进行合并，默认为 true（根据实际需要默认值与 echarts 默认相反），即不合并。
 * @param {boolean=} opts.listenResize 是否监听window.resize事件。动态图表集合或个数过多的图表，建议设为 false 在页面中统一管理以提高性能
 * @param {boolean=} opts.resizeReset 尺寸变化是否需要重置图表（图表配置中使用了容器尺寸，在尺寸变化时，需要重新计算配置），默认为 false
 * @param {function=} opts.optionFactory(data,conf) 返回object。用于生成用户自定义的echart配置。
 * @param {function=} opts.afterInit(oChartApi) echart初始化后的回调
 * - oChartApi {object} echart实例化后的对象
 * @param {function=} opts.afterSetData(oChartApi) echart数据设置后的回调
 * - oChartApi {object} echart实例化后的对象
 * @return {object} obj 返回对象
 * @rtn {object} oChartApi echart实例化后的对象
 * @rtn {function} fSetData(data,conf) 设置配置项以及数据，data 不传则直接刷新图表
 * - data {*=} 同 opts.data，将覆盖原数据。不传时将清空图表当前数据。
 * - conf {object=} 同 opts.conf，在原配置基础上扩展。
 * @rtn {function} fResize() 重算图表尺寸
 * @rtn {function} fDestroy() 销毁图表，目前适用场景为由列表数据遍历绘制多个图表时，数据变化时需销毁旧的图表
 */
const fInitEcharts = function (el, opts) {
    opts = {
        //type: '',
        //data: []
        conf: {}, //与绘图数据无关的配置参数
        listenResize: true, //是否监听window.resize事件
        resizeReset: false, //尺寸变化是否需要重置图表（图表配置中使用了容器尺寸，在尺寸变化时，需要重新计算配置）
        optionFactory: null, //生成绘图option的自定义方法
        afterInit: $.noop,
        afterSetData: $.noop,
        ...opts,
    };
    let oChartApi,
        api,
        nWidth,
        nHeight,
        fOptionFactory;
    let oConf, sGuid, chartData; //图表数据，不定义类型前缀，实际类型由用户传入的具体数据决定
    // 点线图的配置 TODO
    //const fLineOption = function(data, conf) {
    //    return {};
    //};
    // 获取配置项
    const fGetOption = function (data) {
        let option;
        if (fOptionFactory) {
            option = fOptionFactory(data, oConf);
            if (option) {
                return option;
            }
        }
        switch (opts.type) {
            //内置的默认图表配置
            //case 'line': // 点线图的配置 TODO
            //    return fLineOption(data, oConf);
            default:
                return null;
        }
    };
    // 重新计算图表大小
    const fResize = function () {
        oChartApi.resize();
    };
    // 销毁
    const fDestroy = function () {
        if (oChartApi) {
            oChartApi.dispose();
            oChartApi = api.oChartApi = undefined;
            if (sGuid) {
                $(window).off('resize.' + sGuid);
            }
        }
    };
    // 设置配置项以及数据，data 不传则直接刷新图表
    const fSetData = function (data, conf) {
        let option;
        if (!oChartApi) {
            nWidth = el.width();
            nHeight = el.height();
            oChartApi = api.oChartApi = echarts.init(el[0]);
            // console.log('init');
            if (opts.listenResize) {
                if (!sGuid) {
                    sGuid = $.generateGuid('chart');
                }
                $(window).on('resize.' + sGuid, function () {
                    //console.log('resieze');
                    if (opts.resizeReset) {
                        //图表配置中用到了容器宽高，需要重新重新计算
                        setTimeout(function () {
                            //避免因flex布局等原因造成页面渲染延迟，无法获取正确的尺寸
                            nWidth = el.width();
                            nHeight = el.height();
                            fSetData(chartData);
                            oChartApi.resize();
                        }, 0);
                    } else {
                        oChartApi.resize();
                    }
                });
            }
            opts.afterInit && opts.afterInit(oChartApi);
        }
        chartData = data;
        //首先扩展配置
        oConf = {
            ...oConf,
            ...conf,
        };
        if (chartData) {
            option = fGetOption(chartData); //返回 undefined 时不进行绘制
        }
        if (option) {
            oChartApi.setOption(option, oConf.notMerge !== false); // 默认不合并 notMerge为false时合并
            opts.afterSetData && opts.afterSetData(oChartApi);
        } else {
            oChartApi.clear();
        }
    };
    const fInit = function () {
        oConf = opts.conf;
        fOptionFactory = opts.optionFactory;
        if (opts.data) {
            fSetData(opts.data);
        }
    };
    api = {
        //oChartApi: null,//初始化时赋值
        fSetData: fSetData,
        fResize: fResize,
        fDestroy: fDestroy, //销毁图表，目前适用场景为由列表数据遍历绘制多个图表时，数据变化时需销毁旧的图表
    };
    fInit();
    return api;
};

export { oChartColorConf, fInitEcharts };