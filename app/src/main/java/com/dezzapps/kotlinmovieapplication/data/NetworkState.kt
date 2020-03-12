package com.dezzapps.kotlinmovieapplication.data

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,

}

class NetworkState (val status: Status, val msg: String) {

    companion object{
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success (Exitoso)")
            LOADING = NetworkState(Status.RUNNING, "Running (Corriendo)")
            ERROR = NetworkState(Status.FAILED, "Something went wrong (Algo paso)")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end (La busqueda termino)" )

        }
    }



}