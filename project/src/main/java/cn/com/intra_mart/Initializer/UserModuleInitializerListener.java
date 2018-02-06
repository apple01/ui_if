package cn.com.intra_mart.Initializer;

import javax.servlet.ServletContextEvent;

import jp.co.intra_mart.common.platform.log.Logger;
import jp.co.intra_mart.system.servlet.listener.PlatformLifecycleException;
import jp.co.intra_mart.system.servlet.listener.PlatformLifecycleListener;

/**
 * Webアプリケーションの開始処理を行うクラスです。
 *
 * @author INTRAMART
 * @version 8.0.10
 * @since 8.0.0
 */
public class UserModuleInitializerListener implements PlatformLifecycleListener {

	/** Loggerオブジェクト */
	private static Logger imLogger = Logger.getLogger();

	@Override
	public void initialize(ServletContextEvent arg0)
			throws PlatformLifecycleException {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void start(ServletContextEvent arg0)
			throws PlatformLifecycleException {
		try {
			imLogger.info("--- Ndims UserModuleInitializerListener ---");
			// Function-Container 実行環境の初期化
			UserModuleInitializer.getInstance().initialize();

		} catch (Exception e) {
			imLogger.error(e.getMessage(), e);
		}
	}

	@Override
	public void stop(ServletContextEvent arg0)
			throws PlatformLifecycleException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void destroy(ServletContextEvent arg0)
			throws PlatformLifecycleException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
