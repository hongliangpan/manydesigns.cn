//Ex elements.js
function updateSelectOptions(relName, selectionProviderIndex, methodName) {
    var selectFieldId = arguments[3 + selectionProviderIndex];

    var data = {
        relName : relName,
        selectionProviderIndex : selectionProviderIndex
    };
    data[methodName] = '';
    for (var i = 3; i < arguments.length; i++ ) {
        var currentId = arguments[i];
        var current = $(currentId);
        data[current.attr('name')] = current.val();
    }

    var postUrl = stripQueryString(location.href);
    jQuery.ajax({
        type: 'POST',
        url: postUrl,
        data: data,
        success: function(responseData) {
            var options = responseData;
            if('string' === typeof(options)) {
                options = jQuery.parseJSON(options);
            }

            var selectField = $(selectFieldId);
            selectField.empty();

            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                var y = document.createElement('option');
                y.value = option['v'];
                y.text = option['l'];
                y.selected = option['s'];

                selectField.append(y)
            }
            selectField.change();
        }
    });
}

function setupAutocomplete(autocompleteId, relName, selectionProviderIndex, methodName) {
    var setupArguments = arguments;
    var selectFieldId = setupArguments[4 + selectionProviderIndex];
    var autocompleteObj = $(autocompleteId);
    var selectField = $(selectFieldId);
    autocompleteObj.typeahead({
        source: function( request, response ) {
            var data = {
                relName : relName,
                selectionProviderIndex : selectionProviderIndex,
                labelSearch : request
            };
            data[methodName] = '';
            for (var i = 4; i < setupArguments.length; i++ ) {
                var currentId = setupArguments[i];
                var current = $(currentId);
                data[current.attr('name')] = current.attr('value');
            }

            var postUrl = stripQueryString(location.href);
            selectField.val(""); //Reset selected object when user types
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: postUrl,
                data: data,
                success: function( responseData ) {
                    response( $.map( responseData, function( item ) {
                        var obj = {
                            label: item.l,
                            value: item.l,
                            optionValue: item.v
                        };
                        obj.toString = function() {
                            return JSON.stringify(obj);
                        };
                        return obj;
                    }));
                },
                error: function(request, textStatus) {
                    alert(textStatus);
                }
            });
        },
        minLength: 1,
        matcher: function(item) {
            return true;
        },
        sorter: function(items) {
            return items;
        },
        highlighter: function (item) {
            var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
            return item.label.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
                return '<strong>' + match + '</strong>'
            });
        },
        updater: function(item) {
            if(item) {
                item = JSON.parse(item);
                selectField.val(item.optionValue);
                selectField.data("itemLabel", item.label)
                return item.label;
            } else {
                selectField.data("itemLabel", "");
                selectField.val("");
                return "";
            }
        }
    });
}

function setupDatePicker(dateFieldId, dateFormat) {
    var dateField = $(dateFieldId);
    dateField.datepicker({ format: dateFormat, autoclose: true });
}

function stripQueryString(url) {
    var regexp = new RegExp("\\?.*");
    return url.replace(regexp, "");
}

function setupRichTextEditors() {
    $('textarea.mde-form-rich-text').each(function(index, element) {
        element = $(element);
        var conf = element.data('mdeRichTextConfig') || {
            toolbar: 'Full',
            toolbarCanCollapse: false
        };
        if(element.ckeditor) {
            element.ckeditor(function() {
                var elementId = element.attr("id");
                var editor = this;
                if(elementId) {
                    $("label[for=" + elementId + "]").click(function() {
                        editor.focus();
                    })
                }
            }, conf);
        } else if(console && console.error) {
            console.error("CKEditor not loaded! Make sure that /theme/ckeditor/ckeditor.js and /theme/ckeditor/adapters/jquery.js are included in your page.");
        }
    });
}

$(function() {
    setupRichTextEditors();
});

function configureBulkEditTextField(id, checkboxName) {
    $("#" + id).keypress(function(event) {
        var keyCode = event.keyCode || event.which;
        if(keyCode != 9) { //9 = Tab
            $("input[name=" + checkboxName + "]").prop("checked", true);
        }
    });
}

function configureBulkEditDateField(id, checkboxName) {
    configureBulkEditTextField(id, checkboxName);
    configureBulkEditField(id, checkboxName);
}

function configureBulkEditField(id, checkboxName) {
    $("#" + id).change(function() {
        if($(this).val()) {
            $("input[name=" + checkboxName + "]").prop("checked", true);
        }
    });
}

var portofino = portofino || {};

portofino._setupRichTextEditors = setupRichTextEditors;

portofino.contextPath = '';

portofino.setupRichTextEditors = function(config) {
    config = config || {};
    var windowWidth, windowHeight;
    if (window.innerWidth && window.innerHeight) {
        windowWidth = window.innerWidth;
        windowHeight = window.innerHeight;
    } else if (document.compatMode=='CSS1Compat' &&
        document.documentElement &&
        document.documentElement.offsetWidth ) {
        windowWidth = document.documentElement.offsetWidth;
        windowHeight = document.documentElement.offsetHeight;
    } else if (document.body && document.body.offsetWidth) {
        windowWidth = document.body.offsetWidth;
        windowHeight = document.body.offsetHeight;
    }

    var baseConfig = {};
    if(windowHeight) {
        baseConfig.height =
                windowHeight -
                $("textarea.mde-form-rich-text").offset().top -
                $("footer").height() -
                350; //350 ~= toolbar 3 righe + footer + margine tolleranza
    }

    config = $.extend(baseConfig, {
        customConfig : portofino.contextPath + '/m/pageactions/ckeditor-custom/config.js',
        toolbar: 'PortofinoDefault',
        toolbarCanCollapse: false,
        filebrowserWindowWidth : windowWidth,
        filebrowserWindowHeight : windowHeight
    }, config);

    $('textarea.mde-form-rich-text').data('mdeRichTextConfig', config);
    portofino._setupRichTextEditors();
};

portofino.copyFormAsHiddenFields = function(source, form) {
    source.find("input, select").each(function(index, elem) {
        elem = $(elem);
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", elem.attr('name'));
        hiddenField.setAttribute("value", elem.val());
        form.append(hiddenField);
    });
};

portofino.enablePageActionDragAndDrop = function(button, originalPath) {
    $("div.embeddedPageActions").sortable({
        connectWith: "div.embeddedPageActions",
        placeholder: "embeddedPageActionPlaceholder",
        cursor: "move", // cursor image
        revert: true, // moves the page to its new position with a smooth transition
        tolerance: "pointer" // mouse pointer overlaps the droppable
    }).disableSelection().addClass("pageActionBox");

//    var container = $(".content");//superpan comment
    //yujun 修改 解决portofino-war-jee\src\main\webapp\theme\templates\column3-layout\normal.jsp 
    // edit layot时，没有保存按钮
    var container = $(".content_panel");
    container.prepend('\
        <form action="' + portofino.contextPath + '/actions/admin/page" method="post">\
            修改页面布局: \
            <input type="hidden" name="originalPath" value="' + originalPath + '" />\
            <button name="updateLayout" type="submit" class="btn btn-primary">保存</button>\
            <button name="cancel" type="submit" class="btn btn-default">取消</button>\
        </form>');
    container.find("button[name=updateLayout]").click(function() {
        var theButton = $(this);
        $('div.embeddedPageActions').each(function(index, element) {
            var wrapper = $(element);
            var listName = wrapper.data("page-action-list");
            if(listName) {
                var elements = wrapper.sortable('toArray');
                for(var e in elements) {
                    var id = elements[e].substring("embeddedPageAction_".length);
                    if((id || "").length < 1) {
                        continue;
                    }
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", "embeddedPageAction_" + listName);
                    hiddenField.setAttribute("value", id);
                    theButton.before(hiddenField);
                }
            }
        });
        return true;
    });
    button.off("click");
};

portofino.confirmDeletePage = function(pagePath, contextPath) {
    var dialogDiv = $("<div></div>").appendTo($("body"));
    dialogDiv.load(contextPath + "/actions/admin/page?confirmDelete&originalPath=" + pagePath, function() {
        var dialog = dialogDiv.find(".dialog-confirm-delete-page");
        dialog.find("button[name=confirmDeletePageButton]").click(function() {
            var form = $("#pageAdminForm");
            portofino.copyFormAsHiddenFields(dialog, form);
            form.submit();
        });

        dialog.find("button[name=cancelDeletePageButton], button[name=closeDeletePageButton]").click(function() {
            dialog.modal("hide");
            dialog.remove();
        });
        dialog.modal({ backdrop: 'static'});
    });
};

portofino.showMovePageDialog = function(pagePath, contextPath) {
    var dialogDiv = $("<div></div>").appendTo($("body"));
    dialogDiv.load(contextPath + "/actions/admin/page?chooseNewLocation&originalPath=" + pagePath, function() {
        var dialog = dialogDiv.find(".dialog-move-page");
        dialog.find("button[name=confirmMovePageButton]").click(function() {
            var form = $("#pageAdminForm");
            portofino.copyFormAsHiddenFields(dialog, form);
            form.submit();
        });

        dialog.find("button[name=cancelMovePageButton], button[name=closeMovePageButton]").click(function() {
            dialog.modal("hide");
            dialog.remove();
        });
        dialog.modal({ backdrop: 'static'});
    });
};

portofino.showCopyPageDialog = function(pagePath, contextPath) {
    var dialogDiv = $("<div></div>").appendTo($("body"));
    dialogDiv.load(contextPath + "/actions/admin/page?copyPageDialog&originalPath=" + pagePath, function() {
        var dialog = dialogDiv.find(".dialog-copy-page");
        dialog.find("button[name=confirmCopyPageButton]").click(function() {
            var form = $("#pageAdminForm");
            portofino.copyFormAsHiddenFields(dialog, form);
            form.submit();
        });

        dialog.find("button[name=cancelCopyPageButton], button[name=closeCopyPageButton]").click(function() {
            dialog.modal("hide");
            dialog.remove();
        });
        dialog.modal({ backdrop: 'static'});
    });
};

setupRichTextEditors = function() {/* Do nothing (remove default initialization by Elements) */};

var HTML_CHARS = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#x27;',
    '/': '&#x2F;',
    '`': '&#x60;'
};

function htmlEscape (string) {
    if(string == null) {
        return string;
    }
    return (string + '').replace(/[&<>"'\/`]/g, function (match) {
        return HTML_CHARS[match];
    });
}

$(function() {
    portofino.locale = $("html").attr("lang").substring(0, 2).toLowerCase();

    $('form').find(':submit').click(function() {
        var form = $(this).prop('form');
        $(form).data('form-post-button', $(this));
    });

    $('form').on('submit', function() {
        var postedButton = $(this).data('form-post-button');
        if(postedButton && !postedButton.is(".no-ui-block")) {
            //Prevent double submit
            var form = $(this);
            var buttons = form.find(":submit");
            buttons.each(function(index, current) {
                var button = $(current);
                var clone = button.clone();
                var display = button.css("display");
                clone.removeAttr("name");
                clone.attr("disabled", "disabled");
                button.css("display", "none");
                button.after(clone);
                button.appendTo(form);
            });
        }

        //Page abandon
        $(this).data("dirty", false);
    });

    //Page abandon
    $(':input').on("change", function() {
        $(this).closest("form").data("dirty", true);
    });

    window.onbeforeunload = function() {
        var dirty = false;
        $("form:not(.dont-prompt-on-page-abandon)").each(function(index, form) {
            if($(form).data("dirty")) {
                dirty = true;
            }
        });
        if (dirty) {
            return portofino.messages[portofino.locale]['pageactions.page.abandon.are.you.sure'];
        }
    };
});