### Earthquake app

This applicaion follows a basic MVVM architecture by making use of classes like "ViewModel" and Viewbinding libraries. It is written in pure Java and gets the data from the quakes API.

#### 1. Threading

The application uses the RxJava library to handle all background threads with the network request being made on the `io` scheduler and database transactions being done on the `computation` thread.

#### 2. Network

Retrofit is used to download the xml from the RSS feed. This library highly abstracts most networking methods. It uses OkHttp clients in the background to make the network request.

#### 3. Local

Offline data is available in the application. Data is stored in the local SQLite database. Connection to the database as well as transactions are carried out by Jetpack room which like retrofit, is an abstraction layer.

#### UI

The application makes use of a `RecyclerView` to display the list of data from the local database. On clicking an item in the list, the application navigates to a different fragment which displays a map and more information on the specific item.

#### XML parsing

There is a util named `XmlParser` that loops through the entire xml data to extract the values and creates a `Channel` class that holds all the data.

#### Filtering data

The application makes use of MDC components to select dates and ranges. Both of these libraries return date objects which are used to filter out the data. The viewmodel checks for the reuqired positional attributes, magnitude and depth.

#### Navigation

With the help of Navigation components, the application moves between two fragments, i.e `Home` & `Details` fragments, to show the various sets of data.

**The application has the following flow:**

![flow](https://raw.githubusercontent.com/LinusMuema/Earthquake/main/assets/flow.png?token=ALJIC4UA4W32GELUK5TOIULAKTEE6)

#### Testing

| Requirement         | Test case                                                                                                                                                                                                                                                                         | Test data                                              | Expected outcome                                                                                                                                                               |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| XML parsing         | **Given a sample xml file:**<br>- **Case**: the `parse` function should return the correct channel object from the file                                                                                                                                                           | An xml file containing similar data                    | The items list should not be empty                                                                                                                                             |
| Database operations | **Given a temporary database instance:**<br>- **Case 1**: we should be able to save a `channel` object should be saved correctly.<br>- **Case 2**: the DAO should be able to retrieve the object we previously saved                                                              | A parsed `channel` object from the sample xml file     | - **Case 1**: the operation should run to completion without any errors<br>- **Case 2**: the returned list should not be empty and the operation should not result in an error |
| RecyclerView        | **Given an instance of the recyclerView**<br>- **Case 1**: we should be able to click an item<br>- **Case 2**: scroll operations must available for the recyclerView                                                                                                              | The recyclerView instance from the `home` fragment     | - **Case 1**: the card clicked should show the ripple effect<br>- **Case 2**: the recyclerView should scroll to the specified position                                         |
| Home page           | The fragment should display all the results from the parsed XML file in a scrollable manner. <br>It also supports landscape mode with its own layout                                                                                                                              | The `channel` object from the parsed XML               | The fragment displays the items in both portrait and landscape mode. <br>Screenshots:                                                                                          |
| Details page        | The details fragment should have a map showing the location of an earthquake.<br>The marker should have the same color code as the earthquake color code<br>The page should support landscape mode.                                                                               | One `item` object containing details of the earthquake | The fragment has a map showing the location of the earthquake<br>The marker on the map has the same color code as the earthquake's color code<br>Screenshots:                  |
| Date picker         | The application should allow the user to input a specific date. <br>It should display all the earthquakes that occurred that day<br>There should be labels for the most northerly, southerly, easterly and westerly<br>The deepest and shallowest labels should also be displayed | The selected date                                      | The application filters the earthquakes correctly and assigns the corresponding labels<br>Screenshots:                                                                         |
| Range picker        | The application should allow the user to input a date range<br>It should filter and display all earthquakes that occurred between the range<br>The application should add the same labels as the `date picker` functionality                                                      | The selected date range                                | The application filters the earthquakes correctly and assigns the corresponding labels<br>Screenshots:                                                                         |
