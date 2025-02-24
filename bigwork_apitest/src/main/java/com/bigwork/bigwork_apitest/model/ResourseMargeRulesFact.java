package com.bigwork.bigwork_apitest.model;

import lombok.Data;

import java.util.List;

@Data
public class ResourseMargeRulesFact {
    //用于给drools的脚步去marge两张表格承接规则的实体类
    public ResourceDataDto data1;//当前表的数据
    public ResourceDataDto data2;//要marge的表的数据
    public List<Integer> rowDeference;//针对每一行data2,与data1不同字段值的字段个数
    public ResourceDataDto factData;//结果marge后的表

    public List<String> resultMessageLog;//使用中文来解释触发了哪像规则，用于展示规则运作进程日志

}
