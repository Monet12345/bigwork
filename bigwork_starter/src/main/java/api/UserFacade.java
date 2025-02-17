package api;

import model.Result;
import model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "userFacade",
        url = "${bigwork.meta.server}",
        path = "${bigwork.meta.path}",
        primary = false)
public interface UserFacade {
    String INTERNAL_BASE="/internal";
    String BASE="/userFacade";
    @GetMapping(INTERNAL_BASE+BASE+"/getUserById")
    Result<User> getUserById(@RequestParam("userId") String userId,@RequestParam("workspaceId") String workspaceId);
}
