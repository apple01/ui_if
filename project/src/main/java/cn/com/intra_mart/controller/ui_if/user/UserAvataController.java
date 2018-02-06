package cn.com.intra_mart.controller.ui_if.user;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.intra_mart.common.aid.jdk.java.io.IOUtil;
import jp.co.intra_mart.common.aid.jdk.java.lang.IORuntimeException;
import jp.co.intra_mart.foundation.BaseUrl;
import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.foundation.service.client.file.PublicStorage;
import jp.co.intra_mart.foundation.service.client.file.SessionScopeStorage;
import jp.co.intra_mart.foundation.user_context.model.UserContext;
import jp.co.intra_mart.framework.extension.spring.web.servlet.view.TransferView;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;
import cn.com.intra_mart.common.util.FileUtil;
import cn.com.intra_mart.common.util.ImageUtil;
import cn.com.intra_mart.controller.ui_if.user.model.ImageCropParam;

/**
 * 头像操作
 *
 * @author ndims
 *
 */
@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("ndims/user/avatar")
public class UserAvataController {

	private static final Logger logger = LoggerFactory.getLogger(UserAvataController.class);

	@Inject
	private HttpServletResponse response;

	@RequestMapping(value = "upload")
	public String upload(final Model model, final HttpServletRequest request) throws Exception {

		logger.info("ndims/user/avatar.upload");

		try {
			// baseUrl
			String baseUrl = BaseUrl.get();
			if (baseUrl == null || baseUrl.trim().length() == 0) {
				int idx = request.getRequestURL().indexOf(request.getContextPath());
				baseUrl = request.getRequestURL().substring(0, idx) + request.getContextPath() + "/";
			} else if (baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
				baseUrl += "/";
			}
			model.addAttribute("base", baseUrl);

			// extendThemePath
			AccountContext ccountContext = Contexts.get(AccountContext.class);
			String extendThemePath = "ui/theme/" + ccountContext.getThemeId() + "/extend_theme";
			model.addAttribute("extendThemePath", extendThemePath);

			// extendThemePath
			String themePath = "ui/theme/" + ccountContext.getThemeId();
			model.addAttribute("themePath", themePath);

			UserContext userContext = Contexts.get(UserContext.class);
			// 登录用户Cd
			model.addAttribute("userCd", userContext.getUserProfile().getUserCd());
			// 登录用户名
			model.addAttribute("userName", userContext.getUserProfile().getUserName());

		} catch (Exception e) {
			e.printStackTrace();
			return TransferView.VIEW_NAME_ERROR;
		}

		return "/ndims/user/avata.jsp";
	}

	/**
	 * 上传头像
	 *
	 * @param uploadFile
	 * @return 返回上传头像结果
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "set", method = RequestMethod.POST)
	public @ResponseBody JSONObject setAvatar(final @RequestParam(value = "file") MultipartFile uploadFile) {
		JSONObject result = new JSONObject();

		logger.info("ndims/user/avatar.set");

		result.put("error", false);
		result.put("errorMessage", "");
		if (!uploadFile.isEmpty()) {
			OutputStream os = null;
			AccountContext accountContext = Contexts.get(AccountContext.class);

			SessionScopeStorage dir = new SessionScopeStorage("user/avatar");
			try {
				if (dir == null || !dir.isDirectory()) {
					dir.makeDirectories();
				}
				String fileName = accountContext.getUserCd();
				final SessionScopeStorage storage = new SessionScopeStorage("user/avatar", fileName);
				os = storage.create();
				IOUtil.transfer(uploadFile.getInputStream(), os);
			} catch (final IOException e) {
				result.put("error", true);
				result.put("errorMessage", "头像上传失败！");
				return result;
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (final IOException e) {
					result.put("error", true);
					result.put("errorMessage", "文件上传失败！");
					return result;
				}
			}
			return result;
		}
		return result;
	}

	/**
	 * 上传头像
	 *
	 * @param uploadFile
	 * @return 返回上传头像结果
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings({ "unchecked", "restriction" })
	@RequestMapping(value = "setFromApp", method = RequestMethod.POST)
	public @ResponseBody JSONObject setAvatarFromApp(@RequestBody final ImageCropParam cropParam) {
		JSONObject result = new JSONObject();

		logger.info("ndims/user/avatar.setFromApp");

		result.put("error", false);
		result.put("errorMessage", "");

		try {
			AccountContext accountContext = Contexts.get(AccountContext.class);

			String sessionStoragePath = "user/avatar";
			String sessionFileName = accountContext.getUserCd();
			String publicStoragePath = "user/avatar";
			String publicFileName = accountContext.getUserCd() + ".png";

			BASE64Decoder decoder = new BASE64Decoder();
			OutputStream os = null;

			try {
				String image = cropParam.getImgContent();
				image = image.split(",")[1];
				byte[] decodedBytes = decoder.decodeBuffer(image);

				SessionScopeStorage dir = new SessionScopeStorage(sessionStoragePath);
				try {
					if (dir == null || !dir.isDirectory()) {
						dir.makeDirectories();
					}
					String fileName = accountContext.getUserCd();
					final SessionScopeStorage storage = new SessionScopeStorage(sessionStoragePath, fileName);
					os = storage.create();
					os.write(decodedBytes);
				} catch (final IOException e) {
					result.put("error", true);
					result.put("errorMessage", "上传失败");
					return result;
				} finally {
					try {
						if (os != null) {
							os.close();
						}
					} catch (final IOException e) {
						result.put("error", true);
						result.put("errorMessage", "上传失败");
						return result;
					}
				}

				// 进行剪裁
				InputStream is = null;
				SessionScopeStorage storage = new SessionScopeStorage(sessionStoragePath, sessionFileName);
				if (!storage.exists() || !storage.isFile()) {
					return null;
				}
				is = storage.open();

				ImageUtil imageUtil = new ImageUtil(is);
				// 压缩
				imageUtil.resizeImage(cropParam.getResizeX(), cropParam.getResizeY());

				// 输出路径（绝对路径）
				PublicStorage publicStorager = new PublicStorage(publicStoragePath);
				if (publicStorager == null || !publicStorager.isDirectory()) {
					publicStorager.makeDirectories();
				}
				String abOutImageFilePath = FileUtil.getPublicStorageRealPath() + "/" + publicStoragePath + "/"
						+ accountContext.getUserCd();
				imageUtil.toFile(abOutImageFilePath);

				// 将上传的Session的图片复制到PublicStorage
				// FileUtil.copySessionFile2Pubilc(sessionStoragePath,
				// sessionFileName, publicStoragePath, publicFileName);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			result.put("error", true);
			result.put("errorMessage", "头像上传失败！");
		}

		return result;
	}

	/**
	 * 下载头像
	 *
	 * @return 返回头像图片数据
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.GET)
	@RequestMapping(value = "get/{userCd}", method = RequestMethod.GET)
	public final String getAvatarByUserCd(final @PathVariable("userCd") String userCd) {
		try {
			logger.info("ndims/user/avatar.get");

			AccountContext accountContext = Contexts.get(AccountContext.class);
			String avatarUserCd = userCd;
			if (avatarUserCd == null || avatarUserCd.trim().length() == 0) {
				avatarUserCd = accountContext.getUserCd();
			}
			PublicStorage storage = new PublicStorage("user/avatar", avatarUserCd + ".png");
			if (!storage.exists() || !storage.isFile()) {
				storage = new PublicStorage("user/avatar/tmp/user.png");
				if (!storage.exists() || !storage.isFile()) {
					return null;
				}
			}
			InputStream istr = storage.open();
			try {
				response.setHeader("Content-Type", "image/png");
				BufferedImage bi = ImageIO.read(istr);
				ImageIO.write(bi, "png", response.getOutputStream());

			} finally {
				IOUtil.closeCloseableQuietly(istr);
			}
			return null;
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * 下载头像
	 *
	 * @return 返回头像图片数据
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.GET)
	@RequestMapping(value = "get", method = RequestMethod.GET)
	public final String getAvatar() {
		try {
			logger.info("ndims/user/avatar.get");

			AccountContext accountContext = Contexts.get(AccountContext.class);
			String avatarUserCd = accountContext.getUserCd();
			PublicStorage storage = new PublicStorage("user/avatar", avatarUserCd + ".png");
			if (!storage.exists() || !storage.isFile()) {
				storage = new PublicStorage("user/avatar/tmp/user.png");
				if (!storage.exists() || !storage.isFile()) {
					return null;
				}
			}
			InputStream istr = storage.open();
			try {
				response.setHeader("Content-Type", "image/png");
				BufferedImage bi = ImageIO.read(istr);
				ImageIO.write(bi, "png", response.getOutputStream());

			} finally {
				IOUtil.closeCloseableQuietly(istr);
			}
			return null;
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * 剪切头像
	 *
	 * @return 返回头像图片数据
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "crop", method = RequestMethod.POST)
	public @ResponseBody JSONObject userAvataCrop(@RequestBody final ImageCropParam cropParam) throws Exception {
		JSONObject result = new JSONObject();
		logger.info("ndims/user/avatar.crop");

		result.put("error", false);
		result.put("errorMessage", "");
		OutputStream os = null;
		InputStream is = null;
		ImageInputStream iis = null;
		try {
			AccountContext accountContext = Contexts.get(AccountContext.class);
			String sessionStoragePath = "user/avatar";
			String publicStoragePath = "user/avatar";
			String sessionFileName = accountContext.getUserCd();

			if (!cropParam.getIsCrop()) {
				// 不需要剪裁时，直接将上传的Session的图片复制到PublicStorage
				FileUtil.copySessionFile2Pubilc(sessionStoragePath, sessionFileName, publicStoragePath,
						accountContext.getUserCd() + ".png");
			} else {
				// 进行剪裁
				SessionScopeStorage storage = new SessionScopeStorage(sessionStoragePath, sessionFileName);
				if (!storage.exists() || !storage.isFile()) {
					return null;
				}
				is = storage.open();

				ImageUtil imageUtil = new ImageUtil(is, cropParam.getOrgWidth().intValue(), cropParam.getOrgHeight()
						.intValue());
				// 缩放
				imageUtil.scaleImage(cropParam.getScaleX(), cropParam.getScaleY());
				// 旋转
				imageUtil.rotateImage(cropParam.getRotate());
				// 剪裁
				imageUtil.regionImage(cropParam.getX(), cropParam.getY(), cropParam.getWidth(), cropParam.getHeight());

				// 输出路径（绝对路径）
				PublicStorage publicStorager = new PublicStorage(publicStoragePath);
				if (publicStorager == null || !publicStorager.isDirectory()) {
					publicStorager.makeDirectories();
				}
				String abOutImageFilePath = FileUtil.getPublicStorageRealPath() + "/" + publicStoragePath + "/"
						+ accountContext.getUserCd();
				imageUtil.toFile(abOutImageFilePath);

			}
		} catch (final Exception e) {
			e.printStackTrace();
			result.put("error", true);
			result.put("errorMessage", "头像剪裁失败！");
			return result;
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
		}
		return result;
	}
}
