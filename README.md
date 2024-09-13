# Android Waypoint Tracker App

This is an Android app built using **Kotlin** and **Jetpack Compose**. The app allows users to track their movement using GPS, save waypoints, and display their direction and distance from selected waypoints using a dynamic compass. The compass adapts to the user's orientation and displays nearby waypoints. 

## Features

- **MainActivity & SharedComposables**: The project starts with `MainActivity.kt`, and all composable functions are defined in `SharedComposables.kt`.
- **Custom Compass Canvas**: A custom canvas displays the four major compass directions (north, east, west, and south), with the north direction highlighted in a distinct color. The canvas automatically adjusts to a square shape.
- **Compass Rotation**: A listener tracks the device’s rotation vector, rotating the compass to always show the correct orientation based on the direction the user is facing.

- **Tracking Start/Stop Button**: Users can start and stop GPS tracking using a dedicated button. When tracking starts, the app updates the user’s location every 5 seconds.
- **Save Waypoints**: While tracking, users can save their current GPS location as a waypoint by pressing a button. Waypoints are stored in a file and updated instantly when new waypoints are added.
- **Persistent Waypoint List**: Waypoints are stored persistently in a file created by the app, ensuring that the list of waypoints is preserved even after restarting the app.

- **Clear Waypoints**: Users can clear the entire list of waypoints with a confirmation dialog.
- **Select Waypoints**: A UI control allows users to select a waypoint from the list. The compass updates to display the direction of the selected waypoint relative to the user.
- **Distance to Waypoint**: The app displays the distance (in meters) from the user’s current location to the selected waypoint, updating as the user moves.

- **Nearby Waypoints on Compass**: The custom canvas displays circles representing all waypoints within 500 meters of the user, with the center of the compass representing 0 meters and the edge representing 500 meters.
- **Highlighted Waypoints**: The currently selected waypoint is highlighted on the canvas, making it easy to distinguish from other waypoints.
- **Automatic Waypoint Selection**: When the user is within 10 meters of the current waypoint, the app automatically selects the next waypoint in the list.
- **UI Design**: The UI is thoughtfully designed to be intuitive and user-friendly, ensuring a smooth user experience.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **GPS API** for location tracking
- **Rotation Vector Sensor** for compass orientation
- **File I/O** for waypoint storage
- **Visual Studio IDE**


