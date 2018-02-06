package cn.com.intra_mart.controller.ui_if.user.model;

public class ImageCropParam {
	private Integer orgWidth;
	private Integer orgHeight;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	private Integer rotate;
	private Float scaleX;
	private Float scaleY;
	private Integer resizeX;
	private Integer resizeY;
	Boolean isCrop = false;
	private String imgContent;
	private String imgFileName;

	public String getImgContent() {
		return imgContent;
	}

	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getRotate() {
		return rotate;
	}

	public void setRotate(Integer rotate) {
		this.rotate = rotate;
	}

	public Float getScaleX() {
		return scaleX;
	}

	public void setScaleX(Float scaleX) {
		this.scaleX = scaleX;
	}

	public Float getScaleY() {
		return scaleY;
	}

	public void setScaleY(Float scaleY) {
		this.scaleY = scaleY;
	}

	public Boolean getIsCrop() {
		return isCrop;
	}

	public void setIsCrop(Boolean isCrop) {
		this.isCrop = isCrop;
	}

	public Integer getOrgWidth() {
		return orgWidth;
	}

	public void setOrgWidth(Integer orgWidth) {
		this.orgWidth = orgWidth;
	}

	public Integer getOrgHeight() {
		return orgHeight;
	}

	public void setOrgHeight(Integer orgHeight) {
		this.orgHeight = orgHeight;
	}

	public Integer getResizeX() {
		return resizeX;
	}

	public void setResizeX(Integer resizeX) {
		this.resizeX = resizeX;
	}

	public Integer getResizeY() {
		return resizeY;
	}

	public void setResizeY(Integer resizeY) {
		this.resizeY = resizeY;
	}

}
