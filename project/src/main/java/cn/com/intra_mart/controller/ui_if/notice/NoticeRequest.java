package cn.com.intra_mart.controller.ui_if.notice;

import java.util.List;

import jp.co.intra_mart.system.notice.entity.ImntcHistory;

public class NoticeRequest {
	private int start;
	private int length;
	private List<String> noticeIds;
	private List<ImntcHistory> imntcHistorys;

	/**
	 * start取得
	 *
	 * @return start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * start设定
	 *
	 * @param start
	 *            start
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * length取得
	 *
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * length设定
	 *
	 * @param length
	 *            length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * noticeIds取得
	 *
	 * @return noticeIds
	 */
	public List<String> getNoticeIds() {
		return noticeIds;
	}

	/**
	 * noticeIds设定
	 *
	 * @param noticeIds
	 *            noticeIds
	 */
	public void setNoticeIds(List<String> noticeIds) {
		this.noticeIds = noticeIds;
	}

	/**
	 * imntcHistorys取得
	 *
	 * @return imntcHistorys
	 */
	public List<ImntcHistory> getImntcHistorys() {
		return imntcHistorys;
	}

	/**
	 * imntcHistorys设定
	 *
	 * @param imntcHistorys
	 *            imntcHistorys
	 */
	public void setImntcHistorys(List<ImntcHistory> imntcHistorys) {
		this.imntcHistorys = imntcHistorys;
	}

}
