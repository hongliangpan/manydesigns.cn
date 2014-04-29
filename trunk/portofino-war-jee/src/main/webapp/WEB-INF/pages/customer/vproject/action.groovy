import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.security.RequiresPermissions;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.FormElement;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.annotations.*;
import com.manydesigns.elements.blobs.Blob;
import com.manydesigns.elements.blobs.BlobManager;
import com.manydesigns.elements.fields.Field;
import com.manydesigns.elements.fields.FileBlobField;
import com.manydesigns.elements.fields.SelectField;
import com.manydesigns.elements.fields.TextField;
import com.manydesigns.elements.forms.FieldSet;
import com.manydesigns.elements.forms.*;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.elements.options.DefaultSelectionProvider;
import com.manydesigns.elements.options.DisplayMode;
import com.manydesigns.elements.options.SearchDisplayMode;
import com.manydesigns.elements.options.SelectionProvider;
import com.manydesigns.elements.reflection.ClassAccessor;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.servlet.MutableHttpServletRequest;
import com.manydesigns.elements.servlet.ServletUtils;
import com.manydesigns.elements.text.OgnlTextFormat;
import com.manydesigns.elements.util.MimeTypes;
import com.manydesigns.elements.util.Util;
import com.manydesigns.elements.xml.XhtmlBuffer;
import com.manydesigns.portofino.PortofinoProperties;
import com.manydesigns.portofino.buttons.GuardType;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.buttons.annotations.Buttons;
import com.manydesigns.portofino.buttons.annotations.Guard;
import com.manydesigns.portofino.dispatcher.PageInstance;
import com.manydesigns.portofino.pageactions.AbstractPageAction;
import com.manydesigns.portofino.pageactions.PageActionLogic;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudProperty;
import com.manydesigns.portofino.pageactions.crud.reflection.CrudAccessor;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.servlets.BlobCleanupListener;
import com.manydesigns.portofino.util.PkHelper;
import com.manydesigns.portofino.util.ShortNameUtils;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.util.UrlBuilder;
import ognl.OgnlContext;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*

import com.manydesigns.elements.messages.*
import com.manydesigns.elements.reflection.*
import com.manydesigns.portofino.*
import com.manydesigns.portofino.buttons.*
import com.manydesigns.portofino.buttons.annotations.*
import com.manydesigns.portofino.dispatcher.*
import com.manydesigns.portofino.model.database.*
import com.manydesigns.portofino.pageactions.*
import com.manydesigns.portofino.security.*
import com.manydesigns.portofino.shiro.*

import net.sourceforge.stripes.action.*
import org.apache.commons.lang.StringUtils
import org.apache.shiro.*
import org.hibernate.*
import org.hibernate.criterion.*

import com.manydesigns.portofino.pageactions.crud.*
import javax.servlet.*

import com.manydesigns.elements.messages.*
import com.manydesigns.elements.reflection.*
import com.manydesigns.portofino.*
import com.manydesigns.portofino.buttons.*
import com.manydesigns.portofino.buttons.annotations.*
import com.manydesigns.portofino.dispatcher.*
import com.manydesigns.portofino.model.database.*
import com.manydesigns.portofino.pageactions.*
import com.manydesigns.portofino.security.*
import com.manydesigns.portofino.shiro.*

import net.sourceforge.stripes.action.*
import org.apache.commons.lang.StringUtils
import org.apache.shiro.*
import org.hibernate.*
import org.hibernate.criterion.*

import com.manydesigns.portofino.pageactions.crud.*

@SupportsPermissions([ CrudAction.PERMISSION_CREATE, CrudAction.PERMISSION_EDIT, CrudAction.PERMISSION_DELETE ])
@RequiresPermissions(level = AccessLevel.VIEW)
class MyCrudAction extends CrudAction4ItsProject {

    //Automatically generated on Wed Feb 19 17:24:21 CST 2014 by ManyDesigns Portofino
    //Write your code here

    //**************************************************************************
    // Extension hooks
    //**************************************************************************
	public Resolution create() {
		setupForm(Mode.CREATE);
		object =  classAccessor.newInstance();
		createSetup(object);
		form.readFromObject(object);

		return getCreateView();
	}
	public Resolution bulkDelete() {

		return new RedirectResolution(appendSearchStringParamIfNecessary(context.getActionPath()));
	}
	public Resolution bulkEdit() {
		return getBulkEditView();
	}

	public Resolution bulkUpdate() {
			return getBulkEditView();
	}
	public Resolution edit() {
		setupForm(Mode.EDIT);
		editSetup(object);
		form.readFromObject(object);
		return getEditView();
	}
	public Resolution delete() {
		String url = calculateBaseSearchUrl();
		return new RedirectResolution(appendSearchStringParamIfNecessary(url), false);
	}
	
    protected void createSetup(Object object) {}

    protected boolean createValidate(Object object) {
        return true;
    }

    protected void createPostProcess(Object object) {}


    protected void editSetup(Object object) {}

    protected boolean editValidate(Object object) {
        return true;
    }

    protected void editPostProcess(Object object) {}


    protected boolean deleteValidate(Object object) {
        return true;
    }

    protected void deletePostProcess(Object object) {}


    protected Resolution getBulkEditView() {
        return super.getBulkEditView();
    }

    protected Resolution getCreateView() {
        return super.getCreateView();
    }

    protected Resolution getEditView() {
        return super.getEditView();
    }

    protected Resolution getReadView() {
        return super.getReadView();
    }

    protected Resolution getSearchView() {
        return super.getSearchView();
    }

    protected Resolution getEmbeddedSearchView() {
        return super.getEmbeddedSearchView();
    }

    protected Resolution getSearchResultsPageView() {
        return super.getSearchResultsPageView()
    }

}