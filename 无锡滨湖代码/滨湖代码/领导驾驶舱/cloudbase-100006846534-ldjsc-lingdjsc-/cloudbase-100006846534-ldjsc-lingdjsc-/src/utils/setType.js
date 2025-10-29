/**
 * 轻量化设置->设置类型
 * @file 目标格式：3dt
 * @date 2023.02.20 14:25
 * @author yuanhaijun
 */

// 设置类型（下拉）
let Options = [
    { label: "建筑", value: '1' },
    { label: "室内", value: '2' },
    { label: "建筑体块", value: '3' },
    { label: "地面（道路、绿地、桥梁、小品）", value: '4' },
    { label: "水系", value: '5' },
    { label: "树", value: '6' },
    { label: "地下管线", value: '7' }
]

// 下拉值对应的属性（默认值/禁用启用状态）
const Attribute = {
    1: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': true,
            'disable': false
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': false
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': false
        },
        'splitOSGB': {
            'default': false,
            'disable': true

        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': false
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': 120,
            'disable': false
        },
        'automaticGlazing': {
            'default': false,
            'disable': false
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': false
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': false
        },
        'turnOffLighting': {
            'default': false,
            'disable': false
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    },
    2: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': true,
            'disable': false
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': false
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': false
        },
        'splitOSGB': {
            'default': false,
            'disable': true

        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': true
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': 120,
            'disable': false
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': true
        },
        'turnOffLighting': {
            'default': false,
            'disable': false
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    },
    3: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': false,
            'disable': true
        },
        'removeRemoteModels': {
            'default': false,
            'disable': true
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': true
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': false
        },
        'splitOSGB': {
            'default': false,
            'disable': true

        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': false
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': true
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': 0.02,
            'disable': false
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': 300,
            'disable': false
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': true
        },
        'turnOffLighting': {
            'default': false,
            'disable': true
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    },
    4: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': true,
            'disable': false
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': false
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': true
        },
        'splitOSGB': {
            'default': false,
            'disable': true
        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': false
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': 150,
            'disable': false
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': false
        },
        'turnOffLighting': {
            'default': false,
            'disable': true
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    },
    5: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': false,
            'disable': true
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': true
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': true
        },
        'splitOSGB': {
            'default': false,
            'disable': true
        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': true
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': '',
            'disable': true
        },
        'gridSize_100': {
            'default': '',
            'disable': true
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': true
        },
        'turnOffLighting': {
            'default': false,
            'disable': true
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    },
    6: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': false,
            'disable': true
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': false
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': true
        },
        'splitOSGB': {
            'default': false,
            'disable': true
        },
        'interactiveCollision': {
            'default': false,
            'disable': false
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': true
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': '',
            'disable': true
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': true
        },
        'turnOffLighting': {
            'default': false,
            'disable': false
        },
        'customTreeLibrary': {
            'default': false,
            'disable': false
        }
    },
    7: {
        // LTWT：cb_ltqjccjdy
        'lodPyramid': {
            'default': true,
            'disable': false
        },
        'removeRemoteModels': {
            'default': false,
            'disable': false
        },
        'forceBackClipping': {
            'default': false,
            'disable': false
        },
        'distanceFieldShadows': {
            'default': false,
            'disable': true
        },
        'recalculateNormalDirection': {
            'default': false,
            'disable': true
        },
        'splitOSGB': {
            'default': false,
            'disable': true
        },
        'interactiveCollision': {
            'default': false,
            'disable': true
        },
        'objectsCanBePicked': {
            'default': false,
            'disable': true
        },
        'optimizeMapPerformance': {
            'default': false,
            'disable': false
        },
        // LTWT
        'eliminateSmallBuilding': {
            'default': '',
            'disable': true
        },
        'eliminateSmallObjects': {
            'default': 8,
            'disable': false
        },
        'gridSize_100': {
            'default': 150,
            'disable': false
        },
        'automaticGlazing': {
            'default': false,
            'disable': true
        },
        'appointGlassMaterial': {
            'default': '',
            'disable': true
        },
        'dynamicWaterMap': {
            'default': '',
            'disable': false
        },
        'turnOffLighting': {
            'default': false,
            'disable': true
        },
        'customTreeLibrary': {
            'default': false,
            'disable': true
        }
    }
}


export default {
    Options,
    Attribute
}