package com.julienquievreux.randomuser.extensions

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.julienquievreux.randomuser.R

fun NavController.setStartDestination() {
    val navGraph: NavGraph = this.navInflater.inflate(R.navigation.main_graph)
    navGraph.setStartDestination(R.id.main_destination)
    this.graph = navGraph
}