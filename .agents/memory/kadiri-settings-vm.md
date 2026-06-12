---
name: Kadiri Settings ViewModel
description: AyarlarViewModel state and DataStore method mapping
---

AyarlarViewModel uses `AyarlarUiState` data class (NOT a `UserPreferences` data class — that doesn't exist).

DataStore method mapping:
- `isDarkMode`           → `prefs.setDarkTheme(Boolean)`
- `notificationsEnabled` → `prefs.setBildirimNamaz(Boolean)`
- `fontSize`             → `prefs.setFontSizeScale(Float)` — scale factor = fontSize/16f
- `language`             → `prefs.setLanguage(String)` — direct

**Why:** UserPreferencesDataStore exposes individual flows; AyarlarViewModel combines them with `combine()` into AyarlarUiState.
