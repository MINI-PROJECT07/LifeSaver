package com.example.emergencyalert

import com.example.emergencyalert.screens.useraccount.ProfileScreen
import com.example.emergencyalert.location.LocationViewModel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emergencyalert.accidents.AccidentViewModel
import com.example.emergencyalert.donors.DonorViewModel
import com.example.emergencyalert.hospitals.HospitalViewModel
import com.example.emergencyalert.screens.hospital.HospitalScreen
import com.example.emergencyalert.routes.Screens
import com.example.emergencyalert.screens.donors.DonorScreen
import com.example.emergencyalert.screens.home.HomeScreen
import com.example.emergencyalert.screens.useraccount.Contacts
//import com.example.emergencyalert.screens.useraccount.FirstAid
import com.example.emergencyalert.screens.useroperations.AddContactsForm
import com.example.emergencyalert.screens.useroperations.AddDonorInfo
import com.example.emergencyalert.screens.useroperations.AddMedInfo
import com.example.emergencyalert.screens.useroperations.EditContact
import com.example.emergencyalert.screens.useroperations.EditMedInfoForm
import com.example.emergencyalert.screens.useroperations.FirstAid
import com.example.emergencyalert.screens.useroperations.LoginScreen
import com.example.emergencyalert.screens.useroperations.SignUpScreen
import com.example.emergencyalert.sensor.SensorViewModel
import com.example.emergencyalert.ui.theme.MainColor
import com.example.emergencyalert.ui.theme.MainColor3
import com.example.emergencyalert.userauth.UserViewModel


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MyNavigation(navController: NavHostController, isLoggedIn: Boolean, context: Context) {
    val sensorviewModel = viewModel<SensorViewModel>()
    val hospitalViewModel = viewModel<HospitalViewModel>()
    val locationViewModel = viewModel<LocationViewModel>()
    val accidentViewModel = viewModel<AccidentViewModel>()
    val userViewModel = viewModel<UserViewModel>()
    val donorViewModel = viewModel<DonorViewModel>()
    NavHost(
        navController = navController,
        startDestination = if (!isLoggedIn) Screens.Login.route
        else Screens.Home.route,
    ) {
        composable(Screens.Home.route) {
            if (userViewModel.isMedInfoAdded.value && userViewModel.areContactsAdded.value) {
                HomeScreen(
                    navController,
                    context,
                    sensorviewModel,
                    locationViewModel,
                    accidentViewModel,
                    userViewModel
                )
            } else if (!userViewModel.isMedInfoAdded.value) {
                AddMedInfo(
                    context = context,
                    navController = navController,
                    userViewModel = userViewModel
                )
            } else if (!userViewModel.areContactsAdded.value) {
                AddContactsForm(userViewModel)
            }

        }
        composable(Screens.Login.route) {
            LoginScreen(navController = navController, context, userViewModel)
        }
        composable(Screens.SignUp.route) {
            SignUpScreen(
                navController = navController, context,
                userViewModel
            )
        }
        composable(Screens.Hospitals.route) {
            HospitalScreen(context = context, hospitalViewModel, locationViewModel)
        }
        composable(Screens.Profile.route) {
            ProfileScreen(context = context, userViewModel, navController)
        }
        composable(Screens.FirstAid.route) {
            FirstAid(context = context)
        }
        composable(Screens.Contacts.route) {
            Contacts(userViewModel = userViewModel)
        }
        composable(Screens.Donors.route) {
            DonorScreen(
                donorViewModel = donorViewModel,
                locationViewModel = locationViewModel
            )
        }
        composable(Screens.EditMedInfo.route) {
            EditMedInfoForm(userViewModel,navController)
        }
        composable(Screens.AddContacts.route) {
            AddContactsForm(userViewModel)
        }
        composable(Screens.EditContacts.route) {
            EditContact(userViewModel = userViewModel, navController = navController)
        }
        composable(Screens.AddBloodDonor.route) {
            AddDonorInfo(context = context,userViewModel = userViewModel, navController = navController)
        }
    }

}

@Composable
fun MyNavBar(navController: NavHostController) {
    var selectedItem by remember {
        mutableStateOf(2)
    }
    NavigationBar(

        containerColor = Color.White,
        modifier = Modifier
            .padding(5.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .border(
                width = 0.5.dp,
                color = MainColor3,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .shadow(
                elevation = 10.dp
            )
    ) {
        BottomNavItem().bottomNavItems().forEachIndexed { ind, navItem ->
            NavigationBarItem(selected = (selectedItem == ind),
                colors = NavigationBarItemColors(
                    selectedIconColor = MainColor,
                    selectedTextColor = MainColor,
                    selectedIndicatorColor = Color.White,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = NavigationBarItemDefaults.colors().disabledIconColor,
                    disabledTextColor = NavigationBarItemDefaults.colors().disabledTextColor,
                ),
                onClick = {
                    selectedItem = ind
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    selectedItem = ind
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                indication = rememberRipple(
                                    color = Color.White
                                )
                            ),

                        )
                },
                label = {
                    Text(text = navItem.title, textAlign = TextAlign.Center)
                }
            )
        }
    }
}

data class BottomNavItem(
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
    val title: String = ""
) {
    fun bottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                icon = Icons.Filled.AccountCircle,
                route = Screens.Profile.route,
                title = "Profile"
            ),
            BottomNavItem(
                icon = Icons.Filled.HomeWork,
                route = Screens.Hospitals.route,
                title = "Hospitals"
            ),
            BottomNavItem(
                icon = Icons.Filled.Home,
                route = Screens.Home.route,
                title = "Home"
            ),
            BottomNavItem(
                icon = Icons.Filled.Bloodtype,
                route = Screens.Donors.route,
                title = "Donors"
            ),
            BottomNavItem(
                icon = Icons.Filled.LocalHospital,
                route = Screens.FirstAid.route,
                title = "FirstAid"
            )
        )
    }
}