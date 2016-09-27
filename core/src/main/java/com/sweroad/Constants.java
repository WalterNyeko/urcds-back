package com.sweroad;

/**
 * Constant values used throughout the application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

    private Constants() {
        // hide me
    }

    // ~ Static fields/initializers =============================================

    /**
     * Assets Version constant
     */
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key to the same one that Struts
     * uses, we get synchronization in Struts w/o having to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";

    /**
     * The name of the Super User role, as specified in web.xml
     */
    public static final String SUPER_USER_ROLE = "ROLE_SUPER_USER";

    /**
     * The name of the Readonly role, as specifeid in the web.xml
     */
    public static final String READONLY_ROLE = "ROLE_READONLY";

    /**
     * The name of the user's role list, a request-scoped attribute when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     *
     * @deprecated No longer used to set themes.
     */
    public static final String CSS_THEME = "csstheme";

    /**
     * The link used for viewing crash details
     */
    public static final String CRASH_VIEW_LINK = "/crashview?id=%s";

    /**
     * The link used to go to crash edit page
     */
    public static final String CRASH_EDIT_LINK = "/crashform?id=%s";

    /**
     * The link used to remove a crash
     */
    public static final String CRASH_REMOVE_LINK = "/crashremove?id=%s";

    /**
     * Days of the week array
     */
    public static final String[] DAYS_OF_WEEK = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};

    public static final String[] MONTHS_OF_YEAR_KEYS = new String[] { "rcds.jan", "rcds.feb", "rcds.mar", "rcds.apr", "rcds.may",
            "rcds.jun", "rcds.jul", "rcds.aug", "rcds.sep", "rcds.oct", "rcds.nov", "rcds.dec" };

    public static final String[] MONTHS_OF_YEAR = new String[] { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    /**
     * Not Injured id for casualty type stored in db
     */
    public static final long NOT_INJURED_ID = 4L;

    public static final int HOURS_IN_DAY = 24;

    public static final String NOT_SPECIFIED = "Not Specified";

    public static final long NOT_SPECIFIED_ID = -1L;
}
