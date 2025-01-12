package api;

import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@FeignClient(
    name = "idManagementFacade",
    url = "${bigwork.meta.server}",
    path = "${bigwork.meta.path}",
    primary = false)
public interface DeepSeekAIUtilFacade {
  String INTERNAL_BASE="/internal";
  String BASE="/deepSeekAIUtilFacade";
  @GetMapping (INTERNAL_BASE+BASE+"/ask")
  @ApiOperation(value = "询问ai的小工具")
  Result<String> setFirstAskAndAsk(@RequestParam String firstAsk,String question,String workspaceId) throws IOException, InterruptedException;

}
