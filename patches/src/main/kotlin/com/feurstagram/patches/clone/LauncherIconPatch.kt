package com.feurstagram.patches.clone

import app.morphe.patcher.patch.ResourcePatchContext
import app.morphe.patcher.patch.resourcePatch
import com.feurstagram.patches.shared.Constants.COMPATIBILITY_INSTAGRAM
import java.io.File

private const val ICON_RESOURCE = "@mipmap/ic_launcher"

private object LauncherIconResource

private val ICON_ASSETS = listOf(
    "mipmap-mdpi/ic_launcher.png",
    "mipmap-mdpi/ic_launcher_round.png",
    "mipmap-hdpi/ic_launcher.png",
    "mipmap-hdpi/ic_launcher_round.png",
    "mipmap-xhdpi/ic_launcher.png",
    "mipmap-xhdpi/ic_launcher_round.png",
    "mipmap-xxhdpi/ic_launcher.png",
    "mipmap-xxhdpi/ic_launcher_round.png",
    "mipmap-xxxhdpi/ic_launcher.png",
    "mipmap-xxxhdpi/ic_launcher_round.png",
)

@Suppress("unused")
val launcherIconPatch = resourcePatch(
    name = "Lessgram launcher icon",
    description = "Replaces the cloned app launcher icon with the Lessgram icon.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_INSTAGRAM)

    execute {
        document("AndroidManifest.xml").use { manifest ->
            val application = manifest.documentElement
            application.setAttribute("android:icon", ICON_RESOURCE)
            application.setAttribute("android:roundIcon", ICON_RESOURCE)
        }

        for (asset in ICON_ASSETS) {
            copyBundledIcon(asset)
        }
    }
}

private fun ResourcePatchContext.copyBundledIcon(asset: String) {
    val source = LauncherIconResource::class.java.getResourceAsStream("/launcher/$asset")
        ?: error("Lessgram launcher asset was not bundled into the patch: $asset")
    val destination: File = get("res/$asset")
    source.use { input ->
        destination.outputStream().use { output -> input.copyTo(output) }
    }
}
