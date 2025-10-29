/**
 * 轻量化设置->属性相关
 * @file 不同的（目标格式）对应的（源数据格式）属性不同
 * @date 2023.02.20 14:25
 * @author yuanhaijun
 */
// 源数据格式
export const SourceDataFormat = [
    { "code": "las", "codevalue": "ASPRSLidarDataExchangeFormat(LAS)" },
    { "code": "ifc", "codevalue": "IndustryFoundationClassSTEPFiles(IFC)" },
    { "code": "rvt", "codevalue": "AutoDeskRevit(RVT)" },
    { "code": "osgb2", "codevalue": "PhotoGrammetry(OSG倾斜)" },
    { "code": "fbx", "codevalue": "AutodeskFBX(FBX)" },
    { "code": "osgb0", "codevalue": "OpenSceneGraph(OSG模型)" },
    { "code": "osgb1", "codevalue": "OpenSceneGraph(pagedlod瓦片)" },
    { "code": "obj", "codevalue": "WavefrontOBJ(OBJ)" },
    { "code": "3ds", "codevalue": "Autodesk3ds(3DS)" },
    { "code": "stl", "codevalue": "StandardTriangle/TesselationLanguage(STL)" },
    { "code": "iges", "codevalue": "TheInitialGraphicsExchangeSpecification(IGES)" },
    { "code": "stp", "codevalue": "StandardfortheExchangeofProductModelData(STEP)" },
    { "code": "3dxml", "codevalue": "DassaultSystemes3DXML(3DXML)" },
    { "code": "dgn", "codevalue": "MicroStation(DGN)" },
    { "code": "max", "codevalue": "3dsMax2022(MAX)" }
]

// 默认包含的面板（3dtiles）
const DefFold_3dtiles = ['mxjbzh', 'cb_wlmj', 'cb_gz', 'cb_dc', 'inp_wyf', 'cb_qhdgs', 'inp_wd']
// 默认属性（3dtiles坐标系/轻量化设置）
const DefAttr_3dtiles = {
    // ENU
    'long': {},
    'lat': {},
    'height': {},
    // SRS
    'deviationX': {},
    'deviationY': {},
    'deviationZ': {},
    'coord': {},
    'prjFile': {},
    // LTWT：模型局部变换
    'translationX': {},
    'translationY': {},
    'translationZ': {},
    'rotateX': {},
    'rotateY': {},
    'rotateZ': {},
    'scaleX': {},
    'scaleY': {},
    'scaleZ': {},
    // LTWT：cb_wlmj
    'textureDense': {},
    // LTWT：cb_zt
    'minTileSize': {},
    'transparentMaps': {},
    // LTWT：cb_gz
    'rootNodeNum': {},
    'maxTileLevel': {},
    // LTWT：cb_dc
    'exportOP': {},
    'recalculateNormals': {},
    // LTWT：inp_wyf
    'tileMaxVertex': {},
    'leafTiled': {},
    'nonLeafTiled': {},
    // LTWT：cb_qhdgs
    'forceScaleMap': {},
    'marsCoord': {},
    'dracoCompress': {},
    'closeLighting': {},
    'twoSidedLighting': {},
    // LTWT：inp_wd
    'textureCompress': {},
    'dispatchFactor': {}
}

// 默认包含的面板（3dt）
const DefFold_3dt = ['cb_ltqjccjdy']
// 3dt_设置类型
const DefAttr_3dt_T = {
    // SRS
    'deviationX': {},
    'deviationY': {},
    'deviationZ': {},
    // LTWT
    'settingType': {},
    // LTWT：cb_ltqjccjdy
    'lodPyramid': {},
    'removeRemoteModels': {},
    'forceBackClipping': {},
    'distanceFieldShadows': {},
    'recalculateNormalDirection': {},
    'splitOSGB': {},
    'interactiveCollision': {},
    'objectsCanBePicked': {},
    'optimizeMapPerformance': {},
    // LTWT
    'eliminateSmallBuilding': {},
    'eliminateSmallObjects': {},
    'gridSize_100': {},
    'automaticGlazing': {},
    'appointGlassMaterial': {},
    'dynamicWaterMap': {},
    'turnOffLighting': {},
    'customTreeLibrary': {}
}
// 3dt_模型精度
const DefAttr_3dt_P = {
    // SRS
    'deviationX': {},
    'deviationY': {},
    'deviationZ': {},
    // LTWT
    'modelAccuracy': {},
    'lodPyramid': {
        'default': true // LOD金字塔（默认选中）
    },
    'interactiveCollision': {},
    'gridSize_5': {},
    'eliminateSmallObjects': {}
}


// 轻量化 -> 数据集类型
const DatasetType = [
    {
        'code': 'las',
        'codevalue': 'ASPRSLidarDataExchangeFormat(LAS)',
        'fold_3dtiles': ['ci_ydcssz'],
        'attribute_3dtiles': {
            // ENU
            'long': {},
            'lat': {},
            'height': {},
            // SRS
            'deviationX': {},
            'deviationY': {},
            'deviationZ': {},
            'coord': {},
            'prjFile': {},
            // LTWT：ci_ydcssz
            'lodLevel': {},
            'lodReductionRatio': {}
        },
        'fold_3dt': [],
        'attribute_3dt': {
            // SRS
            'deviationX': {},
            'deviationY': {},
            'deviationZ': {}
        }
    }, // 3dtiles/3dt（i3s1.6/i3s1.7 ——NO）
    {
        'code': 'ifc',
        'codevalue': 'IndustryFoundationClassSTEPFiles(IFC)',
        'fold_3dtiles': ['mxjbzh', 'cb_wlmj', 'cb_gz'],
        'attribute_3dtiles': {
            // ENU
            'long': {},
            'lat': {},
            'height': {},
            // SRS
            'deviationX': {},
            'deviationY': {},
            'deviationZ': {},
            'coord': {},
            'prjFile': {},
            // LTWT：模型局部变换
            'translationX': {},
            'translationY': {},
            'translationZ': {},
            'rotateX': {},
            'rotateY': {},
            'rotateZ': {},
            'scaleX': {},
            'scaleY': {},
            'scaleZ': {},
            // LTWT：cb_wlmj
            'textureDense': {},
            // LTWT：cb_zt
            'minTileSize': {},
            'transparentMaps': {},
            // LTWT：cb_gz
            'rootNodeNum': {},
            'maxTileLevel': {}
        }
    }, // 3dtiles/3dt
    { 'code': 'rvt', 'codevalue': 'AutoDeskRevit(RVT)' }, // 3dtiles/i3s1.6/i3s1.7/3dt
    {
        'code': 'osgb2',
        'codevalue': 'PhotoGrammetry(OSG倾斜)',
        'fold_3dtiles': ['cb_qhdgs', 'inp_wd'],
        'attribute_3dtiles': {
            // ENU
            'long': {},
            'lat': {},
            'height': {},
            // SRS
            'deviationX': {},
            'deviationY': {},
            'deviationZ': {},
            'coord': {},
            'prjFile': {},
            // LTWT：cb_qhdgs
            'rebuildPyramid': {
                'default': true
            },
            'marsCoord': {},
            'dracoCompress': {},
            'closeLighting': {},
            'twoSidedLighting': {},
            // LTWT：inp_wd
            'textureCompress': {}
        },
        'fold_3dt': ['ci_shap', 'cb_bj'],
        'attribute_3dt': {
            // SRS
            'deviationX': {},
            'deviationY': {},
            'deviationZ': {},
            // LTWT
            'prjSFile': {},
            'prjTFile': {},
            'shapOffsetX': {},
            'shapOffsetY': {},
            'backCut': {},
            'interactiveCOLL': {}
        }
    },
    { 'code': 'fbx', 'codevalue': 'AutodeskFBX(FBX)' }, // 3dtiles/3dt
    { 'code': 'osgb0', 'codevalue': 'OpenSceneGraph(OSG模型)' }, // 3dtiles/i3s1.6/i3s1.7/3dt
    {
        'code': 'osgb1',
        'codevalue': 'OpenSceneGraph(pagedlod瓦片)',
        // 'fold_3dtiles': [...DefFold_3dtiles],
        // 'attribute_3dtiles': {
        //     ...DefAttr_3dtiles,
        //     // LTWT：模型局部变换
        //     'translationX': {
        //         'disable': true
        //     },
        //     'translationY': {
        //         'disable': true
        //     },
        //     'translationZ': {
        //         'disable': true
        //     },
        //     'rotateX': {
        //         'disable': true
        //     },
        //     'rotateY': {
        //         'disable': true
        //     },
        //     'rotateZ': {
        //         'disable': true
        //     },
        //     'scaleX': {
        //         'disable': true
        //     },
        //     'scaleY': {
        //         'disable': true
        //     },
        //     'scaleZ': {
        //         'disable': true
        //     }
        // }
    }, // 3dtiles/i3s1.6/i3s1.7（3dt ——NO）
    { 'code': 'obj', 'codevalue': 'WavefrontOBJ(OBJ)' }, // 3dtiles（3dt ——NO）
    { 'code': '3ds', 'codevalue': 'Autodesk3ds(3DS)' }, // 3dtiles（3dt ——NO）
    { 'code': 'stl', 'codevalue': 'StandardTriangle/TesselationLanguage(STL)' }, // 3dtiles（3dt ——NO）
    { 'code': 'iges', 'codevalue': 'TheInitialGraphicsExchangeSpecification(IGES)' }, // 3dtiles（3dt ——NO）
    { 'code': 'stp', 'codevalue': 'StandardfortheExchangeofProductModelData(STEP)' }, // 3dtiles（3dt ——NO）
    { 'code': '3dxml', 'codevalue': 'DassaultSystemes3DXML(3DXML)' }, // 3dtiles/3dt
    { 'code': 'dgn', 'codevalue': 'MicroStation(DGN)' }, // 3dtiles/3dt
    { 'code': 'max', 'codevalue': '3dsMax2022(MAX)' } // 3dtiles/3dt
]


// 属性信息（全部）
const Attribute = {
    // ENU
    'long': {
        'attrName': 'enu_lon',
        'default': 116,
        'display': true, // 是否显示
        'disable': false // 禁用/启用状态
    },
    'lat': {
        'attrName': 'enu_lat',
        'default': 39,
        'display': true,
        'disable': false
    },
    'height': {
        'attrName': 'enu_alt',
        'default': 10,
        'display': true,
        'disable': false
    },
    // SRS
    'deviationX': {
        'attrName': 'origin_x',
        'default': 0,
        'display': true,
        'disable': false
    },
    'deviationY': {
        'attrName': 'origin_y',
        'default': 0,
        'display': true,
        'disable': false
    },
    'deviationZ': {
        'attrName': 'origin_z',
        'default': 0,
        'display': true,
        'disable': false
    },
    'proj4': {
        'attrName': 'source_proj4',
        'default': '',
        'display': true,
        'disable': true
    },
    'coord': {
        'attrName': 'source_epsg',
        'default': '',
        'display': true,
        'disable': false
    },
    'prjFile': {
        'attrName': 'source_proj',
        'alias': 'prjStr', // 别名
        'default': '',
        'display': true,
        'disable': false
    }, // prj文件
    // ------------------------------ 3dt
    'settingType': {
        'attrName': 'maxModelType',
        'default': '',
        'display': true,
        'disable': false
    }, // 设置类型
    'modelAccuracy': {
        'attrName': 'viewDetailLevel',
        'default': 'medium',
        'display': true,
        'disable': false
    },
    // LTWT：cb_ltqjccjdy begin
    'lodPyramid': {
        'attrName': 'hlod',
        'default': false,
        'display': true,
        'disable': false
    },
    'removeRemoteModels': {
        'attrName': 'removeTooFar',
        'default': false,
        'display': true,
        'disable': false
    },
    'forceBackClipping': {
        'attrName': 'forceCullFace',
        'default': false,
        'display': true,
        'disable': false
    },
    'distanceFieldShadows': {
        'attrName': 'distancefield',
        'default': false,
        'display': true,
        'disable': false
    },
    'recalculateNormalDirection': {
        'attrName': 'recalculateNormal',
        'default': false,
        'display': true,
        'disable': false
    },
    'splitOSGB': {
        'attrName': 'splitOSGB',
        'default': false,
        'display': true,
        'disable': false
    },
    'interactiveCollision': {
        'attrName': 'collision',
        'default': false,
        'display': true,
        'disable': false
    },
    'objectsCanBePicked': {
        'attrName': 'writeActorInfo',
        'default': false,
        'display': true,
        'disable': false
    },
    'optimizeMapPerformance': {
        'attrName': 'mergeTexture',
        'default': false,
        'display': true,
        'disable': false
    },
    // cb_ltqjccjdy end
    'gridSize_5': {
        'attrName': 'gridLength',
        'default': 10,
        'display': true,
        'disable': false
    },
    'eliminateSmallBuilding': {
        'attrName': 'cutBoxRate',
        'default': '',
        'display': true,
        'disable': false
    },
    'eliminateSmallObjects': {
        'attrName': 'mindrawresolution',
        'default': 8,
        'display': true,
        'disable': false
    },
    'gridSize_100': {
        'attrName': 'gridLength',
        'default': '',
        'display': true,
        'disable': false
    },
    'automaticGlazing': {
        'attrName': 'glassMaterialConvert',
        'default': false,
        'display': true,
        'disable': false
    },
    'appointGlassMaterial': {
        'attrName': 'specialMaterial',
        'default': '',
        'display': true,
        'disable': false
    },
    'dynamicWaterMap': {
        'attrName': 'water',
        'default': '',
        'display': true,
        'disable': false
    },
    'turnOffLighting': {
        'attrName': 'unlightMaterial',
        'default': false,
        'display': true,
        'disable': false
    },
    'customTreeLibrary': {
        'attrName': 'replaceWithCustomAssets',
        'default': false,
        'display': true,
        'disable': false
    },
    // ci_shap && cb_bj
    'prjSFile': {
        'attrName': 'inWkt',
        'alias': 'prjSStr', // 别名
        'default': '',
        'display': true,
        'disable': false
    },
    'prjTFile': {
        'attrName': 'outWkt',
        'alias': 'prjTStr', // 别名
        'default': '',
        'display': true,
        'disable': false
    },
    'shapOffsetX': {
        'attrName': 'xshpoffset',
        'default': 0,
        'display': true,
        'disable': false
    },
    'shapOffsetY': {
        'attrName': 'yshpoffset',
        'default': 0,
        'display': true,
        'disable': false
    },
    'backCut': {
        'attrName': 'orceCullFace',
        'default': true,
        'display': true,
        'disable': false
    },
    'interactiveCOLL': {
        'attrName': 'collision',
        'default': true,
        'display': true,
        'disable': false
    },
    // ------------------------------ other
    // LTWT：模型局部变换
    'translationX': {
        'attrName': 'translate_x',
        'default': 0,
        'display': true,
        'disable': false
    },
    'translationY': {
        'attrName': 'translate_y',
        'default': 0,
        'display': true,
        'disable': false
    },
    'translationZ': {
        'attrName': 'translate_z',
        'default': 0,
        'display': true,
        'disable': false
    },
    'rotateX': {
        'attrName': 'rotate_x',
        'default': 0,
        'display': true,
        'disable': false
    },
    'rotateY': {
        'attrName': 'rotate_y',
        'default': 0,
        'display': true,
        'disable': false
    },
    'rotateZ': {
        'attrName': 'rotate_z',
        'default': 0,
        'display': true,
        'disable': false
    },
    'scaleX': {
        'attrName': 'scale_x',
        'default': 1,
        'display': true,
        'disable': false
    },
    'scaleY': {
        'attrName': 'scale_y',
        'default': 1,
        'display': true,
        'disable': false
    },
    'scaleZ': {
        'attrName': 'scale_z',
        'default': 1,
        'display': true,
        'disable': false
    },
    // LTWT：模型精细度控制
    'seniorSimplify': {
        'attrName': 'leaf_cost_limit',
        'default': '0.00000001',
        'display': true,
        'disable': false
    },
    'otherSimplify': {
        'attrName': 'parent_cost_limit',
        'default': '0.00001',
        'display': true,
        'disable': false
    },
    // LTWT：模型切割
    'autoDivision': {
        'attrName': 'auto_divide_level',
        'default': 3,
        'display': true,
        'disable': false
    },
    'levelUpperLimit': {
        'attrName': 'max_level_count',
        'default': 5,
        'display': true,
        'disable': false
    },
    'topTile': {
        'attrName': 'target_root_count',
        'default': 1,
        'display': true,
        'disable': false
    },
    // LTWT：模型图层显示控制 && 特殊功能 && cb_wlmj
    'saveHighDefinitionTiles': {
        'attrName': 'save_best',
        'default': false,
        'display': true,
        'disable': false
    },
    'textureDense': {
        'attrName': 'textured_scene',
        'default': false,
        'display': true,
        'disable': false
    },
    'backfaceCulling': {
        'attrName': 'override_cull_face',
        'default': '',
        'display': true,
        'disable': false
    },
    'calculateNormals': {
        'attrName': 'reset_normal',
        'default': false,
        'display': true,
        'disable': false
    },
    // LTWT：cb_zt
    'minTileSize': {
        'attrName': 'min_tile_size',
        'default': 16,
        'display': true,
        'disable': false
    },
    'transparentMaps': {
        'attrName': 'alpha_reject',
        'default': 0.3,
        'display': true,
        'disable': false
    },
    // LTWT：cb_gz
    'rootNodeNum': {
        'attrName': 'target_root_count',
        'default': 1,
        'display': true,
        'disable': false
    },
    'maxTileLevel': {
        'attrName': 'max_level_count',
        'default': 5,
        'display': true,
        'disable': false
    },
    // LTWT：cb_dc
    'exportOP': {
        'attrName': 'save_best',
        'default': false,
        'display': true,
        'disable': false
    },
    'recalculateNormals': {
        'attrName': 'reset_normal',
        'default': false,
        'display': true,
        'disable': false
    },
    // LTWT：inp_wyf
    'tileMaxVertex': {
        'attrName': 'max_vertex_count',
        'default': 200000,
        'display': true,
        'disable': false
    },
    'leafTiled': {
        'attrName': 'leaf_cost_limit',
        'default': '0.00000001',
        'display': true,
        'disable': false
    },
    'nonLeafTiled': {
        'attrName': 'parent_cost_limit',
        'default': 0.00001,
        'display': true,
        'disable': false
    },
    // LTWT：cb_qhdgs
    'forceScaleMap': {
        'attrName': 'force_scale_image',
        'default': false,
        'display': true,
        'disable': false
    },
    'rebuildPyramid': {
        'attrName': 'rebulid',
        'default': false,
        'display': true,
        'disable': false
    },
    'marsCoord': {
        'attrName': 'use_gcj02',
        'default': false,
        'display': true,
        'disable': false
    },
    'dracoCompress': {
        'attrName': 'compress_draco',
        'default': false,
        'display': true,
        'disable': false
    },
    'closeLighting': {
        'attrName': 'close_light',
        'default': false,
        'display': true,
        'disable': false
    },
    'twoSidedLighting': {
        'attrName': 'two_sided',
        'default': false,
        'display': true,
        'disable': false
    },
    'ddsCompress': {
        'attrName': 'compress_draco',
        'default': false,
        'display': true,
        'disable': false
    },
    // LTWT：inp_wd
    'textureCompress': {
        'attrName': 'use_webp',
        'default': '',
        'display': true,
        'disable': false
    },
    'dispatchFactor': {
        'attrName': 'pixel_ratio',
        'default': 0.5,
        'display': true,
        'disable': false
    },
    // LTWT：ci_zbx
    'coord2': {
        'attrName': 'target_epsg',
        'default': '',
        'display': true,
        'disable': false
    },
    'prj2File': {
        'attrName': 'target_proj',
        'alias': 'prj2Str', // 别名
        'default': '',
        'display': true,
        'disable': false
    },
    // LTWT：ci_ydcssz
    'lodLevel': {
        'attrName': 'lod_level',
        'default': 10,
        'display': true,
        'disable': false
    },
    'lodReductionRatio': {
        'attrName': 'simply_ratio',
        'default': 0.8,
        'display': true,
        'disable': false
    },
    // LTWT：转换设置 
    'colorMode': {
        'attrName': 'rgb',
        'default': '1',
        'display': true,
        'disable': false
    },
    'northboundModel': {
        'attrName': 'northtype',
        'default': '1',
        'display': true,
        'disable': false
    },
    'originType': {
        'attrName': 'origintype',
        'default': '1',
        'display': true,
        'disable': false
    },
    'viewName': {
        'attrName': 'viewname',
        'default': '',
        'display': true,
        'disable': false
    },
    'linkFile': {
        'attrName': 'linkfile',
        'default': true,
        'display': true,
        'disable': false
    },
    'cadFile': {
        'attrName': 'cadfile',
        'default': false,
        'display': true,
        'disable': false
    }
}


/**
 * 初始化属性
 * @param {string} code 源数据格式（15种）
 * @param {string} target 目标格式
 */
const InitProperty = (code, target) => {
    let data = DatasetType.find(item => item.code === code)
    if (!data) return

    // customize
    if (data.hasOwnProperty(`attribute_${target}`)) return

    // 纹理密集（默认选中）
    function ckWLMJ() {
        let arr = [...DefFold_3dtiles]
        let index = arr.indexOf('cb_gz')
        arr.splice(index, 1, 'cb_zt')
        return {
            'fold': arr,
            'attr': {
                'textureDense': {
                    'default': true
                },
                'tileMaxVertex': {
                    'default': 6000
                }
            }
        }
    }

    switch (target) {
        case '3dtiles':
            // customize: ['las', 'ifc', 'osgb2'] // , 'osgb1'

            // unsupported ——NO
            if (['osgb1'].includes(code)) return

            if (['rvt', 'stl', 'iges', 'stp', '3dxml', 'dgn', 'max'].includes(code)) {
                // 通用属性
                data[`fold_${target}`] = [...DefFold_3dtiles]
                data[`attribute_${target}`] = { ...DefAttr_3dtiles }
            } else if (['fbx', 'osgb0', 'obj', '3ds'].includes(code)) {
                // 纹理密集（默认选中）
                let wlmj = ckWLMJ()
                data[`fold_${target}`] = wlmj.fold
                data[`attribute_${target}`] = { ...DefAttr_3dtiles, ...wlmj.attr }
            }
            return;
        case '3dt':
            // customize: ['las', 'osgb2']

            // unsupported ——NO
            if (['osgb1', 'obj', '3ds', 'stl', 'iges', 'stp'].includes(code)) return

            if (['ifc', 'rvt', '3dxml', 'dgn'].includes(code)) {
                // 3dt_模型精度
                data[`fold_${target}`] = [...DefFold_3dt]
                data[`attribute_${target}`] = { ...DefAttr_3dt_P }
            } else if (['fbx', 'osgb0', 'max'].includes(code)) {
                // 3dt_设置类型
                data[`fold_${target}`] = [...DefFold_3dt]
                data[`attribute_${target}`] = { ...DefAttr_3dt_T }
            }
            return;
        case 'i3s1.6':
        case 'i3s1.7':
            // unsupported ——NO
            if (['las'].includes(code)) return

            let fold = null
            let copy = null
            if (['rvt', 'stl', 'iges', 'stp', '3dxml', 'dgn', 'max'].includes(code)) {
                // 通用属性
                fold = [...DefFold_3dtiles]
                copy = { ...DefAttr_3dtiles }
            } else if (['osgb0', 'fbx', 'obj', '3ds'].includes(code)) {
                // 纹理密集（默认选中）
                let wlmj = ckWLMJ()
                fold = wlmj.fold
                copy = { ...DefAttr_3dtiles, ...wlmj.attr }
            } else if (['osgb1', 'osgb2'].includes(code)) {
                // 自定义属性
                fold = [...data[`fold_3dtiles`]]
                copy = { ...data[`attribute_3dtiles`] }
            } else if (['ifc'].includes(code)) {
                fold = [...data[`fold_3dtiles`], 'cb_dc', 'inp_wyf', 'cb_qhdgs', 'inp_wd']
                copy = {
                    ...data[`attribute_3dtiles`],
                    // LTWT：cb_dc
                    'exportOP': {},
                    'recalculateNormals': {},
                    // LTWT：inp_wyf
                    'tileMaxVertex': {},
                    'leafTiled': {},
                    'nonLeafTiled': {},
                    // LTWT：cb_qhdgs
                    'forceScaleMap': {},
                    'marsCoord': {},
                    'dracoCompress': {},
                    'closeLighting': {},
                    'twoSidedLighting': {},
                    // LTWT：inp_wd
                    'textureCompress': {},
                    'dispatchFactor': {}
                }
            }
            if (!copy) return

            delete copy.closeLighting
            delete copy.textureCompress
            let newAttr = {
                'ddsCompress': {},
                'coord2': {},
                'prj2File': {}
            }
            data[`fold_${target}`] = [...fold, 'ci_zbx']
            data[`attribute_${target}`] = { ...copy, ...newAttr }
            return;
    }
}

export default {
    DatasetType,
    Attribute,
    InitProperty
}