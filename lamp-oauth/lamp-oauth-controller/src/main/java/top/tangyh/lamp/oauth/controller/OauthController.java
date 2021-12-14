package top.tangyh.lamp.oauth.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import top.tangyh.basic.annotation.base.IgnoreResponseBodyAdvice;
import top.tangyh.basic.base.R;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.exception.BizException;
import top.tangyh.basic.jwt.TokenUtil;
import top.tangyh.basic.jwt.model.AuthInfo;
import top.tangyh.lamp.authority.dto.auth.LoginParamDTO;
import top.tangyh.lamp.authority.dto.auth.RegisterParamDTO;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.OnlineService;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.common.constant.BizConstant;
import top.tangyh.lamp.oauth.granter.TokenGranterBuilder;
import top.tangyh.lamp.oauth.service.ValidateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证Controller
 *
 * @author zuihou
 * @date 2020年03月31日10:10:36
 */
@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
@Api(value = "用户授权认证", tags = "登录接口")
public class OauthController {

    private final ValidateCodeService validateCodeService;
    private final TokenGranterBuilder tokenGranterBuilder;
    private final TokenUtil tokenUtil;
    private final OnlineService onlineService;

    @Resource
    private UserService userService;

    /**
     * 租户登录 lamp-ui 系统
     */
    @ApiOperation(value = "登录接口", notes = "登录或者清空缓存时调用")
    @PostMapping(value = "/noToken/login")
    public R<AuthInfo> login(@Validated @RequestBody LoginParamDTO login) throws BizException {
        return tokenGranterBuilder.getGranter(login.getGrantType()).grant(login);
    }

    /**
     * 注册
     */
    @ApiOperation(value = "注册接口", notes = "注册账号时调用")
    @PostMapping(value = "/noToken/register")
    public R<AuthInfo> register(@Validated @RequestBody RegisterParamDTO register) throws BizException {
        System.out.println("register = " + register);
        R<Boolean> check = this.validateCodeService.check(register.getKey(), register.getCode());
        if (check.getData() == null || !check.getData()) {
            return R.fail("验证码错误，请刷新验证码重试");
        }
        User user = new User();
        user.setState(true);
        user.setAccount(register.getAccount());
        user.setName(register.getTheName());
        user.setSalt(RandomUtil.randomString(20));
        user.setPassword(SecureUtil.sha256(register.getPassword() + user.getSalt()));
        ContextUtil.setTenant(register.getTenant());
        userService.save(user);
        return R.success(null);
    }

    @ApiOperation(value = "退出", notes = "退出")
    @PostMapping(value = "/noToken/logout")
    public R<Boolean> logout(String token, Long userId, String clientId) {
        return R.success(onlineService.clear(token, userId, clientId));
    }

    /**
     * 验证验证码
     *
     * @param key  验证码唯一uuid key
     * @param code 验证码
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @GetMapping(value = "/anno/check")
    public R<Boolean> check(@RequestParam(value = "key") String key, @RequestParam(value = "code") String code) throws BizException {
        return this.validateCodeService.check(key, code);
    }

    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping(value = "/anno/captcha", produces = "image/png")
    @IgnoreResponseBodyAdvice
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        this.validateCodeService.create(key, response);
    }

    /**
     * 验证token
     */
    @ApiOperation(value = "验证token", notes = "验证token")
    @GetMapping(value = "/anno/verify")
    public R<AuthInfo> verify(@RequestParam(value = "token") String token) throws BizException {
        return R.success(tokenUtil.getAuthInfo(token));
    }


}
