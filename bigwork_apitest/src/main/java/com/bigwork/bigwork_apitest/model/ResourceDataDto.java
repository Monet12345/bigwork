package com.bigwork.bigwork_apitest.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("资源共享data承接")
public class ResourceDataDto {
    private List<String>head;//表头
    private List<List<String>>body;//表中对应数据的值

}
