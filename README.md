![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![XML](https://img.shields.io/badge/XML-0060AC?style=for-the-badge&logo=xml&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min%20SDK-23-4CAF50?style=for-the-badge)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-2196F3?style=for-the-badge)

# Braille Translator

A lightweight, efficient Android application for translating English text into Braille and Braille back into English.

## Overview

Braille Translator provides a simple, reliable way to convert between standard English text
and Unicode Braille symbols. Built with accessibility and ease of use in mind, the app
supports bidirectional translation, automatic input detection, and full compatibility with
Android's system theming.

## Features

- **Two-Way Translation** — Switch seamlessly between Text-to-Braille and Braille-to-Text modes.
- **Automatic Mode Detection** — The app analyzes input and suggests the appropriate translation direction automatically.
- **Clipboard Integration** — Copy translation results to the clipboard with a single tap.
- **Input Management** — Clear current input and output instantly to begin a new translation.
- **Dark Mode Support** — Fully compatible with Android's system-wide light and dark themes.
- **Unicode-Based Encoding** — Uses the standard Unicode Braille Patterns block (`U+2800`–`U+28FF`) to ensure broad compatibility across platforms and devices.

## Tech Stack

| Component    | Details                        |
|--------------|--------------------------------|
| Language     | Java                           |
| UI Framework | Android XML (ConstraintLayout) |
| Minimum SDK  | 23 (Android 6.0)               |
| Target SDK   | 36 (Android 16)                |
| Build System | Gradle (Kotlin DSL)            |

## Project Structure

- `app/src/main/java/.../MainActivity.java`: Handles the UI logic and user interactions.
- `app/src/main/java/.../BrailleTranslator.java`: The core engine containing the translation mappings (Letters, Numbers, and Punctuation).
- `app/src/main/res/layout/activity_main.xml`: The main user interface design.

## How to Use

1. Enter text into the input field.
2. Tap **Translate**.
    - English input is converted to Braille.
    - Braille input is converted to English.
3. Use the **Switch** button to manually override the detected translation mode.
4. Tap the **Copy** icon to copy the result to the clipboard.

## Building the Project

1.  Clone the repository.
2.  Open the project in **Android Studio**.
3.  Ensure you have **JDK 21** configured.
4.  Sync Gradle and click **Run**.
