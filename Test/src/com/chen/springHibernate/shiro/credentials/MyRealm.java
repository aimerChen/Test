package com.chen.springHibernate.shiro.credentials;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.service.UserService;
import com.google.gson.Gson;

/**
 * doGetAuthenticationInfo:认证用户，即是否存在该用户<br/>
 * doGetAuthorizationInfo:授权用户，即满足条件的用户可以得到授权<br/>
 * @author Administrator
 *
 */
@Service("MyRealm")
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	
	/**
	 * 为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试:本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
	 * @see 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,
	 *      则可灵活决定是否启用AuthorizationCache
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
		String currentUsername = (String) super.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		// 从数据库中获取当前登录用户的详细信息
		List<User> userList=userService.findUsersByName(currentUsername);
		User user=null;
		if (null != userList&&userList.size()>0) {
			user=userList.get(0);
			// 实体类User中包含有用户角色的实体类信息
			if (null != user.getRoles() && user.getRoles().size() > 0) {
				// 获取当前登录用户的角色
				List<String> roles=user.getRoles();
				for (String role : roles) {
					roleList.add(role);
					// 实体类Role中包含有角色权限的实体类信息
				}
			}
		} else {
			//普通用户
			
		}
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		System.out.println("doGetAuthorizationInfo:用户"+currentUsername+"验证角色");
		// 实际中可能会像上面注释的那样从数据库取得
		if (null != currentUsername && "mike".equals(currentUsername)) {
			// 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
			simpleAuthorInfo.addRole("admin");
			// 添加权限
//			simpleAuthorInfo.addStringPermission("admin:manage");
			System.out.println("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
		}
		
		if (null != currentUsername && "chen".equals(currentUsername)) {
			// 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
			simpleAuthorInfo.addRole("sysUser");
			// 添加权限
//			simpleAuthorInfo.addStringPermission("sysUser:manage");
			System.out.println("已为用户[chen]赋予了[sysUser]角色和[sysUser:manage]权限");
		}
		// 若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
		// 详见applicationContext.xml中的<bean id="shiroFilter">的配置
		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		/* 这里编写登陆认证代码 */
		String username = (String) token.getPrincipal();
		List<User> userList = userService.findUsersByName(username);
		System.out.println("gson:" + new Gson().toJson(userList));
		if (userList.size() != 0) {
			if (userList.size() != 0&&1 == userList.get(0).getLocked()) {
				throw new LockedAccountException(); // 帐号锁定
			}
			// 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
			// 当用户执行登录时,在方法处理上要实现user.login(token);
			// 然后会自动进入这个类进行认证
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现

			SimpleAuthenticationInfo authenticationInfo = 
					new SimpleAuthenticationInfo(username,userList.get(0).getPassword(), getName()); // realm name

			authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userList.get(0).getSalt())); // salt=username+salt
			// 当验证都通过后，把用户信息放在session里
			return authenticationInfo;
		} else {
			throw new UnknownAccountException();// 没找到帐号
		}
	}

}
