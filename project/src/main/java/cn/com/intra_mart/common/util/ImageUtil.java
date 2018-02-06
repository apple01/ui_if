package cn.com.intra_mart.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class ImageUtil {

	// 正在处理的内存中的图片
	private Builder<? extends InputStream> imageInputStream;
	int orgImageWidth;
	int orgImageHeight;

	public ImageUtil(InputStream imageInputStream, int orgImageWidth, int orgImageHeight) {
		this.imageInputStream = Thumbnails.of(imageInputStream);
		this.imageInputStream.outputFormat("png");
		this.orgImageWidth = orgImageWidth;
		this.orgImageHeight = orgImageHeight;
	}

	public ImageUtil(InputStream imageInputStream) {
		this.imageInputStream = Thumbnails.of(imageInputStream);
		this.imageInputStream.outputFormat("png");
	}

	/**
	 * 获得处理完图像的OutputStream
	 *
	 */
	public OutputStream getOutputStream() {
		OutputStream os = null;
		try {
			imageInputStream.toOutputStream(os);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return os;

	}

	/**
	 * 获得处理完图像的OutputStream
	 *
	 */
	public void toFile(String path) {
		try {
			imageInputStream.toFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按指定大小把图片进行缩放（会遵循原图高宽比例）
	 *
	 * @param scaleX
	 * @param scaleY
	 */
	public void scaleImage(Float scaleX, Float scaleY) {
		int scaleWidth = orgImageWidth;
		int scaleHeight = orgImageHeight;
		if (scaleX != null && scaleX > 0) {
			scaleWidth = new Float(scaleWidth * scaleX.floatValue()).intValue();
		}
		if (scaleY != null && scaleY > 0) {
			scaleHeight = new Float(scaleHeight * scaleY.floatValue()).intValue();
		}

		imageInputStream = imageInputStream.size(scaleWidth, scaleHeight);
	}

	/**
	 * 按指定大小把图片进行压缩
	 *
	 * @param scaleX
	 * @param scaleY
	 */
	public void resizeImage(Integer x, Integer y) {
		if (x == null) {
			x = 200;
		}
		if (y == null) {
			y = 200;
		}
		imageInputStream = imageInputStream.size(x.intValue(), y.intValue());
	}

	/**
	 * 旋转
	 *
	 * @param degree
	 */
	public void rotateImage(Integer degree) {
		if (degree != null) {
			imageInputStream = imageInputStream.rotate(degree);
		}
	}

	/**
	 * 裁剪
	 *
	 * @param degree
	 */
	public void regionImage(Integer x, Integer y, Integer width, Integer height) {
		if (x == null || y == null || width == null || height == null) {
			return;
		}
		imageInputStream = imageInputStream.sourceRegion(x, y, width, height).keepAspectRatio(false);

	}

}
