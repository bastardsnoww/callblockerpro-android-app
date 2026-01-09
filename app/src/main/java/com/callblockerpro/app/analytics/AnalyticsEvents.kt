package com.callblockerpro.app.analytics

/**
 * Analytics event names used throughout the app
 */
object AnalyticsEvents {
    // App Lifecycle
    const val APP_OPENED = "app_opened"
    const val SCREEN_VIEW = "screen_view"
    
    // Call Blocking Events
    const val CALL_BLOCKED = "call_blocked"
    const val CALL_ALLOWED = "call_allowed"
    const val CALL_SCREENED = "call_screened"
    
    // List Management
    const val CONTACT_ADDED_TO_WHITELIST = "contact_added_to_whitelist"
    const val CONTACT_REMOVED_FROM_WHITELIST = "contact_removed_from_whitelist"
    const val CONTACT_ADDED_TO_BLOCKLIST = "contact_added_to_blocklist"
    const val CONTACT_REMOVED_FROM_BLOCKLIST = "contact_removed_from_blocklist"
    const val BULK_IMPORT_CONTACTS = "bulk_import_contacts"
    
    // Mode Changes
    const val BLOCKING_MODE_CHANGED = "blocking_mode_changed"
    const val SCHEDULE_CREATED = "schedule_created"
    const val SCHEDULE_UPDATED = "schedule_updated"
    const val SCHEDULE_DELETED = "schedule_deleted"
    const val SCHEDULE_TOGGLED = "schedule_toggled"
    
    // Backup & Restore
    const val BACKUP_CREATED = "backup_created"
    const val BACKUP_RESTORED = "backup_restored"
    const val CLOUD_BACKUP_CREATED = "cloud_backup_created"
    const val CLOUD_BACKUP_RESTORED = "cloud_backup_restored"
    const val AUTO_BACKUP_ENABLED = "auto_backup_enabled"
    const val AUTO_BACKUP_DISABLED = "auto_backup_disabled"
    const val ENCRYPTION_ENABLED = "encryption_enabled"
    const val ENCRYPTION_DISABLED = "encryption_disabled"
    
    // Settings
    const val THEME_CHANGED = "theme_changed"
    const val NOTIFICATION_SETTINGS_CHANGED = "notification_settings_changed"
    const val PERMISSION_GRANTED = "permission_granted"
    const val PERMISSION_DENIED = "permission_denied"
    
    // Google Drive Integration
    const val GOOGLE_SIGN_IN = "google_sign_in"
    const val GOOGLE_SIGN_OUT = "google_sign_out"
    const val GOOGLE_SIGN_IN_FAILED = "google_sign_in_failed"
    
    // Errors
    const val ERROR_OCCURRED = "error_occurred"
    const val BACKUP_FAILED = "backup_failed"
    const val RESTORE_FAILED = "restore_failed"
}

/**
 * Analytics parameter names
 */
object AnalyticsParams {
    // Common
    const val SCREEN_NAME = "screen_name"
    const val ITEM_ID = "item_id"
    const val ITEM_NAME = "item_name"
    const val SUCCESS = "success"
    const val ERROR_MESSAGE = "error_message"
    const val ERROR_TYPE = "error_type"
    
    // Call related
    const val PHONE_NUMBER = "phone_number"
    const val CALL_TYPE = "call_type"
    const val BLOCKING_REASON = "blocking_reason"
    const val IS_CONTACT = "is_contact"
    
    // Mode related
    const val MODE_TYPE = "mode_type"
    const val PREVIOUS_MODE = "previous_mode"
    const val NEW_MODE = "new_mode"
    
    // Backup related
    const val BACKUP_TYPE = "backup_type"
    const val BACKUP_SIZE = "backup_size"
    const val IS_ENCRYPTED = "is_encrypted"
    const val IS_AUTO_BACKUP = "is_auto_backup"
    const val BACKUP_LOCATION = "backup_location"
    
    // List management
    const val LIST_TYPE = "list_type"
    const val CONTACT_COUNT = "contact_count"
    const val IMPORT_SOURCE = "import_source"
    
    // Settings
    const val THEME_MODE = "theme_mode"
    const val PERMISSION_TYPE = "permission_type"
    const val SETTING_NAME = "setting_name"
    const val SETTING_VALUE = "setting_value"
}
