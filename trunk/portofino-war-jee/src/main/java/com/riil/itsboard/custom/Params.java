package com.riil.itsboard.custom;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class Params {
	private String contextPath;
	private String statX;
	private String statY;
	private String region = "";
	private String regions;
	private List<String> regionsCheckbox;
	private String pageId;
	private SortType sortType;

	public Params(String statX, String statY, String region, String pageId) {
		super();
		this.statX = statX;
		this.statY = statY;
		this.region = region;
		this.pageId = pageId;
	}

	/**
	 * @return statX - {return content description}
	 */
	public String getStatX() {
		return statX;
	}

	/**
	 * @return statX - {return content description}
	 */
	public String getStatXName(String[][] statMenus) {
		for (String[] func : statMenus) {
			if (func[0].equals(statX)) {
				return func[1];
			}
		}
		return "";
	}

	/**
	 * @param statX - {parameter description}.
	 */
	public void setStatX(String statX) {
		this.statX = statX;
	}

	/**
	 * @return statY - {return content description}
	 */
	public String getStatY() {
		return statY;
	}

	/**
	 * @return statX - {return content description}
	 */
	public String getStatYName(String[][] statMenus) {
		for (String[] func : statMenus) {
			if (func[0].equals(statY)) {
				return func[1];
			}
		}
		return "";
	}

	/**
	 * @param statY - {parameter description}.
	 */
	public void setStatY(String statY) {
		this.statY = statY;
	}

	/**
	 * @return region - {return content description}
	 */
	public String getRegion() {
		return region;
	}

	public String getRegionDisplay() {
		if (StringUtils.isBlank(regions) || regions.equals("null")) {
			return region;
		} else {
			return regions;
		}
	}

	/**
	 * @param region - {parameter description}.
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return pageId - {return content description}
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId - {parameter description}.
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return pageUrl - {return content description}
	 */
	public String getPageUrl() {
		return contextPath + "/" + pageId + "?pageId=" + pageId + "&statx=" + statX + "&staty=" + statY + "&sortType="
				+ sortType + "&regions=" + regions + "&region=" + region;
	}

	/**
	 * @return pageUrl - {return content description}
	 */
	public String getPageUrlSingleRegion() {
		return contextPath + "/" + pageId + "?pageId=" + pageId + "&statx=" + statX + "&staty=" + statY + "&sortType="
				+ sortType + "&region=";
	}

	/**
	 * @return sortType - {return content description}
	 */
	public SortType getSortType() {
		return sortType;
	}

	/**
	 * @param sortType - {parameter description}.
	 */
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	/**
	 * @return regions - {return content description}
	 */
	public String getRegions() {
		return regions;
	}

	/**
	 * @param regions - {parameter description}.
	 */
	public void setRegions(String regions) {
		this.regions = regions;
	}

	/**
	 * @return regionsCheckbox - {return content description}
	 */
	public List<String> getRegionsCheckbox() {
		return regionsCheckbox;
	}

	/**
	 * @param regionsCheckbox - {parameter description}.
	 */
	public void setRegionsCheckbox(List<String> regionsCheckbox) {
		this.regionsCheckbox = regionsCheckbox;
	}

	/**
	 * @return contextPath - {return content description}
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath - {parameter description}.
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public boolean isIncludeRegion(String region) {
		if (StringUtils.isBlank(getRegionDisplay())) {
			return false;
		}
		if (getRegionDisplay().indexOf(region) >= 0) {
			return true;
		}
		return false;
	}

	public String checked(String region) {
		if (isIncludeRegion(region)) {
			return "checked";
		} else {
			return "";
		}
	}

	public static Params getParams(HttpServletRequest request, String defaultPageId) {

		String contextPath = request.getServletContext().getContextPath();
		String statX = request.getParameter("statx");
		if (statX == null || statX.length() == 0) {
			statX = "c_state";
		}
		String statY = request.getParameter("staty");
		if (statY == null || statY.length() == 0) {
			statY = "c_industry1";
		}
		String region = request.getParameter("region");

		if (region == null || region.length() == 0) {
			region = "全国";
		}
		String regionsParam = request.getParameter("regions");

		String pageId = request.getParameter("pageId");
		if (pageId == null || pageId.length() == 0) {
			pageId = defaultPageId;
		}

		String sortType = request.getParameter("sortType");
		if (sortType == null || sortType.length() == 0) {
			sortType = SortType.DESC.getId();
		}

		String[] picked = request.getParameterValues("regionsCheckbox");
		List<String> regionsCheckbox = new ArrayList<String>();
		if (null != picked) {
			for (String t_string : picked) {
				System.out.println("" + t_string);
				regionsCheckbox.add(t_string);
			}
		}
		if (regionsCheckbox.size() > 0) {
			regionsParam = getRegionsString(regionsCheckbox);
		}
		if (regionsCheckbox.size() == 1) {
			region = regionsParam;
		}
		Params params = new Params(statX, statY, region, pageId);
		params.setContextPath(contextPath);
		params.setPageId(pageId);
		params.setSortType(SortType.parse(sortType));
		if (regionsParam != null && regionsParam.equals("null")) {
			regionsParam = null;
		}
		params.setRegions(regionsParam);
		params.setRegionsCheckbox(regionsCheckbox);
		return params;
	}

	/**
	 * @return regions
	 */
	public static String getRegionsString(List<String> regions) {
		StringBuilder t_temp = new StringBuilder();
		for (String region : regions) {
			t_temp.append(region).append(",");
		}
		if (t_temp.toString().lastIndexOf(",") >= 0) {
			return t_temp.toString().substring(0, t_temp.toString().length() - 1);
		}
		return t_temp.toString();
	}

}
