package cn.com.intra_mart.controller.ui_if.menu;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.intra_mart.ui.utils.MenuUtils;

/**
 * 菜单信息
 *
 * @author ndims
 *
 */
@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("ndims/ui_if/menu")
public class MenuController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Inject
	private HttpServletResponse response;

	/**
	 * 获得全局导航菜单（PC版）数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getGlobalNavSp", method = RequestMethod.POST)
	public @ResponseBody JSONObject getGlobalNavSp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getGlobalNavSp");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			String[] categorie = new String[] { "im_global_nav_sp" };
			JSONObject iAPMenuRoot = menuUtils.getJsonMenuInfo(categorie);
			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getGlobalNavSp信息出现异常错误。");
		}
		return result;
	}

	/**
	 * 获得全局导航菜单（移动机版）数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getGlobalNavPc", method = RequestMethod.POST)
	public @ResponseBody JSONObject getGlobalNavPc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getGlobalNavPc");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			String[] categorie = new String[] { "im_global_nav_pc" };
			JSONObject iAPMenuRoot = menuUtils.getJsonMenuInfo(categorie);
			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getGlobalNavPc信息出现异常错误。");
		}
		return result;
	}

	/**
	 * 获得个人设定菜单（PC版）数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getPersonalSettingsPc", method = RequestMethod.POST)
	public @ResponseBody JSONObject getPersonalSettingsPc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getPersonalSettingsPc");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			String[] categorie = new String[] { "im_personal_settings_pc" };
			JSONObject iAPMenuRoot = menuUtils.getJsonMenuInfo(categorie);
			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getPersonalSettingsPc信息出现异常错误。");
		}
		return result;
	}

	/**
	 * 获得网站地图菜单（PC版）数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getSitemapPc", method = RequestMethod.POST)
	public @ResponseBody JSONObject getSitemapPc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getSitemapPc");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			String[] categorie = new String[] { "im_sitemap_pc" };
			JSONObject iAPMenuRoot = menuUtils.getJsonMenuInfo(categorie);
			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getSitemapPc信息出现异常错误。");
		}
		return result;
	}

	/**
	 * 获得网站地图菜单（移动版）数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getSitemapSp", method = RequestMethod.POST)
	public @ResponseBody JSONObject getSitemapSp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getSitemapSp");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			String[] categorie = new String[] { "im_sitemap_sp" };
			JSONObject iAPMenuRoot = menuUtils.getJsonMenuInfo(categorie);
			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getSitemapSp信息出现异常错误。");
		}
		return result;
	}

	/**
	 * 获得个人所属菜单数据
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getUserDepInfo", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserDepInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject result = new JSONObject();

		logger.info("ndims/ui_if/menu/getUserDepInfo");

		try {
			result.put("error", false);
			result.put("message", "");

			MenuUtils menuUtils = new MenuUtils();
			JSONObject iAPMenuRoot = menuUtils.getJsonUserPostInfo();

			result.put("menuRoot", iAPMenuRoot);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("message", "获得菜单getUserDepInfo信息出现异常错误。");
		}
		return result;
	}

}
