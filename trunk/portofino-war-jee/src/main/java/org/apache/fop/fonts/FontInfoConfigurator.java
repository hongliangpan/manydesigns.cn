/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: FontInfoConfigurator.java 1357883 2012-07-05 20:29:53Z gadams $ */

package org.apache.fop.fonts;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOPException;
import org.apache.fop.fonts.autodetect.FontFileFinder;
import org.apache.fop.fonts.autodetect.FontInfoFinder;
import org.apache.fop.util.LogUtil;

import com.manydesigns.portofino.pageactions.crud.CrudAction4ItsProject;

/**
 * An abstract FontInfo configurator
 */
public class FontInfoConfigurator {
	/** logger instance */
	protected static final Log log = LogFactory.getLog(FontInfoConfigurator.class);

	private final Configuration cfg;
	private final FontManager fontManager;
	private final FontResolver fontResolver;
	private final FontEventListener listener;
	private final boolean strict;

	/**
	 * Main constructor
	 * 
	 * @param cfg the configuration object
	 * @param fontManager the font manager
	 * @param fontResolver the font resolver
	 * @param listener the font event listener
	 * @param strict true if an Exception should be thrown if an error is found.
	 */
	public FontInfoConfigurator(Configuration cfg, FontManager fontManager, FontResolver fontResolver,
			FontEventListener listener, boolean strict) {
		this.cfg = cfg;
		this.fontManager = fontManager;
		this.fontResolver = fontResolver;
		this.listener = listener;
		this.strict = strict;
	}

	/**
	 * Initializes font info settings from the user configuration
	 * 
	 * @param fontInfoList a font info list
	 * @throws FOPException if an exception occurs while processing the configuration
	 */
	public void configure(List<EmbedFontInfo> fontInfoList) throws FOPException {
		Configuration fontsCfg = cfg.getChild("fonts", false);
		if (fontsCfg != null) {
			long start = 0;
			if (log.isDebugEnabled()) {
				log.debug("Starting font configuration...");
				start = System.currentTimeMillis();
			}

			FontAdder fontAdder = new FontAdder(fontManager, fontResolver, listener);

			// native o/s search (autodetect) configuration
			boolean autodetectFonts = (fontsCfg.getChild("auto-detect", false) != null);
			if (autodetectFonts) {
				FontDetector fontDetector = new FontDetector(fontManager, fontAdder, strict, listener);
				fontDetector.detect(fontInfoList);
			}

			// Add configured directories to FontInfo list
			addDirectories(fontsCfg, fontAdder, fontInfoList);

			// Add fonts from configuration to FontInfo list
			addFonts(fontsCfg, fontManager.getFontCache(), fontInfoList);

			// Update referenced fonts (fonts which are not to be embedded)
			fontManager.updateReferencedFonts(fontInfoList);

			// Renderer-specific referenced fonts
			Configuration referencedFontsCfg = fontsCfg.getChild("referenced-fonts", false);
			if (referencedFontsCfg != null) {
				FontTriplet.Matcher matcher = FontManagerConfigurator.createFontsMatcher(referencedFontsCfg, strict);
				fontManager.updateReferencedFonts(fontInfoList, matcher);
			}

			// Update font cache if it has changed
			fontManager.saveCache();

			if (log.isDebugEnabled()) {
				log.debug("Finished font configuration in " + (System.currentTimeMillis() - start) + "ms");
			}
		}
	}

	private void addDirectories(Configuration fontsCfg, FontAdder fontAdder, List<EmbedFontInfo> fontInfoList)
			throws FOPException {
		// directory (multiple font) configuration
		Configuration[] directories = fontsCfg.getChildren("directory");
		for (int i = 0; i < directories.length; i++) {
			boolean recursive = directories[i].getAttributeAsBoolean("recursive", false);
			String directory = null;
			try {
				directory = directories[i].getValue();
			} catch (ConfigurationException e) {
				LogUtil.handleException(log, e, strict);
				continue;
			}
			if (directory == null) {
				LogUtil.handleException(log, new FOPException("directory defined without value"), strict);
				continue;
			}

			// add fonts found in directory
			FontFileFinder fontFileFinder = new FontFileFinder(recursive ? -1 : 1, listener);
			List<URL> fontURLList;
			try {
				fontURLList = fontFileFinder.find(directory);
				fontAdder.add(fontURLList, fontInfoList);
			} catch (IOException e) {
				LogUtil.handleException(log, e, strict);
			}
		}
	}

	/**
	 * Populates the font info list from the fonts configuration
	 * 
	 * @param fontsCfg a fonts configuration
	 * @param fontCache a font cache
	 * @param fontInfoList a font info list
	 * @throws FOPException if an exception occurs while processing the configuration
	 */
	protected void addFonts(Configuration fontsCfg, FontCache fontCache, List<EmbedFontInfo> fontInfoList)
			throws FOPException {
		// font file (singular) configuration
		Configuration[] font = fontsCfg.getChildren("font");
		for (int i = 0; i < font.length; i++) {
			EmbedFontInfo embedFontInfo = getFontInfo(font[i], fontCache);
			if (embedFontInfo != null) {
				fontInfoList.add(embedFontInfo);
			}
		}
	}

	private static void closeSource(Source src) {
		if (src instanceof StreamSource) {
			StreamSource streamSource = (StreamSource) src;
			IOUtils.closeQuietly(streamSource.getInputStream());
			IOUtils.closeQuietly(streamSource.getReader());
		}
	}

	/**
	 * Returns a font info from a font node Configuration definition
	 * 
	 * @param fontCfg Configuration object (font node)
	 * @param fontCache the font cache (or null if it is disabled)
	 * @return the embedded font info
	 * @throws FOPException if something's wrong with the config data
	 */
	protected EmbedFontInfo getFontInfo(Configuration fontCfg, FontCache fontCache) throws FOPException {
		String metricsUrl = fontCfg.getAttribute("metrics-url", null);
		String embedUrl = fontCfg.getAttribute("embed-url", null);
		String subFont = fontCfg.getAttribute("sub-font", null);

		if (metricsUrl == null && embedUrl == null) {
			LogUtil.handleError(log, "Font configuration without metric-url or embed-url attribute", strict);
			return null;
		}
		if (strict) {
			// This section just checks early whether the URIs can be resolved
			// Stream are immediately closed again since they will never be used anyway
			if (embedUrl != null) {
				// hongliangpan add path config
				String fopConfig = "fop.xml";
				String filePath = CrudAction4ItsProject.class.getClassLoader().getResource(fopConfig).getPath();
				Source source = fontResolver.resolve(new File(filePath).getParent() + "/" + embedUrl);
				// fontResolver.resolve(embedUrl);
				closeSource(source);
				if (source == null) {
					LogUtil.handleError(log, "Failed to resolve font with embed-url '" + embedUrl + "'", strict);
					return null;
				}
			}
			if (metricsUrl != null) {
				String fopConfig = "fop.xml";
				String filePath = CrudAction4ItsProject.class.getClassLoader().getResource(fopConfig).getPath();
				Source source = fontResolver.resolve(new File(filePath).getParent() + "/" + metricsUrl);
				// Source source = fontResolver.resolve(metricsUrl);
				closeSource(source);
				if (source == null) {
					LogUtil.handleError(log, "Failed to resolve font with metric-url '" + metricsUrl + "'", strict);
					return null;
				}
			}
		}

		Configuration[] tripletCfg = fontCfg.getChildren("font-triplet");

		// no font triplet info
		if (tripletCfg.length == 0) {
			LogUtil.handleError(log, "font without font-triplet", strict);

			File fontFile = FontCache.getFileFromUrls(new String[] { embedUrl, metricsUrl });
			URL fontURL = null;
			try {
				fontURL = fontFile.toURI().toURL();
			} catch (MalformedURLException e) {
				LogUtil.handleException(log, e, strict);
			}
			if (fontFile != null) {
				FontInfoFinder finder = new FontInfoFinder();
				finder.setEventListener(listener);
				EmbedFontInfo[] infos = finder.find(fontURL, fontResolver, fontCache);
				return infos[0]; // When subFont is set, only one font is returned
			} else {
				return null;
			}
		}

		List<FontTriplet> tripletList = new java.util.ArrayList<FontTriplet>();
		for (int j = 0; j < tripletCfg.length; j++) {
			FontTriplet fontTriplet = getFontTriplet(tripletCfg[j]);
			tripletList.add(fontTriplet);
		}

		boolean useKerning = fontCfg.getAttributeAsBoolean("kerning", true);
		boolean useAdvanced = fontCfg.getAttributeAsBoolean("advanced", true);
		EncodingMode encodingMode = EncodingMode.getValue(fontCfg.getAttribute("encoding-mode",
				EncodingMode.AUTO.getName()));
		EmbeddingMode embeddingMode = EmbeddingMode.getValue(fontCfg.getAttribute("embedding-mode",
				EmbeddingMode.AUTO.toString()));
		EmbedFontInfo embedFontInfo = new EmbedFontInfo(metricsUrl, useKerning, useAdvanced, tripletList, embedUrl,
				subFont);
		embedFontInfo.setEncodingMode(encodingMode);
		embedFontInfo.setEmbeddingMode(embeddingMode);

		boolean skipCachedFont = false;
		if (fontCache != null) {
			if (!fontCache.containsFont(embedFontInfo)) {
				fontCache.addFont(embedFontInfo);
			} else {
				skipCachedFont = true;
			}
		}

		if (log.isDebugEnabled()) {
			String embedFile = embedFontInfo.getEmbedFile();
			log.debug((skipCachedFont ? "Skipping (cached) font " : "Adding font ")
					+ (embedFile != null ? embedFile + ", " : "") + "metric file " + embedFontInfo.getMetricsFile());
			for (int j = 0; j < tripletList.size(); ++j) {
				FontTriplet triplet = tripletList.get(j);
				log.debug("  Font triplet " + triplet.getName() + ", " + triplet.getStyle() + ", "
						+ triplet.getWeight());
			}
		}
		return embedFontInfo;
	}

	/**
	 * Creates a new FontTriplet given a triple Configuration
	 * 
	 * @param tripletCfg a triplet configuration
	 * @return a font triplet font key
	 * @throws FOPException thrown if a FOP exception occurs
	 */
	private FontTriplet getFontTriplet(Configuration tripletCfg) throws FOPException {
		try {
			String name = tripletCfg.getAttribute("name");
			if (name == null) {
				LogUtil.handleError(log, "font-triplet without name", strict);
				return null;
			}

			String weightStr = tripletCfg.getAttribute("weight");
			if (weightStr == null) {
				LogUtil.handleError(log, "font-triplet without weight", strict);
				return null;
			}
			int weight = FontUtil.parseCSS2FontWeight(FontUtil.stripWhiteSpace(weightStr));

			String style = tripletCfg.getAttribute("style");
			if (style == null) {
				LogUtil.handleError(log, "font-triplet without style", strict);
				return null;
			} else {
				style = FontUtil.stripWhiteSpace(style);
			}
			return FontInfo.createFontKey(name, style, weight);
		} catch (ConfigurationException e) {
			LogUtil.handleException(log, e, strict);
		}
		return null;
	}

}
