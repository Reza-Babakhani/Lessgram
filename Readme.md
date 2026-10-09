# Lessgram

**A personal Android customization project based on FeurStagram, focused on keeping Instagram's useful social features with fewer distractions.**

Lessgram is a fork of [FeurStagram](https://github.com/jean-voila/FeurStagram), originally developed by Jean Herail ([@jean-voila](https://github.com/jean-voila)) and the FeurStagram contributors. The original patches, runtime code, build tooling, and documentation provide the foundation for this project. Full credit for that work belongs to their respective authors.

This repository contains patches and extension code applied to the official Instagram Android app. It is not a standalone implementation of Instagram.

[Source](https://github.com/Reza-Babakhani/Lessgram) · [Build from source](#build-from-source) · [Fork releases](https://github.com/Reza-Babakhani/Lessgram/releases) · [Upstream project](https://github.com/jean-voila/FeurStagram) · [License](LICENSE)

## Fork status

Lessgram is the starting point for a custom app maintained in [Reza-Babakhani/Lessgram](https://github.com/Reza-Babakhani/Lessgram). This README was adapted for the fork on October 9, 2026; the application code has not been rebranded as part of this documentation change.

The inherited implementation still uses FeurStagram names in its interface, Java/Kotlin packages, preferences, signing variables, and APK output. Its automatic update checker still points to **upstream FeurStagram releases**, rather than Lessgram releases. You can disable automatic update checks in the app's settings while developing your own build.

The fork's releases page is the place to check for Lessgram builds. [Upstream APKs](https://github.com/jean-voila/FeurStagram/releases) are FeurStagram builds and should not be presented as Lessgram releases.

## Features inherited from FeurStagram

The patches aim to preserve Direct Messages, stories, search, profiles, notifications, and deep links while limiting distracting surfaces. Actual compatibility depends on the Instagram version being patched.

| Feature | Default behavior | Configurable |
| --- | --- | --- |
| Home feed | Visible | Yes |
| Explore | Blocked | Yes |
| Reels | Blocked | Yes |
| Friends lane in Reels | Hidden | Yes |
| Stories | Visible | Yes |
| Suggested accounts | Blocked | Yes |
| Ads | Blocked | Yes |
| Notes | Visible | Yes |
| Instants | Hidden | Yes |
| Notifications button | Visible | Yes |
| Selected analytics and telemetry requests | Blocked | No |
| Shopping and commerce preloads | Blocked | No |

Long-press the **Home tab** to open the inherited settings page. It also supports navigation visibility, a startup landing page, a following-only feed enabled by default, and disabling forced HDR display.

Settings persist across restarts. A permanent lock can prevent relaxing existing restrictions for the current installation; review the [FAQ](docs/FAQ.md) before enabling it.

## Build from source

### Requirements

- JDK 21.
- Android SDK with build-tools installed and `ANDROID_HOME` set.
- Bash for `build.sh`; on Windows, use a suitable Bash environment such as WSL with the build dependencies configured there.
- A GitHub token with `read:packages` access for the Morphe dependencies, configured in your user-level `~/.gradle/gradle.properties`:

  ```properties
  gpr.user=<your-github-username>
  gpr.key=<token-with-read:packages>
  ```

- A [Morphe Desktop CLI](https://github.com/MorpheApp/morphe-desktop) JAR in `tools/`, named `morphe-cli-*.jar` or `morphe-desktop-*.jar`, as expected by `build.sh`.
- A compatible Instagram APK obtained from a source you trust. Split bundles (`.apkm`, `.xapk`, or `.apks`) additionally require an [APKEditor](https://github.com/REAndroid/APKEditor) JAR named `APKEditor-*.jar` in `tools/`.
- Android platform-tools (`adb`) if you want to install on a connected device.

Keep tokens in your user-level Gradle configuration, outside the repository.

### Clone and build

```bash
git clone https://github.com/Reza-Babakhani/Lessgram.git
cd Lessgram
./build.sh instagram.apk
```

The current build script writes the signed result to **`lessgram.apk`**.

To install alongside stock Instagram and push the result to a connected device:

```bash
./build.sh instagram.apk --clone --install
```

The `--clone` option installs Lessgram alongside stock Instagram as
`com.instagram.android.lessgram`.

To install a build manually:

```bash
adb install -r lessgram.apk
```

For additional upstream build details, see [Build from source](docs/BUILD_FROM_SOURCE.md). That guide still uses the upstream repository URL; use the Lessgram clone command above for this fork.

### Signing

The build script accepts `LESSGRAM_KEYSTORE`, `LESSGRAM_KEY_ALIAS`,
`LESSGRAM_KEYSTORE_PASS`, and `LESSGRAM_KEY_PASS`. Without a keystore
password, it uses a throwaway signing key for testing.

Use your own signing key and reuse it across your releases so Android can install updates in place. Keep keystores and credentials out of version control.

## Development

| Location | Purpose |
| --- | --- |
| `patches/` | Kotlin patches and fingerprints that locate injection points |
| `extensions/extension/` | Java runtime code merged into the patched app |
| `build.sh` | Builds the patch bundle, applies it, and signs the APK |
| `gradle/` | Gradle wrapper and dependency configuration |
| `docs/` | Inherited installation, build, privacy, FAQ, and design documentation |
| `assets/` | Inherited project assets |

Start with [Design](docs/DESIGN.md) before changing the settings interface. Instagram updates can change the code targeted by fingerprints, so verify patches against the APK version you intend to use.

The existing documentation and assets describe FeurStagram unless explicitly updated for this fork:

- [Installation](docs/INSTALL.md)
- [Build from source](docs/BUILD_FROM_SOURCE.md)
- [FAQ](docs/FAQ.md)
- [Privacy](docs/PRIVACY.md)
- [Contributing](CONTRIBUTING.md)
- [Security policy](SECURITY.md)
- [Changelog](CHANGELOG.md)

## Privacy and affiliation

Authentication and core Instagram services remain handled by the Instagram app and Meta's infrastructure. Blocking selected telemetry endpoints does not make the app anonymous or eliminate all data collection by Instagram. The inherited update checker contacts GitHub when enabled.

Lessgram is an unofficial, independent fork. It is not affiliated with or endorsed by Instagram, Meta, the FeurStagram maintainers, Morphe, or Piko. Instagram and Meta names, trademarks, and original application assets belong to their respective owners.

## Credits, copyright, and license

Lessgram retains the **GNU General Public License v3.0** used by FeurStagram. See [LICENSE](LICENSE) for the full terms and [NOTICE](NOTICE) for the inherited copyright and third-party attribution notices. Both files are preserved unchanged.

The original copyright notice is:

> Copyright (C) Feurstagram contributors.

Credits for the foundation and dependencies:

- **[FeurStagram](https://github.com/jean-voila/FeurStagram)** — original project by Jean Herail and contributors; the base for this fork.
- **[Morphe](https://morphe.software)** — patcher and Gradle tooling. As recorded in `NOTICE`, the Morphe name is used descriptively for compatibility; this is an independent project and does not imply Morphe authorship or affiliation.
- **[Piko](https://github.com/crimera/piko)** — source of adapted Instagram patch approaches, notably network blocking and clone techniques, as recorded in `NOTICE`.
- **[Reza-Babakhani](https://github.com/Reza-Babakhani)** — Lessgram fork maintenance and customization.

Forking or renaming the project does not transfer ownership of upstream work. Preserve existing copyright, license, and attribution notices when modifying or redistributing it, and follow the GPLv3 requirements for any covered source or binaries you distribute. The GPL license for this repository does not relicense Instagram's proprietary application or trademarks.

The project is provided without warranty, as described in `LICENSE`.

## Contributing and support

Report fork-specific problems through [Lessgram issues](https://github.com/Reza-Babakhani/Lessgram/issues), and submit proposed changes through [Lessgram pull requests](https://github.com/Reza-Babakhani/Lessgram/pulls). Include the Android version, Instagram APK version, build details, and steps to reproduce any bug.

For upstream development and support, visit [FeurStagram](https://github.com/jean-voila/FeurStagram).
