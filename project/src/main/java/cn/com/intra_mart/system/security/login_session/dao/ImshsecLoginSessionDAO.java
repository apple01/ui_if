package cn.com.intra_mart.system.security.login_session.dao;

import jp.co.intra_mart.mirage.ext.dao.AbstractDAO;
import jp.co.intra_mart.system.security.login_session.entity.ImsecLoginSession;
import jp.co.intra_mart.system.security.login_session.entity.SearchLoginSessionCondition;

/**
 * imsec_login_session表操作类DAO.
 * 
 * @author INTRAMART
 * @version 8.0.0
 */
public class ImshsecLoginSessionDAO extends AbstractDAO<ImsecLoginSession> {

	private static final String SQL_PATH = "jp/co/intra_mart/system/security/login_session/";

	/**
	 * 登录session数取得。
	 * 
	 * @return 登录session数
	 */
	public int getSessionCount() {
		final SearchLoginSessionCondition condition = new SearchLoginSessionCondition();
		condition.invalidateFlag = "0";
		return sqlManager.getSingleResult(Integer.class,
				SQL_PATH.concat("select_session_count.sql"), condition);
	}
}
