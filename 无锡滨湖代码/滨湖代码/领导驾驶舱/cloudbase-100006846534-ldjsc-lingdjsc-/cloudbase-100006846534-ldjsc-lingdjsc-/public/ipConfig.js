
const isLocalDev = 'false'; // 用户服务接口

//const _host = '2.21.137.143';
const _host = '2.21.138.89';
const cim_loginServer = `https://${_host}:8088/cim-gateway/`; // 用户服务接口
const cim_server = `https://${_host}:8088/cim-server-0614/`;
const cim_main = `https://${_host}:8088/cim-gateway/cim/`;

const account_info = {
    username: 'lijie000', // 用户名
    password: 'Szbh@2024' // 密码
    //username: 'admin', // 用户名
    //password: '123456' // 密码
}

// 服务代理地址
// const service_proxy_address = 'http://2.21.137.143:8088/cim-gateway-0614//cluster_962/cim-server-0614//service/scene/getCloudInfo?mediaId=9abefe84-781d-4762-b10c-7a9b90bed298&freedoToken=7da226cbb16334bce41c007e4168eec1';
 const service_proxy_address = 'https://2.21.138.89:8088/cim-proxy/cluster_797/cim-gateway/cim/engine/mediascene/loadAcpProject?mediaId=349eaa855e88428b95b9a998e0616a62&freedoToken=92996d5a020c4ed5972ad4aeac5d7011';  
//const service_proxy_address = 'http://2.21.137.143:8088/cim-gateway-0614//cluster_963/cim-server-0614//service/scene/getCloudInfo?mediaId=adf77006-4517-46cc-a0a5-115a6e0e09d3&freedoToken=fb85d84112fe1b92e60c42c2d3dedadf'              