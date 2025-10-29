package com.znv.manage.common.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RegionTree {

    private List<Region> regionList = new ArrayList<Region>();
    public RegionTree(List<Region> regionList) {
        this.regionList=regionList;
    }

    //建立树形结构
    public List<Region> builTree(){
        List<Region> treeRegions = new ArrayList<Region>();
        for(Region region : getRootNode()) {
            region=buildChilTree(region);
            treeRegions.add(region);
        }
        return treeRegions;
    }

    //递归，建立子树形结构
    private Region buildChilTree(Region pNode){
        List<Region> chilRegion =new  ArrayList<Region>();
        for(Region region : regionList) {
            if(pNode.getIndexCode().equals(region.getParentIndexCode())) {
                chilRegion.add(buildChilTree(region));
            }
        }
        pNode.setChildren(chilRegion);
        return pNode;
    }

    //获取根节点
    private List<Region> getRootNode() {
        List<Region> rootRegionLists = new ArrayList<Region>();
        for(Region region : regionList) {
            if("-1".equals(region.getParentIndexCode())) {
                rootRegionLists.add(region);
            }
        }
        return rootRegionLists;
    }
}
