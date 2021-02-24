package com.ksw.gomovie.network

/**
 * Created by KSW on 2021-02-23
 */
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
}

class NetworkState(val status: Status, val message: String) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "성공")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "로딩중")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "실패")
        val END: NetworkState = NetworkState(Status.FAILED, "데이터의 끝입니다.")

    }
}