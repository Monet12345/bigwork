package com.bigwork.bigwork_apitest.model;

import com.bigwork.bigwork_apitest.dal.mapper.ResourceIterationDataMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component // 标记为 Spring 管理的 Bean
public class ResourcePathMap {

    @Resource // 注入 ResourceIterationDataMapper
    private ResourceIterationDataMapper resourceIterationDataMapper;

    // 用于记录关系的 map 容器
    @Getter
    private ArrayList<List<List<String>>> map; // 存关系图
    @Getter
    private ConcurrentHashMap<String, Integer> map2; // 查询下一个编号
    @Getter
    private ConcurrentHashMap<String, Integer> map3; // 记录第一个维度，表 id 对应的数字

    public Integer Offset2; // 发放编号
    public Integer Offset3;


    @PostConstruct// 初始化方法，在 Bean 创建后自动调用
    public void Init() {
        this.map = new ArrayList<>();
        this.map2 = new ConcurrentHashMap<>();
        this.map3 = new ConcurrentHashMap<>();
        this.Offset2 = 0; // 发放版本对应编号
        this.Offset3 = 0; // 发放 id 对应编号
        List<ResourceIterationDo> resourceIterationDos = resourceIterationDataMapper.getAll();
        if (resourceIterationDos == null || resourceIterationDos.isEmpty()) {
            log.warn("No data found in resourceIterationDos, initialization skipped.");
            return;
        }

        for (ResourceIterationDo resourceIterationDo : resourceIterationDos) {
            if (map3.get(resourceIterationDo.getResourceDataId()) == null) {
                map3.put(resourceIterationDo.getResourceDataId(), Offset3);
                map.add(new ArrayList<>());
                Offset3++;
            }
            Integer cnt3 = map3.get(resourceIterationDo.getResourceDataId());
            String parent = resourceIterationDo.getParent();
            String point = resourceIterationDo.getIteration();
            if (map2.get(parent) == null) {
                map2.put(parent, Offset2);
                map.get(cnt3).add(new ArrayList<>());
                Offset2++;
            }
            if (map2.get(point) == null) {
                map2.put(point, Offset2);
                map.get(cnt3).add(new ArrayList<>());
                Offset2++;
            }
            int cntParent = map2.get(parent);
            map.get(cnt3).get(cntParent).add(point);
        }
    }

    public void addPoint(String point, String resourceDataId, String workspaceId) {
        synchronized (this) {
            ResourceIterationDo resourceIterationDo = resourceIterationDataMapper.getDetailById(resourceDataId, point, workspaceId);
            if(resourceIterationDo==null)return;
            String parent = resourceIterationDo.getParent();
            int cnt = (map2.get(parent) == null) ? 0 : map2.get(parent);
            if (!map.get(map3.get(resourceDataId)).get(cnt).contains(point)) {
                map.get(map3.get(resourceDataId)).get(cnt).add(point);
            } else {
                log.info("要加入的节点已经存在了，详见 ResourcePathMap 类的 addPoint 方法");
            }
        }
    }

    public void deletePoint(String point, String resourceDataId, String workspaceId) {
        synchronized (this) {
            ResourceIterationDo resourceIterationDo = resourceIterationDataMapper.getDetailById(resourceDataId, point, workspaceId);
            if(resourceIterationDo==null)return;
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
                    resourceIterationDataMapper.updateParent(resourceDataId, children.get(i), parent, workspaceId);
                }
            } else {
                log.info("要删除的节点不存在，详见 ResourcePathMap 类的 deletePoint 方法");
            }
        }
    }

    public List<List<String>> getIterationTree(String resourceDataId, String workspaceId) {
        return map.get(map3.get(resourceDataId));
    }
}