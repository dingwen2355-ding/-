package com.znv.manage.common.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class UnitTree {

    private List<Unit> unitList = new ArrayList<Unit>();
    public UnitTree(List<Unit> unitList) {
        this.unitList=unitList;
    }

    //建立树形结构
    public List<Unit> builTree(){
        List<Unit> treeUnits = new ArrayList<Unit>();
        for(Unit unit : getRootNode()) {
            unit=buildChilTree(unit);
            treeUnits.add(unit);
        }
        return treeUnits;
    }

    //递归，建立子树形结构
    private Unit buildChilTree(Unit pNode){
        List<Unit> chilUnit =new  ArrayList<Unit>();
        for(Unit unit : unitList) {
            if(pNode.getId().equals(unit.getSuperId())) {
                chilUnit.add(buildChilTree(unit));
            }
        }
        pNode.setChildren(chilUnit);
        return pNode;
    }

    //获取根节点
    private List<Unit> getRootNode() {
        List<Unit> rootUnitLists = new ArrayList<Unit>();
        for(Unit unit : unitList) {
            if("-1".equals(unit.getSuperId())) {
                rootUnitLists.add(unit);
            }
        }
        return rootUnitLists;
    }
}
