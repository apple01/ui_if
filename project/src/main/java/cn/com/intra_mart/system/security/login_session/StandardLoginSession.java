package cn.com.intra_mart.system.security.login_session;

import jp.co.intra_mart.foundation.security.login_session.exception.LoginSessionException;
import jp.co.intra_mart.mirage.exception.SQLRuntimeException;
import jp.co.intra_mart.mirage.ext.dao.DAOFactory;
import jp.co.intra_mart.mirage.ext.session.SessionCallback;
import jp.co.intra_mart.mirage.ext.session.SessionTemplate;
import jp.co.intra_mart.mirage.session.Session;
import jp.co.intra_mart.system.security.login_session.message.LoginSessionLog;
import cn.com.intra_mart.system.security.login_session.dao.ImshsecLoginSessionDAO;

/**
 * LoginSession<br>
 *
 * @author intra-mart shanghai
 *
 */
public class StandardLoginSession {

	public int getLoginSessionInfoCount() throws LoginSessionException {
		try {
			return SessionTemplate.execute(
					new SessionCallback<Integer, SQLRuntimeException>() {
						@Override
						public Integer execute(final Session session) {
							final ImshsecLoginSessionDAO dao = DAOFactory
									.getTenantDatabaseDAO(ImshsecLoginSessionDAO.class);
							final int count = dao.getSessionCount();
							return Integer.valueOf(count);
						}
					}).intValue();
		} catch (final SQLRuntimeException e) {
			throw new LoginSessionException(
					LoginSessionLog.E_IWP_SECURITY_LOGINSESSION_00001
							.getWithCode(),
					e);
		}
	}

}
