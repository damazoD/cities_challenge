package com.damazo.ualachallenge.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.damazo.featurecities.model.City
import com.damazo.featurecities.model.Coordinates
import com.damazo.featurecities.ui.screen.CitiesFilterScreen
import com.damazo.featurecities.ui.screen.CityMapScreen

@ExperimentalMaterial3Api
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Destination.CitiesFilter) {
        composable<Destination.CitiesFilter> {
            CitiesFilterScreen(
                onItemPressed = { city ->
                    navController.navigate(
                        route = Destination.CityMap(
                            id = city.id,
                            displayName = city.displayName,
                            latitude = city.coordinates!!.latitude,
                            longitude = city.coordinates!!.longitude,
                            city.isFavourite
                        )
                    )
                }
            )
        }

        composable<Destination.CityMap> { backStackEntry ->
            val city: Destination.CityMap = backStackEntry.toRoute()
            CityMapScreen(
                city = City(
                    city.id,
                    city.displayName,
                    Coordinates(
                        longitude = city.longitude,
                        latitude = city.latitude
                    ),
                    city.isFavourite
                ),
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
