package cn.com.intra_mart.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import jp.co.intra_mart.common.aid.jdk.java.io.IOUtil;
import jp.co.intra_mart.foundation.service.client.file.PublicStorage;
import jp.co.intra_mart.foundation.service.client.file.SessionScopeStorage;
import jp.co.intra_mart.system.service.client.file.StorageInformationImpl;

public class FileUtil {

	/**
	 * 把sessionStorage下的文件保存到Public
	 *
	 * @param outsidePath
	 *            要保存的目标文件路径
	 * @param publicFileName
	 *            sessionStorage下的文件名
	 * @throws IOException
	 */
	public static void copySessionFile2Pubilc(String sessionStoragePath, String sessionFileName,
			String publicStoragePath, String publicFileName) throws Exception {

		InputStream is = null;
		OutputStream os = null;
		try {
			SessionScopeStorage sessionStorage = new SessionScopeStorage(sessionStoragePath, sessionFileName);
			if (!sessionStorage.exists() || !sessionStorage.isFile()) {
				throw new Exception("FileUtil#copySessionFile2Pubilc 复制元文件不存在。");
			}

			// 打开复制元文件，进行内容复制
			is = sessionStorage.open();

			PublicStorage publicStorager = new PublicStorage(publicStoragePath);
			if (publicStorager == null || !publicStorager.isDirectory()) {
				publicStorager.makeDirectories();
			}
			publicStorager = new PublicStorage(publicStoragePath + "/" + publicFileName);
			os = publicStorager.create();
			IOUtil.transfer(is, os);

		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * @param 获得文件名中的扩展名
	 * @return 文件名中的扩展名(小写返回)
	 */
	public static String getFileExtName(String fileOrgname) {

		if (fileOrgname == null) {
			return null;
		}
		// 取得文件的扩展名
		int lastDotPosition = fileOrgname.lastIndexOf(".");
		String extFileName = "";
		if (lastDotPosition != -1) {
			extFileName = fileOrgname.substring(lastDotPosition + 1).toLowerCase();
		}
		return extFileName;
	}

	/**
	 * @param 获得文件名中的除去扩展名的文件名
	 * @return
	 */
	public static String getFileFrontName(String fileOrgname) {

		if (fileOrgname == null) {
			return null;
		}
		// 取得文件的扩展名
		int lastDotPosition = fileOrgname.lastIndexOf(".");
		String frontFileName = "";
		if (lastDotPosition != -1) {
			frontFileName = fileOrgname.substring(0, lastDotPosition);
		}
		return frontFileName;
	}

	/**
	 * 获得PublicStorage的绝对路径
	 *
	 * @return
	 */
	public static String getPublicStorageRealPath() {
		// AccountContext accountContext = Contexts.get(AccountContext.class);

		StorageInformationImpl impl = new StorageInformationImpl();
		String destPublic = impl.getPublicStorageRootPath();

		return destPublic;

	}

	/**
	 * 获得文件内容Text
	 *
	 * @param filePathName
	 * @return
	 */
	public static String getFileText(String filePathName) {

		StringBuilder builder = new StringBuilder("");

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(filePathName), "UTF-8"));
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

}
