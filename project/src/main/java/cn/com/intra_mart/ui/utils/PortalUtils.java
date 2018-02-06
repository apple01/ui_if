package cn.com.intra_mart.ui.utils;

import java.util.ArrayList;
import java.util.List;

import jp.co.intra_mart.foundation.portal.common.model.PortalDisplaySetModel;

import com.sun.portal.container.PortletType;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryContext;
import com.sun.portal.portletcontainer.context.registry.PortletRegistryException;
import com.sun.portal.portletcontainer.driver.DriverUtil;

public class PortalUtils {

	/**
	 * 获得Portal信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<PortalDisplaySetModel> getPortalInfo() {

		List<PortalDisplaySetModel> portalInfos = new ArrayList<PortalDisplaySetModel>();
		try {
			final PortletRegistryContext context = DriverUtil
					.getPortletRegistryContext();
			portalInfos = context.getVisiblePortletWindows(PortletType.ALL);

		} catch (PortletRegistryException e) {
			e.printStackTrace();
		}
		return portalInfos;
	}
}
