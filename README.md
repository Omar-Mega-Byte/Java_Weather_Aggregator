# Weather Aggregator

A JavaFX application that provides real-time weather updates for various cities, featuring a dynamic table and multithreading for efficient data fetching.

---

## Features
- Real-time weather updates every 5 seconds.
- Multithreading for optimized data fetching.
- Easy-to-use JavaFX interface with a dynamic table.
- Configurable thread count for performance tuning.

---

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/Omar-Mega-Byte/Java_Weather_Aggregator.git
   ```
2. Configure JavaFX in your IDE.
3. Compile and run:
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml com/example/weathergui/Main.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml com.example.weathergui.Main
   ```

---

## Usage
- Enter the desired number of threads.
- Click **Start Display** to begin fetching weather data.
- Use the **Stop** button to halt the updates.

---

## Architecture
- **Main**: Manages GUI initialization, multithreading, and weather updates.
- **WeatherData**: Represents weather information for each city.
- **API**: Simulates fetching weather data (can be replaced with a real API).

---

## Customization
- Modify the `CITIES` array in `Main` to add or remove cities.
- Adjust the update frequency by modifying the `Thread.sleep()` interval.
---

## License
This project is licensed under the MIT License.

### Team Members:
- Omar Ahmed
- Refaat Ismail
- Sara Ashraf
- Ahmed Hossam
- Ali Gomaa
- Ahmed Maghawry

