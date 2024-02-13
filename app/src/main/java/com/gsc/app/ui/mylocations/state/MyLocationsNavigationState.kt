package com.gsc.app.ui.mylocations.state

sealed class MyLocationsNavigationState {
    data class MoveToMyLocationDirections(val locationId: Int) : MyLocationsNavigationState()
}