package com.spoon.cookme.application;

import android.content.Context;

import com.spoon.cookme.backend.events.EventBuses;
import com.spoon.corelib.backend.settings.SettingBooleanValue;
import com.spoon.corelib.backend.settings.SettingDateTimeValue;
import com.spoon.corelib.backend.settings.SettingGroup;
import com.spoon.corelib.backend.settings.SettingIntegerValue;
import com.spoon.corelib.backend.settings.SettingStringValue;

import org.joda.time.DateTime;

/**
 * Created by Lycha on 9/5/2015.
 */
public class Settings {

    public static GeneralGroup General;
    public static AccessTokens Tokens;
    public static VersionGroup VersionAndUpdates;
    public static SessionGroup Session;

    public static void init(final Context context) {
        General = new GeneralGroup(context);
        Tokens = new AccessTokens(context);
        VersionAndUpdates = new VersionGroup(context);
        Session = new SessionGroup(context);
    }

    public static class GeneralGroup extends SettingGroup {
        public static final String NAME = "General";
        public GeneralGroup(Context context) {
            super(NAME, context);
        }

        public SettingIntegerValue ClickCounter = new SettingIntegerValue("clickCounter", this, EventBuses.Settings, 0);
    }

    public static class AccessTokens extends SettingGroup {
        public static final String NAME = "AccessTokens";
        public AccessTokens(Context context) {
            super(NAME, context);
        }

        public SettingStringValue UpdateToken   = new SettingStringValue("UpdateToken", this, EventBuses.Settings, "");
        public SettingStringValue LocationToken = new SettingStringValue("LocationUpdateToken", this, EventBuses.Settings, "");
    }

    public static class VersionGroup extends SettingGroup {
        public static final String NAME = "Version";
        public VersionGroup(Context context) {
            super(NAME, context);
        }

        public SettingBooleanValue UpdateRequired = new SettingBooleanValue("updateRequired", this, EventBuses.Settings, false);
        public SettingIntegerValue CriticalCode = new SettingIntegerValue("criticalCode", this, EventBuses.Settings, -1);
        public SettingIntegerValue UpdateCode = new SettingIntegerValue("updateCode", this, EventBuses.Settings, -1);
        public SettingStringValue UpdateName = new SettingStringValue("updateName", this, EventBuses.Settings, "");
        public SettingStringValue UpdateUrl = new SettingStringValue("updateUrl", this, null, "");
        public SettingStringValue UpdateFile = new SettingStringValue("updateFile", this, null, "");
        public SettingDateTimeValue UpdateChecked = new SettingDateTimeValue("updateCheckedDate", this, EventBuses.Settings, new DateTime(0));
        public SettingBooleanValue UpdateDownloaded = new SettingBooleanValue("updateDownloaded", this, EventBuses.Settings, false);
        public SettingIntegerValue UpdateCurrentDownloadId = new SettingIntegerValue("updateDownloadId", this, null, -1);
        public SettingBooleanValue InstallAutomatically = new SettingBooleanValue("installAutomatically", this, null, false);
    }

    public static class SessionGroup extends SettingGroup {
        public static final String NAME = "Sessions";
        public SessionGroup(Context context) {
            super(NAME, context);
        }

        public SettingStringValue LastPostmanDisplay = new SettingStringValue("LastUserDisplayName", this, null, null);
        public SettingStringValue LastPostmanId = new SettingStringValue("LastUserId", this, null, null);

        public void clearSession() {
            LastPostmanDisplay.disableEventPropagationForNextChange().clear();
            LastPostmanId.disableEventPropagationForNextChange().clear();
        }

        public boolean canContinueSession() {
            return LastPostmanId.getValue() != null;
        }
    }


}
