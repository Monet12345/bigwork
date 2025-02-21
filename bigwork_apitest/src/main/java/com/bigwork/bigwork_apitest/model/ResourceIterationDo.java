package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceIterationDo {
    private String resourceDataId;
    private int nowIteration;
    private int lastIteration;
}
