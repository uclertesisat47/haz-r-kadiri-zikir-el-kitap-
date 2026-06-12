---
name: Kadiri Theme Name
description: Exact theme names used in AndroidManifest and themes.xml
---

Splash theme name: `Theme.KadiriElKitabi.Splash`
- Defined in: `app/src/main/res/values/themes.xml`
- Referenced in: `android:theme` attribute of `<application>` in AndroidManifest.xml

Main theme: `Theme.KadiriElKitabi` (parent: Material3.DayNight.NoActionBar)

**Why:** Early manifest draft used `Theme.Kadiri.SplashScreen` which caused resource-not-found crash at startup.
