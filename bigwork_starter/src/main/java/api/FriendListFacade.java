package api;

import model.Result;
import model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "friendListFacade",
        url = "${bigwork.apitest.server}",
        path = "${bigwork.apitest.path}",
        primary = false)
public interface FriendListFacade {
    String INTERNAL_BASE="/internal";
    String BASE="/friendListFacade";
    @GetMapping(INTERNAL_BASE+BASE+"/setChatFriends")
    Result<String> setChatFriends(@RequestParam("userId") String userId,@RequestParam("workspaceId") String workspaceId);
}
