# Mask Studio 🎭

**Mask Studio** is an interactive Android application designed for mask enthusiasts and designers. It allows users to browse a diverse catalog of masks, customize them using a built-in digital canvas, and proceed through a seamless checkout process with integrated location services.

---

## 🚀 Features

### 1. Interactive Mask Canvas
- **Custom Design Engine**: A high-performance drawing canvas built using the Android `Canvas` and `Path` APIs.
- **Predefined Templates**: Choose from several iconic mask styles:
  - **Oni Mask**: Traditional Japanese demon aesthetics.
  - **Kitsune Mask**: Sacred fox-inspired geometry.
  - **Cyberpunk Mask**: Futuristic sci-fi visor design.
  - **Gala/Comedy**: Classic theatrical styles.
- **Drawing Tools**: Smooth path rendering with customizable colors.

### 2. Smart Catalog
- **Dynamic Search**: Real-time filtering of mask items based on names and descriptions.
- **Intuitive UI**: Built with `RecyclerView` for a smooth scrolling experience and Material Design components.

### 3. Integrated Checkout & Delivery
- **Embedded Maps**: Real-time address visualization using an embedded Google Maps WebView.
- **Flexible Payments**: Support for Credit/Debit Cards (with auto-formatting expiry) and GCash.
- **Address Auto-lookup**: Search and verify delivery locations instantly.

### 4. User Experience
- **Modern UI**: Dark-themed, sleek interface using Material3 guidelines.
- **Responsive Layouts**: Optimized for various screen sizes using `ConstraintLayout`.

---

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Minimum SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34
- **Libraries**:
  - **Core**: Android Jetpack (Activity-KTX, Core-KTX)
  - **UI**: Material Components, ConstraintLayout, RecyclerView
  - **Geospatial**: Google Maps Embed API
  - **Build System**: Gradle Kotlin DSL

---

## 📂 Project Structure

```text
app/src/main/java/com/example/maskstudio/
├── MaskCanvasView.kt   # Core logic for the drawing engine
├── CatalogActivity.kt  # Searchable mask inventory
├── CanvasActivity.kt   # The creative workspace
├── CheckoutActivity.kt # Payment and Map integration logic
├── LoginActivity.kt    # User authentication entry point
└── MaskAdapter.kt      # RecyclerView binding logic
```

---

## ⚙️ Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 11+
- Android Device or Emulator (API 26+)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/MaskStudio.git
   ```
2. Open the project in **Android Studio**.
3. Wait for Gradle sync to complete.
4. Click **Run** to launch on your device.

---

## 📸 Screenshots (Placeholders)
*Note: Add your actual app screenshots here to make the repo look professional!*

| Catalog | Canvas Editor | Checkout |
| :---: | :---: | :---: |
| ![Catalog](https://via.placeholder.com/200x400?text=Catalog+Screen) | ![Canvas](https://via.placeholder.com/200x400?text=Design+Screen) | ![Checkout](https://via.placeholder.com/200x400?text=Checkout+Screen) |

---

## 🗺️ Future Roadmap
- [ ] Save designs to local storage/gallery.
- [ ] Multi-layer support for the canvas.
- [ ] Real-time Firebase backend for user accounts.
- [ ] Native Google Maps SDK integration for better performance.

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
