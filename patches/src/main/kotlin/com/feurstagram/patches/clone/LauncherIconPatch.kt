package com.feurstagram.patches.clone

import app.morphe.patcher.patch.ResourcePatchContext
import app.morphe.patcher.patch.resourcePatch
import com.feurstagram.patches.shared.Constants.COMPATIBILITY_INSTAGRAM
import java.io.File

private const val ICON_RESOURCE = "@drawable/lessgram_launcher"

private object LauncherIconResource

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

        val icon = get("res/drawable-nodpi/lessgram_launcher.png")
        copyBundledIcon(icon)
    }
}

private fun ResourcePatchContext.copyBundledIcon(destination: File) {
    val source = LauncherIconResource::class.java.getResourceAsStream("/lessgram-icon.png")
        ?: error("Lessgram launcher icon was not bundled into the patch")
    source.use { input ->
        destination.outputStream().use { output -> input.copyTo(output) }
    }
}
