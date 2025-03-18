# Cities Challenge

## Technical solutions

* **Filtering:** To improve the filtering process, I decided to download the data and store it in local database.
    Additionally, to enhance search efficiency, I created a composite index using the city name and country, as both are used in every request.

* **Sorting:** I chose to sort the results directly in the SQL query, as this approach is more efficient for this type of search.

* **Use Cases:** While use cases were not strictly required in this case, I preferred to implement them to define a clear domain layer.

* **Screens:** My solution consists of two main screens:
  * **CitiesFilterScreen:** This screen loads the list of cities stored in the database from the start 
      and displays all results. It also includes a search bar that provides a filtered section with matching results. 
      Additionally, there is a checkbox that allows filtering only favorite cities.
  * **CityMapScreen:** The toggle for marking a city as a favorite is placed on this screen.

* **Data Download:** The initial data download occurs the first time the app is launched. After that, 
    users can manually refresh the data using the refresh icon in the toolbar.

## Pending or Improvements

* **Dimens:** A strategy should be created to handle dimensions for different device types. This could be done 
    using the traditional approach (via dimens.xml) or by implementing a Kotlin class that adjusts dimensions based on screen density.
* **UI Testing:** Unit tests were implemented for ViewModels, mappers, and repositories, but UI tests are still pending.