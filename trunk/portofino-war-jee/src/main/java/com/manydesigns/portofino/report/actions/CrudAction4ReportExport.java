package com.manydesigns.portofino.report.actions;

import java.io.File;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.annotations.CssClass;
import com.manydesigns.elements.annotations.Label;
import com.manydesigns.elements.annotations.Required;
import com.manydesigns.elements.forms.Form;
import com.manydesigns.elements.forms.FormBuilder;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.elements.options.SelectionProvider;
import com.manydesigns.elements.util.BootstrapSizes;
import com.manydesigns.portofino.PortofinoProperties;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.di.Inject;
import com.manydesigns.portofino.dispatcher.DispatcherLogic;
import com.manydesigns.portofino.modules.BaseModule;
import com.manydesigns.portofino.modules.PageactionsModule;
import com.manydesigns.portofino.report.utils.SqlUtils;
import com.manydesigns.portofino.stripes.AbstractActionBean;

/**
 * <br>
 * <p>
 * Create on : 2014-3-6<br>
 * <p>
 * </p>
 * <br>
 * 
 * @author panhongliang<br>
 * @version portofino-war-jee v1.0
 *          <p>
 *          <br>
 *          <strong>Modify History:</strong><br>
 *          user modify_date modify_content<br>
 *          -------------------------------------------<br>
 *          <br>
 */
@UrlBinding(CrudAction4ReportExport.URL_BINDING)
public class CrudAction4ReportExport extends AbstractActionBean {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	public static final String URL_BINDING = "/actions/report/export";

	@Inject(BaseModule.PORTOFINO_CONFIGURATION)
	public Configuration configuration;

	@Inject(PageactionsModule.PAGES_DIRECTORY)
	public File pagesDir;

	Form form;

	// --------------------------------------------------------------------------
	// Logging
	// --------------------------------------------------------------------------

	public final static Logger logger = LoggerFactory.getLogger(CrudAction4ReportExport.class);

	// --------------------------------------------------------------------------
	// Action events
	// --------------------------------------------------------------------------

	@DefaultHandler
	public Resolution execute() {
		SqlUtils.main(null);
		setupFormAndBean();
		return new ForwardResolution("/m/pageactions/actions/admin/settings.jsp");
	}

	private void setupFormAndBean() {
		SelectionProvider pagesSelectionProvider = DispatcherLogic.createPagesSelectionProvider(pagesDir);

		Settings settings = new Settings();
		settings.appName = configuration.getString(PortofinoProperties.APP_NAME);
		settings.landingPage = configuration.getString(PortofinoProperties.LANDING_PAGE);
		settings.loginPage = configuration.getString(PortofinoProperties.LOGIN_PAGE);
		settings.preloadGroovyPages = configuration.getBoolean(PortofinoProperties.GROOVY_PRELOAD_PAGES, true);
		settings.preloadGroovyClasses = configuration.getBoolean(PortofinoProperties.GROOVY_PRELOAD_CLASSES, true);

		form = new FormBuilder(Settings.class).configSelectionProvider(pagesSelectionProvider, "landingPage")
				.configSelectionProvider(pagesSelectionProvider, "loginPage").build();

		form.readFromObject(settings);
	}

	@Button(list = "settings", key = "update", order = 1, type = Button.TYPE_PRIMARY)
	public Resolution update() {
		setupFormAndBean();
		form.readFromRequest(context.getRequest());
		if (form.validate()) {
			logger.debug("Applying settings to model");
			try {
				FileConfiguration fileConfiguration = (FileConfiguration) configuration;
				Settings settings = new Settings();
				form.writeToObject(settings);
				fileConfiguration.setProperty(PortofinoProperties.APP_NAME, settings.appName);
				fileConfiguration.setProperty(PortofinoProperties.LANDING_PAGE, settings.landingPage);
				fileConfiguration.setProperty(PortofinoProperties.LOGIN_PAGE, settings.loginPage);
				if (!settings.preloadGroovyPages
						|| fileConfiguration.getProperty(PortofinoProperties.GROOVY_PRELOAD_PAGES) != null) {
					fileConfiguration
							.setProperty(PortofinoProperties.GROOVY_PRELOAD_PAGES, settings.preloadGroovyPages);
				}
				if (!settings.preloadGroovyClasses
						|| fileConfiguration.getProperty(PortofinoProperties.GROOVY_PRELOAD_CLASSES) != null) {
					fileConfiguration.setProperty(PortofinoProperties.GROOVY_PRELOAD_CLASSES,
							settings.preloadGroovyClasses);
				}
				fileConfiguration.save();
				logger.info("Configuration saved to " + fileConfiguration.getFile().getAbsolutePath());
			} catch (Exception e) {
				logger.error("Configuration not saved", e);
				SessionMessages.addErrorMessage(ElementsThreadLocals.getText("the.configuration.could.not.be.saved"));
				return new ForwardResolution("/m/pageactions/actions/admin/settings.jsp");
			}
			SessionMessages.addInfoMessage(ElementsThreadLocals.getText("configuration.updated.successfully"));
			return new RedirectResolution(this.getClass());
		} else {
			return new ForwardResolution("/m/pageactions/actions/admin/settings.jsp");
		}
	}

	@Button(list = "settings", key = "return.to.pages", order = 2)
	public Resolution returnToPages() {
		return new RedirectResolution("/");
	}

	// --------------------------------------------------------------------------
	// Getters/setters
	// --------------------------------------------------------------------------

	public Form getForm() {
		return form;
	}

	// --------------------------------------------------------------------------
	// Form
	// --------------------------------------------------------------------------

	public static class Settings {

		@Required
		@Label("Application name")
		@CssClass(BootstrapSizes.BLOCK_LEVEL)
		public String appName;
		@Required
		public String landingPage;
		@Required
		public String loginPage;
		@Required
		@Label("Preload Groovy pages at startup")
		public boolean preloadGroovyPages;
		@Required
		@Label("Preload Groovy shared classes at startup")
		public boolean preloadGroovyClasses;

	}

}
