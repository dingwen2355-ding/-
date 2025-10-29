package com.znv.manage.common.bean;


import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

@Data
public class HuaweiGroupTree {

    private List<HuaweiGroup> huaweiGroupList = new ArrayList<>();
    public HuaweiGroupTree(List<HuaweiGroup> huaweiGroupList) {
        this.huaweiGroupList=huaweiGroupList;
    }

    //建立树形结构
    public List<HuaweiGroup> builTree(){
        List<HuaweiGroup> treeGroups = new ArrayList<>();
        for(HuaweiGroup group : getRootNode()) {
            group=buildChilTree(group);
            treeGroups.add(group);
        }
        return treeGroups;
    }

    //递归，建立子树形结构
    private HuaweiGroup buildChilTree(HuaweiGroup pNode){
        List<Object> childGroup =new  ArrayList<>();
        for(HuaweiGroup group : huaweiGroupList) {
            if(pNode.getId().equals(group.getParentId())) {
                childGroup.add(buildChilTree(group));
            }
        }
        if(!CollectionUtils.isEmpty(pNode.getChildren())){
            pNode.getChildren().addAll(childGroup);
        }else {
            pNode.setChildren(childGroup);
        }
        return pNode;
    }

    //获取根节点
    private List<HuaweiGroup> getRootNode() {
        List<HuaweiGroup> rootGroupLists = new ArrayList<>();
        for(HuaweiGroup group : huaweiGroupList) {
            if("-1".equals(group.getParentId())) {
                rootGroupLists.add(group);
            }
        }
        return rootGroupLists;
    }
}
