/*
 * @Descripttion:
 * @version:
 * @Author: GM
 * @Date: 2021-03-08 14:35:34
 * @LastEditors: GM
 * @LastEditTime: 2021-10-19 09:59:36
 */
import systemApi from "@/api/modules/system";
import scoketTaskApi from "@/api/modules/scoketTask";
import baseInteractiveApi from "@/api/modules/baseInteractive";
import BuildingApi from "@/api/modules/Building";
import KeyPeopleApi from "@/api/modules/KeyPeople";
import DataQualityApi from "@/api/modules/DataQuality";
import EventAnalysisApi from "@/api/modules/EventAnalysis";
import GridPowerApi from "@/api/modules/GridPower";

export const system = systemApi;
export const scoketTask = scoketTaskApi;
export const baseInteractive = baseInteractiveApi;
export const Building = BuildingApi;
export const KeyPeople = KeyPeopleApi;
export const DataQuality = DataQualityApi;
export const EventAnalysis = EventAnalysisApi;
export const GridPower = GridPowerApi;
