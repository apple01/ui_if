package cn.com.intra_mart.ui.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.intra_mart.foundation.authz.client.AuthorizationClient;
import jp.co.intra_mart.foundation.authz.client.AuthorizationClientFactory;
import jp.co.intra_mart.foundation.authz.client.AuthorizeResult;
import jp.co.intra_mart.foundation.authz.context.AuthzSubjectContext;
import jp.co.intra_mart.foundation.authz.model.Tuple;
import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.foundation.context.model.ClientContext;
import jp.co.intra_mart.foundation.i18n.locale.LocaleInfo;
import jp.co.intra_mart.foundation.i18n.locale.SystemLocale;
import jp.co.intra_mart.foundation.menu.MenuException;
import jp.co.intra_mart.foundation.menu.MenuGroupCategoryManager;
import jp.co.intra_mart.foundation.menu.MenuGroupManager;
import jp.co.intra_mart.foundation.menu.external.ExternalMenuSignature;
import jp.co.intra_mart.foundation.menu.model.MenuGroupCategory;
import jp.co.intra_mart.foundation.menu.model.MenuItemType;
import jp.co.intra_mart.foundation.menu.model.MenuTree;
import jp.co.intra_mart.foundation.ui.tags.dropdown.UIDropdown.MenuItem;
import jp.co.intra_mart.foundation.user_context.model.DepartmentPost;
import jp.co.intra_mart.foundation.user_context.model.UserContext;
import jp.co.intra_mart.system.menu.cache.MenuCacheDelegate;
import jp.co.intra_mart.system.menu.cache.MenuCacheFactory;
import jp.co.intra_mart.system.menu.tags.ImMenuTagCommon;
import jp.co.intra_mart.system.menu.tags.dropdown.ImMenuDropdown;
import jp.co.intra_mart.system.menu.tags.message.MenuTagsCap;
import jp.co.intra_mart.system.menu.tags.message.MenuTagsLog;
import jp.co.intra_mart.system.router.RoutingTableInspector;
import jp.co.intra_mart.system.router.RoutingTableInspectorFactory;
import jp.co.intra_mart.system.router.authz.user.AuthzMappedEntry;
import jp.co.intra_mart.system.ui.util.Util;

import org.jamon.escaping.Escaping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuUtils {

	private static final Logger logger = LoggerFactory.getLogger(MenuUtils.class);

	private static final String CACHEKEY = ImMenuDropdown.class.getName().concat(".im_global_nav"); //$NON-NLS-1$

	private static final String SITEMAP_URL = "menu/sitemap"; //$NON-NLS-1$

	// PC版グローバルナビのカテゴリコード
	private static final String GLOBAL_NAV_PC_CATEGORY_CD = "im_global_nav_pc";

	// SP版グローバルナビのカテゴリコード
	private static final String GLOBAL_NAV_SP_CATEGORY_CD = "im_global_nav_sp";

	// フィールドの限界の長さより大きい文字数を指定することで、特別なアイテムと認識させる
	private static final String SITEMAP_MENUID = "________________________________________________________________sitemap"; //$NON-NLS-1$

	private static final String IFRAME_PAGE_URL = "menu/common/customize_sender";

	private static final String IFRAME_PAGE_PARAM_KEY = "url";

	private static final String SIGN_PARAM_KEY = "sign";

	/** メニューカテゴリの配列 */
	protected List<String> categories = null;

	private int limitation = 5;

	List<MenuItem> menuList = new ArrayList<MenuItem>();;

	/** オプション */
	protected JSONObject options = new JSONObject();

	/**
	 * @param categorie
	 * @return
	 * @throws Exception
	 */
	public String getStrJsonMenuInfo(String[] categorie) throws Exception {
		JSONObject jsonMenuRoot = getJsonMenuInfo(categorie);
		return jsonMenuRoot.toJSONString();
	}

	/**
	 * @param categorie
	 * @return
	 * @throws Exception
	 */
	public JSONObject getJsonMenuInfo(String[] categorie) throws Exception {

		if (categorie != null && categorie.length > 0) {
			categories = new ArrayList<String>();
			for (int i = 0; i < categorie.length; i++) {
				categories.add(categorie[i]);
			}
		} else {
			initializeCategories();
		}

		final AuthzSubjectContext authz = Contexts.get(AuthzSubjectContext.class);

		final MenuGroupManager groupManager = new MenuGroupManager();

		boolean cacheableCategory = false;
		if (categories.size() == 1 && GLOBAL_NAV_SP_CATEGORY_CD.equals(categories.get(0))) {
			cacheableCategory = true;
		}

		MenuTree tmpRoot = null;
		final ClientContext client = Contexts.get(ClientContext.class);

		final MenuCacheDelegate delegate = MenuCacheFactory.getInstance("IM_TENANT-MENUDORPDOWN");
		if (cacheableCategory) {
			@SuppressWarnings("unchecked")
			final Tuple<MenuTree, Tuple<String, Long>> list = (Tuple<MenuTree, Tuple<String, Long>>) delegate
					.get(CACHEKEY);
			if (list != null) {
				if (client.getClientTypeId().equals(list.getRight().getLeft())
						&& authz.getRevision() == list.getRight().getRight().longValue()) {
					// クライアントタイプおよび認可コンテキストのリビジョンがキャッシュした時点とおなじなのでキャッシュを採用する。

					System.out.println("Use cached menu"); //$NON-NLS-1$
					tmpRoot = list.getLeft();
				} else {
					// 削除して続行
					delegate.remove(CACHEKEY);
					System.out.println("ClientType or Authz updated. Cached menu removed."); //$NON-NLS-1$
				}
			} else {
				System.out.println("Cached menu not found."); //$NON-NLS-1$
			}
		}

		if (tmpRoot == null) {
			System.out.println("Retrieving menu data from database"); //$NON-NLS-1$
			// キャッシュから取得できていないので、APIを使用して取得
			final List<String> ids = groupManager.getAvailableMenuGroupIds(categories.toArray(new String[categories
					.size()]));
			tmpRoot = groupManager.getAvailableMenuTreeWithId(ids.toArray(new String[ids.size()]));
			if (tmpRoot == null) {
				tmpRoot = new MenuTree();
			}

			// 別サーバから、メニューツリーを取得してマージする
			tmpRoot = ImMenuTagCommon.getExternalData(tmpRoot, this.categories, true);

			// 网站地图
			addSitemapLink(tmpRoot);

			if (cacheableCategory) {
				System.out.println("Retrieved menu data put into cache"); //$NON-NLS-1$
				// キャッシュしてよいカテゴリなら、キャッシュする。
				delegate.put(
						CACHEKEY,
						new Tuple<MenuTree, Tuple<String, Long>>(tmpRoot, new Tuple<String, Long>(client
								.getClientTypeId(), authz.getRevision())));
			}
		}

		JSONObject jsonMenuRoot = cnvJsonMenuItem(tmpRoot, 0);
		return jsonMenuRoot;

	}

	/**
	 * @param nemuTree
	 * @param level
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject cnvJsonMenuItem(MenuTree nemuTree, int level) throws Exception {

		AccountContext accountContext = Contexts.get(AccountContext.class);
		Locale locale = accountContext.getLocale();

		jp.co.intra_mart.foundation.menu.model.MenuItem menu = nemuTree.getMenuItem();

		JSONObject jMenu = new JSONObject();
		if (menu != null) {
			String targetUrl = menu.getUrl();
			targetUrl = Util.escape(targetUrl, Escaping.STRICT_HTML);
			jMenu.put("id", menu.getId());
			jMenu.put("label", menu.getDisplayName(locale));
			jMenu.put("url", targetUrl);
			jMenu.put("method", menu.getMethod());
			jMenu.put("type", menu.getType().getMenuType());
			jMenu.put("useIframe", menu.isUseIframe());
			jMenu.put("usePopup", menu.isUsePopup());
			jMenu.put("imagePath", menu.getImagePath());
			jMenu.put("arguments", translateArguments(menu.getArguments()));
			jMenu.put("hasChildren", nemuTree.hasChildren());
			jMenu.put("level", level);
		}
		JSONArray children = new JSONArray();
		jMenu.put("children", children);

		if (nemuTree.hasChildren()) {
			for (int i = 0; i < nemuTree.getChildren().length; i++) {
				MenuTree childMenu = nemuTree.getChildren()[i];
				JSONObject jChildMenu = cnvJsonMenuItem(childMenu, level + 1);
				children.add(jChildMenu);
			}
		}

		return jMenu;
	}

	/**
	 * @param arguments
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject translateArguments(final Map<String, String> arguments) {
		final JSONObject obj = new JSONObject();
		if (arguments != null) {
			for (final Entry<String, String> entry : arguments.entrySet()) {
				obj.put(entry.getKey(), entry.getValue());
			}
		}

		return obj;
	}

	/**
	 * @throws MenuException
	 */
	private void initializeCategories() throws MenuException {
		if (categories != null) {
			return;
		}
		final MenuGroupCategoryManager categoryManager = new MenuGroupCategoryManager();
		final List<String> ids = new ArrayList<String>();
		for (final MenuGroupCategory cate : categoryManager.getAllCategories()) {
			ids.add(cate.getCategory());
		}
		categories = ids;
	}

	/**
	 * @param root
	 */
	private void addSitemapLink(final MenuTree root) {
		final RoutingTableInspector router = RoutingTableInspectorFactory.getInstance().getRoutingTableInspector();
		final AuthzMappedEntry entry = router.getProperty(
				"/" + SITEMAP_URL, new HashMap<String, String[]>(), AuthzMappedEntry.class); //$NON-NLS-1$

		if (entry == null) {
			// SITEMAP_URLに 外部サイトが設定されているわけもないので、利用不可にする
			return;
		}

		final String uri = entry.getUri();
		final AuthorizationClient authz = AuthorizationClientFactory.getInstance().getAuthorizationClient();
		final AuthorizeResult result = authz.authorize(uri, "execute"); //$NON-NLS-1$
		if (!AuthorizeResult.Permit.equals(result)) {
			return;
		}

		final jp.co.intra_mart.foundation.menu.model.MenuItem menuItem = new jp.co.intra_mart.foundation.menu.model.MenuItem();
		menuItem.setId(SITEMAP_MENUID);
		menuItem.setMethod("GET"); //$NON-NLS-1$
		menuItem.setOriginalMenuId(SITEMAP_MENUID);
		menuItem.setType(MenuItemType.ITEM);
		menuItem.setUrl(SITEMAP_URL);

		resolveMessageCodeDisplay(menuItem, MenuTagsCap.CAP_Z_IWP_MENU_TAGS_SITEMAP);

		final MenuTree menuTree = new MenuTree(menuItem);
		root.addChild(menuTree);
	}

	/**
	 * @param item
	 * @param code
	 */
	private void resolveMessageCodeDisplay(final jp.co.intra_mart.foundation.menu.model.MenuItem item,
			final MenuTagsCap code) {
		for (final LocaleInfo localeInfo : SystemLocale.getLocaleInfos()) {
			final Locale locale = localeInfo.getLocale();
			item.setDisplayName(locale, code.get(locale));
		}
	}

	/**
	 * 外部メニューの URL を Iframe 内に表示するための URL に変換します。
	 *
	 * @param url
	 *            リンク先URL
	 * @param encoding
	 *            URLパラメータのエンコーディング
	 * @return Iframe 内に表示するための URL
	 * @throws MenuException
	 *             変換に失敗した場合に発生します。
	 */
	public static String getCusIframeUrl(final String url, final String encoding) throws MenuException {
		if (url == null || url.isEmpty()) {
			return url;
		}

		try {
			return IFRAME_PAGE_URL + "?" + IFRAME_PAGE_PARAM_KEY + "=" + URLEncoder.encode(url, encoding) + "&"
					+ SIGN_PARAM_KEY + "=" + URLEncoder.encode(ExternalMenuSignature.getInstance().make(url), encoding);
		} catch (final UnsupportedEncodingException e) {
			throw new MenuException(MenuTagsLog.E_IWP_MENU_TAGS_10004.getWithCode(), e);
		}
	}

	/**
	 * 填充omip用户菜单topnav<br>
	 * 将iAP的用户信息填充
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonUserPostInfo() {
		JSONObject userInfo = new JSONObject();
		try {
			UserContext usertContext = Contexts.get(UserContext.class);
			userInfo.put("id", usertContext.getUserProfile().getUserCd());
			userInfo.put("username", usertContext.getUserProfile().getUserName());

			// 填充所有所属
			List<DepartmentPost> allPosts = usertContext.getAllPosts();
			JSONArray joblist = new JSONArray();
			JSONObject job = null;
			DepartmentPost departmentPost = null;
			HashMap<String, JSONObject> jobsMap = new HashMap<String, JSONObject>();
			String jobId = "";
			for (int i = 0; i < allPosts.size(); i++) {
				// 对想相同部门，兼职多个职务的数据进行合并
				departmentPost = allPosts.get(i);
				jobId = departmentPost.getCompanyCd() + "|" + departmentPost.getDepartmentSetCd() + "|"
						+ departmentPost.getDepartmentCd();
				JSONObject sameDep = jobsMap.get(jobId);
				if (sameDep == null) {
					job = new JSONObject();
					job.put("id", jobId);
					job.put("name", departmentPost.getDepartmentFullName() + " " + departmentPost.getPostName());
					String value = "javascript:ndimsfw.changeUserContext(\"" + departmentPost.getCompanyCd() + "\", \""
							+ departmentPost.getDepartmentCd() + "\", \"" + departmentPost.getDepartmentSetCd() + "\")";
					job.put("value", value);
					job.put("companyCd", departmentPost.getCompanyCd());
					job.put("departmentSetCd", departmentPost.getDepartmentSetCd());
					job.put("departmentCd", departmentPost.getDepartmentCd());
					jobsMap.put(jobId, job);
					joblist.add(job);
				} else {
					job = sameDep;
					// 追加同一部门的兼职职务名
					job.put("name", job.get("name") + " " + departmentPost.getPostName());
				}
			}
			userInfo.put("joblists", joblist);

			// 当前有效的部门
			String currJobName = "无所属部门和职务";
			String currJobId = "";
			if (usertContext.getCurrentDepartment() != null) {
				currJobId = usertContext.getCurrentDepartment().getCompanyCd() + "|"
						+ usertContext.getCurrentDepartment().getDepartmentSetCd() + "|"
						+ usertContext.getCurrentDepartment().getDepartmentCd();
				for (int i = 0; i < joblist.size(); i++) {
					job = (JSONObject) joblist.get(i);
					jobId = (String) job.get("id");
					if (jobId.equals(currJobId)) {
						currJobName = (String) job.get("name");
					}
				}
			}
			userInfo.put("currJobId", currJobId);
			userInfo.put("currJobName", currJobName);

			// 填充主所属
			String mainJobId = "";
			String mainJobName = "无主所属部门";
			if (usertContext.getMainDepartment() != null) {
				mainJobId = usertContext.getMainDepartment().getCompanyCd() + "|"
						+ usertContext.getMainDepartment().getDepartmentSetCd() + "|"
						+ usertContext.getMainDepartment().getDepartmentCd();

				userInfo.put("mainCompanyCd", usertContext.getMainDepartment().getCompanyCd());
				userInfo.put("mainDepartmentSetCd", usertContext.getMainDepartment().getDepartmentSetCd());
				userInfo.put("mainDepartmentCd", usertContext.getMainDepartment().getDepartmentCd());

				for (int i = 0; i < joblist.size(); i++) {
					job = (JSONObject) joblist.get(i);
					jobId = (String) job.get("id");
					if (jobId.equals(mainJobId)) {
						mainJobName = (String) job.get("name");
					}
				}
			}
			userInfo.put("mainJobId", mainJobId);
			userInfo.put("mainJobName", mainJobName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

}
