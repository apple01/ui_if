package cn.com.intra_mart.controller.ui_if.login_session;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.intra_mart.system.security.login_session.StandardLoginSession;

/**
 * loginSession控制层(Controller)<br>
 *
 * @author intra-mart shanghai
 *
 */
@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("ndims/ui_if/login_session")
public class LoginSessionController {

	/**
	 * 获得在线用户人数
	 *
	 * @return 在线用户人数
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "getLoginSessionInfoCount", method = RequestMethod.POST)
	public @ResponseBody final Map<String, String> getLoginSessionInfoCount() throws Exception {
		int count = 0;
		try {
			StandardLoginSession standardLoginSession = new StandardLoginSession();
			count = standardLoginSession.getLoginSessionInfoCount();
		} catch (Exception e) {
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("count", String.valueOf(count));
		return result;
	}
}
