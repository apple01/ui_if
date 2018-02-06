package cn.com.intra_mart.controller.ui_if.cert;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.foundation.security.certification.CertificationStatus;
import jp.co.intra_mart.foundation.security.certification.UserCertificationManager;
import jp.co.intra_mart.foundation.security.certification.model.LoginRequestInfo;
import jp.co.intra_mart.foundation.security.login_session.LoginSessionInfo;
import jp.co.intra_mart.foundation.security.login_session.LoginSessionManager;
import jp.co.intra_mart.foundation.security.message.MessageManager;
import jp.co.intra_mart.foundation.user_context.model.UserContext;
import jp.co.intra_mart.system.security.certification.message.CertificationSecurityLog;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.intra_mart.controller.ui_if.cert.model.LoginIfParam;

/**
 * Login操作
 *
 * @author ndims
 *
 */
@Controller
@RequestMapping("ndims/ui_if/cert")
public class CertController {

	private static final Logger logger = LoggerFactory.getLogger(CertController.class);

	@Inject
	private HttpServletResponse response;

	/**
	 * 用户Login
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody JSONObject login(@RequestBody final LoginIfParam param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/cert/login");

		try {
			result.put("error", false);
			result.put("message", "");

			int statusCode = 0;
			String statusName = "";
			String message = "";
			UserCertificationManager userCertificationManager = UserCertificationManager.getInstance();
			LoginRequestInfo loginRequestInfo = userCertificationManager.createLoginRequestInfo();

			loginRequestInfo.setUserCd(param.getUserCd());
			loginRequestInfo.setPassword(param.getPassword());

			// MessageManager.getInstance().getMessage("I18N.MESSAGE.EXAMPLE");
			// 尝试验证登陆账号
			CertificationStatus certificationStatus = userCertificationManager.checkAccount(loginRequestInfo);

			CertificationSecurityLog log = certificationStatus.toMessageId();
			if (log != null) {
				message = MessageManager.getInstance().getMessage(log.getKey());
			}
			statusName = certificationStatus.name();
			statusCode = certificationStatus.toCode();
			result.put("message", message);
			result.put("statusName", statusName);
			result.put("statusCode", statusCode);
			if (statusCode != 1 && statusCode != 3) {
				result.put("error", true);
				return result;
			}

			loginRequestInfo = userCertificationManager.initialParseRequest(loginRequestInfo);

			// 正式登陆前使当前Session无效化
			String sessionId = request.getSession().getId();
			logger.info("current session id :" + sessionId);
			LoginSessionManager loginSessionManager = new LoginSessionManager();
			LoginSessionInfo loginSessionInfo = loginSessionManager.getLoginSessionInfo(sessionId);
			if (loginSessionInfo != null) {
				loginSessionManager.invalidateLoginSessionInfo(sessionId);
				logger.info("invalidateLoginSession id :" + sessionId);
			}
			List<LoginSessionInfo> loginSessionInfos = loginSessionManager.getUserLoginSession(param.getUserCd());
			for (LoginSessionInfo info : loginSessionInfos) {
				if (!info.isInvalidate()) {
					loginSessionManager.invalidateLoginSessionInfo(info.getSessionId());
					logger.info("invalidateLoginSession id :" + info.getSessionId());
				}
			}

			// 正式登录
			certificationStatus = userCertificationManager.login(loginRequestInfo);
			log = certificationStatus.toMessageId();
			if (log != null) {
				message = MessageManager.getInstance().getMessage(log.getKey());
			}
			statusName = certificationStatus.name();
			statusCode = certificationStatus.toCode();
			result.put("message", message);
			result.put("statusName", statusName);
			result.put("statusCode", statusCode);
			result.put("loginInfo", loginRequestInfo);

			if (statusCode != 1 && statusCode != 3) {
				result.put("error", true);
			} else {
				AccountContext accountContext = Contexts.get(AccountContext.class);
				result.put("accountInfo", accountContext);

				UserContext userContext = Contexts.get(UserContext.class);
				JSONObject resultUserContext = new JSONObject();
				resultUserContext.put("userProfile", userContext.getUserProfile());
				resultUserContext.put("companyList", userContext.getCompanyList());
				resultUserContext.put("currentDepartment", userContext.getCurrentDepartment());
				resultUserContext.put("mainDepartment", userContext.getMainDepartment());
				resultUserContext.put("mainPostList", userContext.getMainPostList());
				resultUserContext.put("allPosts", userContext.getAllPosts());
				resultUserContext.put("allDepartments", userContext.getAllDepartments());
				result.put("userContext", resultUserContext);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "logIn出现异常错误。");
		}
		return result;
	}

	/**
	 * 用户是否已经Login
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "isLogin", method = RequestMethod.POST)
	public @ResponseBody JSONObject isLogin(@RequestBody final LoginIfParam param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("ndims/ui_if/cert/isLogin");
		logger.info("current session id :" + request.getSession().getId());

		JSONObject result = new JSONObject();
		try {
			result.put("error", false);
			result.put("isLogin", false);
			result.put("message", "");

			AccountContext accountContext = Contexts.get(AccountContext.class);
			if (accountContext == null) {
				result.put("isLogin", false);
				return result;
			}

			logger.info("accountContext userCd : " + accountContext.getUserCd());

			if (accountContext.getUserCd().equals(param.getUserCd())) {
				result.put("isLogin", true);
				return result;
			} else {
				result.put("isLogin", false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "logout出现异常错误。");
		}
		return result;
	}

	/**
	 * 用户Logout
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public @ResponseBody JSONObject logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("ndims/ui_if/cert/logout");
		logger.info("current session id :" + request.getSession().getId());
		JSONObject result = new JSONObject();
		try {
			result.put("error", false);
			result.put("message", "");

			AccountContext accountContext = Contexts.get(AccountContext.class);
			logger.info("current user cd :" + accountContext.getUserCd());

			// 注销
			UserCertificationManager userCertificationManager = UserCertificationManager.getInstance();
			userCertificationManager.logout();

			// 正式登陆前使当前Session无效化
			String sessionId = request.getSession().getId();
			logger.info("current session id :" + sessionId);
			LoginSessionManager loginSessionManager = new LoginSessionManager();
			LoginSessionInfo loginSessionInfo = loginSessionManager.getLoginSessionInfo(sessionId);
			if (loginSessionInfo != null) {
				loginSessionManager.invalidateLoginSessionInfo(sessionId);
				logger.info("invalidateLoginSession id :" + sessionId);
			}
			List<LoginSessionInfo> loginSessionInfos = loginSessionManager.getUserLoginSession(accountContext
					.getUserCd());
			for (LoginSessionInfo info : loginSessionInfos) {
				if (!info.isInvalidate()) {
					loginSessionManager.invalidateLoginSessionInfo(info.getSessionId());
					logger.info("invalidateLoginSession id :" + info.getSessionId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "logout出现异常错误。");
		}
		return result;
	}

}
