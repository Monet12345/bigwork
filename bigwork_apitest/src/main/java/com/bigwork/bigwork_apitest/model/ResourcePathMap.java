package com.bigwork.bigwork_apitest.model;


import com.bigwork.bigwork_apitest.dal.mapper.ResourceIterationDataMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//单例模式存资源共享模块关系图
public class ResourcePathMap {
    @Resource
    private ResourceIterationDataMapper resourceIterationDataMapper;
    public static volatile ResourcePathMap instance;

    //用于记录关系的map容器
    private  ArrayList<List<List<String>>> map;//存关系图
    private  ConcurrentHashMap<String,Integer> map2;//查询下一个编号

    private  ConcurrentHashMap<String,Integer> map3;//记录第一个维度，表id对应的数字

    public  Integer Offset2 ;//发放编号
    public  Integer Offset3;

    private ResourcePathMap() {
        this.map = new ArrayList<List<List<String>>>();
        this.map2 = new ConcurrentHashMap<String,Integer>();
        this.map3 = new  ConcurrentHashMap<String,Integer>();
        this.Offset2=0;//发放版本对应编号
        this.Offset3=0;//发放id对应编号
        Init();
    }
    private void Init(){
        List<ResourceIterationDo> resourceIterationDos = resourceIterationDataMapper.getAll();
        for(ResourceIterationDo resourceIterationDo:resourceIterationDos){
            if(map3.get(resourceIterationDo.getResourceDataId())==null){
                map3.put(resourceIterationDo.getResourceDataId(),Offset3);
                map.add(new ArrayList<List<String>>());
                Offset3++;
            }
            Integer cnt3=map3.get(resourceIterationDo.getResourceDataId());
            String parent=resourceIterationDo.getParent();
            String point=resourceIterationDo.getIteration();
            if(map2.get(parent)==null){
                map2.put(parent,Offset2);
                map.get(cnt3).add(new ArrayList<String>());
                Offset2++;
            }
            if(map2.get(point)==null){
                map2.put(point,Offset2);
                map.get(cnt3).add(new ArrayList<String>());
                Offset2++;
            }
            int cntParent=map2.get(parent);
            map.get(cnt3).get(cntParent).add(point);
        }
    }
    public static ResourcePathMap getInstance() {
        if (instance == null) {
            synchronized (ResourcePathMap.class) {
                if (instance == null) {
                    instance = new ResourcePathMap();
                }
            }
        }
        return instance;
    }




    public void addPoint(String point,String resourceDataId,String workspaceId) {

        synchronized (this){
            ResourceIterationDo resourceIterationDo = resourceIterationDataMapper.getDetailById(resourceDataId, point, workspaceId);
            String parent = resourceIterationDo.getParent();
            int cnt=(map2.get(parent)==null)?0:map2.get(parent);
            if(!map.get(map3.get(resourceDataId)).get(cnt).contains(point)){map.get(map3.get(resourceDataId)).get(cnt).add(point);}
            else {
                log.info("要加入的节点已经存在了，详见ResourcePathMap类的addPoint方法");
            }
        }

    }


    //删除point节点（需要传入他的parent）
    public void deletePoint(String point,String resourceDataId,String workspaceId){
        synchronized (this) {
            ResourceIterationDo resourceIterationDo = resourceIterationDataMapper.getDetailById(resourceDataId, point, workspaceId);
            String parent = resourceIterationDo.getParent();
                if (map2.get(point) != null) {
                int cntPoint = map2.get(point);
                List<String> children = map.get(map3.get(resourceDataId)).get(cntPoint);
                int cntParent = map2.get(parent);
                int removePoint = 0;
                for (int i = 0; i < map.get(cntParent).size(); i++) {
                    if (map.get(cntParent).get(i).equals(point)) {
                        removePoint = i;
                        break;
                    }
                }
                map.get(cntParent).remove(removePoint);
                resourceIterationDataMapper.deleteById(resourceDataId, point, workspaceId);
                for (int i = 0; i < children.size(); i++) {
                    map.get(map3.get(resourceDataId)).get(cntParent).add(children.get(i));
                    resourceIterationDataMapper.updateParent(resourceDataId,children.get(i),parent, workspaceId);
                }
            } else {
                log.info("要删除的节点不存在，详见ResourcePathMap类的deletePoint方法");
            }
        }
    }


    public List<List<String>> getIterationTree(String resourceDataId,String workspaceId){
        return map.get(map3.get(resourceDataId));
    }




}
