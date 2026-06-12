---
name: Kadiri Navigation Routes
description: Route data class definitions and navigation API for KADİRİ TARİKATI app
---

KitapDetayRoute is `data class KitapDetayRoute(val kitapId: String)` — string ID, not Int.
The kitap domain model uses `bolumId: Long` internally in data/domain layers, but the navigation route only needs the top-level `kitapId: String`.

**Why:** Previous session defined the route with bolumId/sayfaId Ints which didn't match the KitapDetayScreen's `kitapId: String` parameter.

**How to apply:** When navigating to kitap detail, pass `kitap.id` (String). The KitapDetayScreen calls `viewModel.loadKitap(kitapId)` to fetch all bolumler.
