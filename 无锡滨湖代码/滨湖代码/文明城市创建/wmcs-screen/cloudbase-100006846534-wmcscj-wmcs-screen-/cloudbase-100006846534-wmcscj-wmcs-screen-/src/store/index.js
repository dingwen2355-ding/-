import { defineStore } from 'pinia'
// 第一个参数是应用程序中 store 的唯一 id
export const useStore = defineStore('main', {
    state: () => {//创建状态
        return {
            heatmap: false,
            jrdb: false,
            jrbj: false,
            areaName: undefined,
            activeMenu: 1,
            loadmap: false,
            indexMonth: "",
        }
    }
})