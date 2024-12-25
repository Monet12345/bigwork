package api;

import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
    name = "idManagementFacade",
    url = "${bigwork.meta.server}",
    path = "${bigwork.meta.path}",
    primary = false)
public interface IdManagementFacade {
  String INTERNAL_BASE="/internal";
  String BASE="/idManagementFacade";
  @GetMapping (INTERNAL_BASE+BASE+"/nextId")
  @ApiOperation(value = "自动获取下一个命名id")
  Result<String> getNextId(@RequestParam("key") String key);

}
