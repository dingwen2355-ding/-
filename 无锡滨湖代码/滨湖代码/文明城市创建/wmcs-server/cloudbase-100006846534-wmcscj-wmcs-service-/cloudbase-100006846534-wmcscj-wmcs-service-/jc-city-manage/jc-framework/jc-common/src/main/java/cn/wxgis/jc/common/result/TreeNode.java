package cn.wxgis.jc.common.result;

import cn.wxgis.jc.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {

    private Logger logger = LoggerFactory.getLogger(TreeNode.class);

    private List<T> list;

    public TreeNode(){}

    public TreeNode(List<T> list){
        this.list = list;
    }

    public List<T> buildThree(){
        List<T> treeNode = new ArrayList<>();
        // 获取所有根节点
        getRootNode().forEach(rootNode -> {
            // 将顶级节点进行构建子树
            rootNode = buildChildTree(rootNode);
            treeNode.add(rootNode);
        });
        return treeNode;
    }

    public List<T> getRootNode(){
        //所有根节点集合
        List<T> nodeList = new ArrayList<>();
        List<String> ids = this.getIds();
        list.forEach(item ->{
            try {
                Object parentId = item.getClass().getMethod("getParentId").invoke(item);
                if (StringUtils.isNull(parentId) || "0".equals(parentId) || "".equals(parentId) || !ids.contains(parentId)) {
                    nodeList.add(item);
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
            } catch (NoSuchMethodException e) {
                logger.error(e.getMessage());
            }
        });
        return nodeList;
    }

    public List<String> getIds() {
        List<String> ids = new ArrayList<>();
        try {
            for (T t : list) {
                Object id = t.getClass().getMethod("getId").invoke(t);
                ids.add(String.valueOf(id));
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        }
        return ids;
    }

    public T buildChildTree(T t) {
        List<T> childList = new ArrayList<>();
        list.forEach(item -> {
            try {
                Object parentId = item.getClass().getMethod("getParentId").invoke(item);
                Object id = t.getClass().getMethod("getId").invoke(t);
                if (StringUtils.isNotNull(parentId) && parentId.equals(id)) {
                    childList.add(buildChildTree(item));
                }
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
            } catch (NoSuchMethodException e) {
                logger.error(e.getMessage());
            }
        });
        try {
            Field f = t.getClass().getDeclaredField("children");
            f.setAccessible(true);
            f.set(t, childList);
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return t;
    }

}
