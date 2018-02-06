package cn.com.intra_mart.Initializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.cassandra.thrift.Cassandra.AsyncProcessor.system_add_column_family;

import jp.co.intra_mart.common.aid.jdk.java.lang.ClassUtil;
import jp.co.intra_mart.common.aid.jdk.java.util.ServiceLoaderUtil;
import jp.co.intra_mart.foundation.config.ConfigurationException;
import jp.co.intra_mart.foundation.config.ConfigurationLoader;
import jp.co.intra_mart.foundation.log.MessageCodeLogger;
import jp.co.intra_mart.system.modules.model.ModuleKey;
import jp.co.intra_mart.system.modules.model.impl.ModuleImpl;
import jp.co.intra_mart.system.platform.ServerContext;
import jp.co.intra_mart.system.platform.message.PlatformLog;
import jp.co.intra_mart.system.secure.product.initializer.config.ApplicationInitializerConfig;
import jp.co.intra_mart.system.secure.product.initializer.config.ApplicationInitializerConfig.ClassName;
import jp.co.intra_mart.system.secure.product.initializer.config.InitializerConfig;
import jp.co.intra_mart.system.secure.product.initializer.config.JavaScriptApiType;
import jp.co.intra_mart.system.secure.product.initializer.config.JsspTagType;
import jp.co.intra_mart.system.service.ApplicationInitializer;
import jp.co.intra_mart.system.service.mbean.ApplicationInitializerExecutor;
import jp.co.intra_mart.system.service.mbean.ApplicationInitializerExecutorMBean;
import jp.co.intra_mart.system.service.provider.ApplicationRuntimeInitializer;
import jp.co.intra_mart.system.service.provider.JavaScriptApiInitializer;
import jp.co.intra_mart.system.service.provider.platform.ApplicationInitializerDispatcher;

public class UserModuleInitializer implements ApplicationInitializer {

	/**
	 * 初期化処理の状態を表す列挙型です。
	 *
	 * @author INTRAMART
	 * @version 8.0.10
	 */
	public enum InitializerStatus {
		/** 未完了 */
		READY,
		/** 完了 */
		COMPLETE,
		/** 初期化処理中に例外が発生 */
		FAILED
	}

	private static final MessageCodeLogger LOGGER = MessageCodeLogger
			.getLogger(ApplicationRuntimeInitializer.class);

	/** 自定义 UserModule的模块名定义文件 */
	private static final String USER_MODULE_PROP_FILE = "conf/ndims-user-module/ndims-user-module.properties";

	/** ApplicationInitializer設定ファイルディレクトリ */
	private static final String INITIALIZER_DIRECTORY = "conf/products/initializer";

	/** 置換文字列 */
	private static final String INITIALIZER_PREFIX = "initializer-";

	/** 置換文字列 (後処理) */
	private static final String INITIALIZER_POST_PREFIX = "initializer-post-";

	/** 置換文字列 (サンプル) */
	private static final String SAMPLE_PREFIX = "sample-";

	/** ApplicationInitializer設定ファイルパターン */
	private static final String FILE_EXTENSION = ".xml";

	private static UserModuleInitializer instance = new UserModuleInitializer();

	private final Map<String, InitializerConfig> configurationMap;

	private InitializerStatus status = InitializerStatus.READY;

	private UserModuleInitializer() {
		super();
		this.configurationMap = new HashMap<String, InitializerConfig>();
		init();
	}

	private void init() {
		// 設定ファイルの取得
		final File initializerDir = new File(ServerContext.getInstance()
				.getHomeDirectoryPath(), INITIALIZER_DIRECTORY);
		if (!initializerDir.isDirectory()) {
			LOGGER.write(PlatformLog.W_IWP_PLATFORM_00001,
					initializerDir.getAbsolutePath());
			return;
		}
		Unmarshaller unmarshaller = null;
		try {
			final JAXBContext context = JAXBContext
					.newInstance(InitializerConfig.class);
			unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(ConfigurationLoader
					.findSchema(InitializerConfig.class));
		} catch (final JAXBException e) {
			// unmarshallerの生成に失敗
			throw new IllegalStateException(
					PlatformLog.E_IWP_PLATFORM_00001.getWithCode(), e);
		} catch (final ConfigurationException e) {
			// スキーマが見つからない
			throw new IllegalStateException(e);
		}

		final File[] initializeFiles = initializerDir
				.listFiles(new InitializerFileFilter());
		if (initializeFiles != null) {
			for (final File file : initializeFiles) {
				try {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Load Configuration File : {}",
								file.getName());
					}
					final InitializerConfig config = (InitializerConfig) unmarshaller
							.unmarshal(file);
					this.configurationMap.put(file.getName(), config);
				} catch (final JAXBException e) {
					// initializer-config読み込みエラー
					throw new IllegalStateException(
							PlatformLog.E_IWP_PLATFORM_00001.getWithCode(file
									.getAbsolutePath()), e);
				}
			}
		}
	}

	/**
	 * ApplicationRuntimeInitializerインスタンスを取得します。
	 *
	 * @return ApplicationRuntimeInitializerインスタンス
	 */
	public static UserModuleInitializer getInstance() {
		return instance;
	}

	/**
	 * 初期化処理を実行します。
	 */
	@Override
	public synchronized void initialize() {
		// TODO
		try {
			Properties props = new Properties();
			try {
				String propFilePath = ServerContext.getInstance()
						.getHomeDirectoryPath() + "/" + USER_MODULE_PROP_FILE;
				props.load(new FileInputStream(propFilePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String valUserModulIds = props.getProperty("userModulIds");
			if (valUserModulIds == null) {
				return;
			}
			String[] listUserModulIds = valUserModulIds.split(",");

			Collection<ModuleKey> modules = new ArrayList<ModuleKey>();

			for (int i = 0; i < listUserModulIds.length; i++) {
				System.out.println("--- Ndims UserModuleInitializer : [" + listUserModulIds[i] +  "] ---");
				ModuleKey module = new ModuleImpl(listUserModulIds[i], "0",
						listUserModulIds[i], "Intramart Shanghai",
						"userModule deploy ", null, null, null, null, false);
				modules.add(module);
			}

			// 初期化
			executeInitializer(modules, INITIALIZER_PREFIX);
			// 後処理初期化
			executeInitializer(modules, INITIALIZER_POST_PREFIX);
			// サンプル初期化
			executeInitializer(modules, SAMPLE_PREFIX);
		} catch (final RuntimeException e) {
			this.status = InitializerStatus.FAILED;
			throw e;
		}
		this.status = InitializerStatus.COMPLETE;
	}

	/**
	 * 指定初始化指定的模块的initialize文件
	 *
	 * @param moduleId
	 */
	public synchronized void initialize(String moduleId) {
		// TODO
		try {

			if (moduleId == null || moduleId.length() == 0) {
				return;
			}

			Collection<ModuleKey> modules = new ArrayList<ModuleKey>();
			System.out.println("--- Ndims UserModuleInitializer : [" + moduleId +  "] ---");
			ModuleKey module = new ModuleImpl(moduleId, "0", moduleId,
					"Intramart Shanghai", "userModule deploy ", null, null,
					null, null, false);
			modules.add(module);

			// 初期化
			executeInitializer(modules, INITIALIZER_PREFIX);
			// 後処理初期化
			executeInitializer(modules, INITIALIZER_POST_PREFIX);
			// サンプル初期化
			executeInitializer(modules, SAMPLE_PREFIX);
		} catch (final RuntimeException e) {
			this.status = InitializerStatus.FAILED;
			throw e;
		}
		this.status = InitializerStatus.COMPLETE;
	}

	/**
	 * アプリケーションの初期化処理の状態を返却します。
	 *
	 * @return 状態
	 */
	public InitializerStatus getStatus() {
		return this.status;
	}

	private void executeInitializer(final Collection<ModuleKey> modules,
			final String prefix) {
		for (final ModuleKey module : modules) {
			final String moduleId = module.getId();
			final int index = moduleId.lastIndexOf(".");
			final String fileName = prefix
					.concat(moduleId.substring(index + 1)).concat(
							FILE_EXTENSION);
			final InitializerConfig config = this.configurationMap
					.get(fileName);
			if (config == null) {
				continue;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Set Configuration File : {}", fileName);
			}
			defineAPI(config);
		}
	}

	private void defineAPI(final InitializerConfig config) {
		final JavaScriptApiInitializer initializer = ServiceLoaderUtil
				.loadFirst(JavaScriptApiInitializer.class);
		try {
			initializer.initialize();
			final JavaScriptApiType api = config.getJavaScriptApi();
			if (api != null) {
				// JavaScriptグローバル関数の設定
				initializer.defineJSGlobalFunc4Class(api
						.getGlobalFunctionClass());
				initializer.defineJSGlobalFunc4Script(api
						.getGlobalFunctionScript());
				// 基本APIの設定
				initializer.defineJavaScriptAPIClass(api.getApiClass());
				initializer.defineJavaScriptAPIScript(api.getApiScript());
			}

			final JsspTagType tags = config.getJsspTag();
			if (tags != null) {
				// IMARTタグの設定
				initializer.defineImartTag4Class(tags.getTagClass());
				initializer.defineImartTag4Script(tags.getTagScript());
			}

			final ApplicationInitializerConfig inits = config.getInitializer();
			if (inits != null) {
				// 初期化処理
				initializeApplicationClass(inits.getClassName());
				initializer.initializeApplicationScript(inits.getScriptName());
			}
		} finally {
			initializer.dispose();
		}
	}

	/**
	 * アプリケーションの初期化処理(Java)を実行します。
	 *
	 * @param names
	 *            初期化クラス名配列
	 * @throws ClassNotFoundException
	 *             クラスが存在しない場合にスローされます。
	 * @throws InstantiationException
	 *             インスタンスの生成に失敗した場合にスローされます。
	 * @throws IllegalAccessException
	 *             指定したファンクションの呼び出しに失敗した場合にスローされます。
	 */
	// @formatter:off
	private void initializeApplicationClass(final List<ClassName> names) {
		// @formatter:on
		if (names.size() == 0) {
			return;
		}
		final ApplicationInitializerDispatcher dispatcher = ApplicationInitializerDispatcher
				.getInstance();
		for (final ClassName name : names) {
			try {
				LOGGER.write(PlatformLog.I_IWP_PLATFORM_00001, name.getValue());
				dispatcher.execute(name.getValue());
			} catch (final ClassNotFoundException e) {
				// クラスが存在しない
				throw new IllegalStateException(
						PlatformLog.E_IWP_PLATFORM_00012.getWithCode(name
								.getValue()), e);
			} catch (final InstantiationException e) {
				// インスタンスの生成に失敗
				throw new IllegalStateException(
						PlatformLog.E_IWP_PLATFORM_00012.getWithCode(name
								.getValue()), e);
			} catch (final IllegalAccessException e) {
				// アクセス不可
				throw new IllegalStateException(
						PlatformLog.E_IWP_PLATFORM_00012.getWithCode(name
								.getValue()), e);
			}
		}
	}

	/**
	 * ApplicationInitializer実行用のMeanを返却します。<br/>
	 *
	 * @return MBean配列
	 */
	public ApplicationInitializerExecutorMBean[] createMBeans() {
		final List<ApplicationInitializerExecutorMBean> mbeans = new ArrayList<ApplicationInitializerExecutorMBean>();
		for (final InitializerConfig config : this.configurationMap.values()) {
			if (config.getInitializer() == null) {
				continue;
			}
			for (final ClassName className : config.getInitializer()
					.getClassName()) {
				if (!className.getMBean()) {
					continue;
				}
				final Class<?> initializer = ClassUtil.forName(className
						.getValue());
				if (ApplicationInitializer.class.isAssignableFrom(initializer)) {
					mbeans.add(new ApplicationInitializerExecutor(initializer
							.asSubclass(ApplicationInitializer.class)));
				} else {
					LOGGER.write(PlatformLog.W_IWP_PLATFORM_00002,
							className.getValue());
				}
			}
		}
		return mbeans.toArray(new ApplicationInitializerExecutorMBean[mbeans
				.size()]);
	}

	private static class InitializerFileFilter implements FilenameFilter {
		@Override
		public boolean accept(final File dir, final String name) {
			return name.endsWith(FILE_EXTENSION);
		}
	}

}
