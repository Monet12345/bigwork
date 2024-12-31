package com.bigwork.bigwork_meta.service.Impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_meta.dal.mapper.UserMapper;
import com.bigwork.bigwork_meta.dal.modle.UserDo;
import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.CaptchaVo;
import com.bigwork.bigwork_meta.web.model.LoginReq;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.DecoderException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import util.BizException;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import static cn.hutool.core.date.DateUtil.now;

@Component

public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;
  @Resource private RedisTemplate<String, Object> redisTemplate;

  private static final int SALT_LENGTH = 16; // 盐的长度

  @Override

  public SaTokenInfo login(LoginReq req) {
    if (StpUtil.isLogin()) {
      throw new BizException("已登录");
    }
       UserDo userDo = userMapper.selectByUserName(req.getUserName(), req.getWorkspaceId());
    String redisCode= (String) redisTemplate.opsForValue().get(req.getVerKey());
    if(redisCode==null){
      throw new BizException("验证码已过期");
    }
    if(req.getVerCode()==null||!redisCode.equals(req.getVerCode().trim().toLowerCase())){
      throw new BizException("验证码错误");
    }
    if (userDo == null || !checkPassword(userDo, req.getPassword())) {
      throw new BizException("用户名或密码错误");
    }
    try {
      StpUtil.login(userDo.getUserId());
    }
    catch (Exception e){
      throw new BizException("登录失败",e);
    }
    return StpUtil.getTokenInfo();
  }

  @Override
  public void register(LoginReq req) {
    UserDo row = userMapper.selectByUserName(req.getUserName(), req.getWorkspaceId());
    if(row!=null){
      throw new BizException("用户已存在");
    }
    UserDo userDo = BeanUtil.copyProperties(req, UserDo.class);
    userDo.setCreateTime(now());
    userDo.setUpdateTime(now());
    userDo = creatPassword(userDo);
    userDo.setWorkspaceId(req.getWorkspaceId());
    userDo.setUserId(UUID.randomUUID().toString());
    userDo.setNickName("未定义昵称（请自行修改昵称）");
    userMapper.add(userDo);
  }

  @Override
  public CaptchaVo getCaptcha() {
    SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
    String verCode = specCaptcha.text().toLowerCase();
    String key = UUID.randomUUID().toString();
    redisTemplate.opsForValue().set(key, verCode, 30, TimeUnit.MINUTES);
    CaptchaVo captchaVo = new CaptchaVo();
    captchaVo.setKey(key);
    captchaVo.setImage(specCaptcha.toBase64());
    // 将key和base64返回给前端
    return captchaVo;
  }


  @Override
  public void githubCallBack(String code) throws IOException, InterruptedException {
    String githubUrl = "https://github.com/login/oauth/access_token?client_id=Ov23lilFmwrnT9298kxG&client_secret=2b064aab541e2cf3fc47f0af6039d5a5352fd4bc&code="+code;
    // 创建HttpClient实例
    HttpClient client = HttpClient.newHttpClient();

    // 创建HttpRequest实例
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(githubUrl))
        .GET() // 指定请求方法为GET
        .build();
    // 发送请求并获取响应
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    String body = response.body();//内含access_token和scope和token_type
    String accessToken = extractAccessToken(body);

    // 使用accessToken获取用户信息
    String githubUserUrl = "https://api.github.com/user";
    HttpRequest userRequest = HttpRequest.newBuilder()
        .uri(URI.create(githubUserUrl))
        .header("Authorization", "token " + accessToken) // 使用Authorization头传递访问令牌
        .GET()
        .build();

    // 发送请求并获取响应
    HttpResponse<String> responseAskUser = client.send(userRequest, HttpResponse.BodyHandlers.ofString());

    if (responseAskUser.statusCode() != 200) {
      throw new BizException("获取用户信息失败，状态码：" + responseAskUser.statusCode());
    }

    System.out.println(responseAskUser.body());
  }
  public String extractAccessToken(String responseBody) {
    // 检查输入是否为空
    if (responseBody == null || responseBody.isEmpty()) {
      throw new BizException("响应体为空");
    }
    // 使用字符串操作来提取 access_token 的值
    String accessTokenPrefix = "access_token=";
    int startIndex = responseBody.indexOf(accessTokenPrefix);
    if (startIndex == -1) {
      throw new BizException("未找到 access_token");
    }
    startIndex += accessTokenPrefix.length();
    int endIndex = responseBody.indexOf('&', startIndex);
    if (endIndex == -1) {
      endIndex = responseBody.length();
    }

    return responseBody.substring(startIndex, endIndex);
  }

  //https://github.com/login/oauth/authorize?client_id=Ov23lilFmwrnT9298kxG&redirect_uri=http://localhost:8082/meta/test/&scope=openid
  //https://github.com/login/oauth/access_token?client_id=Ov23lilFmwrnT9298kxG&client_secret=2b064aab541e2cf3fc47f0af6039d5a5352fd4bc&code=eb711ec511b7bae58920


  boolean checkPassword(UserDo userDo, String password) {
    String[] parts = userDo.getPassword().split("\\$");//盐和密码以$分割存在数据库当中
    if (parts.length != 2) {
      return false;
    }
    String salt = parts[0];
    String hashedPassword = parts[1];
    String hashedInputPassword = hashPassword(password, salt);
    return hashedPassword.equals(hashedInputPassword);
  }

  UserDo creatPassword(UserDo userDo) {
    String salt = generateSalt();
    String hashedPassword = hashPassword(userDo.getPassword(), salt);
    userDo.setPassword(salt + "$" + hashedPassword);
    return userDo;
  }

  private String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_LENGTH];
    random.nextBytes(salt);
    return Hex.encodeHexString(salt);
  }

  private String hashPassword(String password, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(Hex.decodeHex(salt.toCharArray()));
      byte[] hashedBytes = md.digest(password.getBytes());
      return Hex.encodeHexString(hashedBytes);
    } catch (NoSuchAlgorithmException | DecoderException | org.apache.commons.codec.DecoderException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }
}
