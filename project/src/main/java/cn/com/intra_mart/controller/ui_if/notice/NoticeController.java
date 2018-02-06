package cn.com.intra_mart.controller.ui_if.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.system.notice.NoticeHistoryManager;
import jp.co.intra_mart.system.notice.dao.ImntcHistoryDAO.NoticeHistoryResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通知控制层(Controller)<br>
 *
 * @author intra-mart shanghai
 *
 */
@Controller
@CrossOrigin(allowCredentials = "true")
@RequestMapping("ndims/ui_if/notice")
public class NoticeController {

	/**
	 * 登录通知历史
	 *
	 * @param noticeRequest
	 *            通知请求
	 * @return 登录通知件数
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "insertNoticeHistories", method = RequestMethod.POST)
	public @ResponseBody final Map<String, String> insertNoticeHistories(@RequestBody final NoticeRequest noticeRequest)
			throws Exception {

		int count = 0;
		try {
			if (noticeRequest != null && noticeRequest.getImntcHistorys() != null
					&& noticeRequest.getImntcHistorys().size() > 0) {
				count = NoticeHistoryManager.insertNoticeHistories(noticeRequest.getImntcHistorys());

			}

		} catch (Exception e) {
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("count", String.valueOf(count));
		return result;
	}

	/**
	 * 获得未读通知件数
	 *
	 * @return 未读通知件数
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "countUnreadNoticeHistory", method = RequestMethod.POST)
	public @ResponseBody final Map<String, String> countUnreadNoticeHistory() throws Exception {
		AccountContext accountContext = Contexts.get(AccountContext.class);
		int count = 0;
		try {
			count = NoticeHistoryManager.countUnreadNoticeHistory(accountContext.getUserCd());

		} catch (Exception e) {
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("count", String.valueOf(count));
		return result;
	}

	/**
	 * 获得通知历史
	 *
	 * @param noticeRequest
	 *            通知请求
	 * @return 通知历史
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "getNoticeHistoryResult", method = RequestMethod.POST)
	public @ResponseBody final List<NoticeHistoryResult> getNoticeHistoryResult(
			@RequestBody final NoticeRequest noticeRequest) throws Exception {
		AccountContext accountContext = Contexts.get(AccountContext.class);
		List<NoticeHistoryResult> listNoticeHistoryResult = new ArrayList<NoticeHistoryResult>();
		try {
			listNoticeHistoryResult = NoticeHistoryManager.getNoticeHistoryResult(accountContext.getUserCd(),
					noticeRequest.getStart(), noticeRequest.getLength());

		} catch (Exception e) {
		}
		return listNoticeHistoryResult;
	}

	/**
	 * 删除通知历史
	 *
	 * @param noticeRequest
	 *            通知请求
	 * @return 删除通知历史件数
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "deleteNoticeHistory", method = RequestMethod.POST)
	public @ResponseBody final Map<String, String> deleteNoticeHistory(@RequestBody final NoticeRequest noticeRequest)
			throws Exception {
		AccountContext accountContext = Contexts.get(AccountContext.class);
		int count = 0;
		try {
			if (noticeRequest.getNoticeIds() != null && noticeRequest.getNoticeIds().size() > 0) {
				count = NoticeHistoryManager.deleteNoticeHistory(noticeRequest.getNoticeIds(),
						accountContext.getUserCd());
			}

		} catch (Exception e) {
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("count", String.valueOf(count));
		return result;
	}

	/**
	 * 更新通知历史为已读
	 *
	 * @param noticeRequest
	 *            通知请求
	 * @return 更新通知历史件数
	 * @throws Exception
	 */
	@CrossOrigin(allowCredentials = "true", origins = "*", methods = RequestMethod.POST)
	@RequestMapping(value = "updateNoticeHasReadFlag", method = RequestMethod.POST)
	public @ResponseBody final Map<String, String> updateNoticeHasReadFlag(
			@RequestBody final NoticeRequest noticeRequest) throws Exception {
		AccountContext accountContext = Contexts.get(AccountContext.class);
		int count = 0;
		try {
			if (noticeRequest.getNoticeIds() != null && noticeRequest.getNoticeIds().size() > 0) {
				count = NoticeHistoryManager.updateNoticeHasReadFlag(noticeRequest.getNoticeIds(),
						accountContext.getUserCd(), "1");
			}

		} catch (Exception e) {
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("count", String.valueOf(count));
		return result;
	}
}
