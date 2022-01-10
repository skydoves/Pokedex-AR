package com.skydoves.pokedexar.ui.room

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            // [무선 LAN 어댑터 Wi-Fi] - IPv4 주소
            // ERROR! Wifi ivp4 주소가 계속 바뀐다!!!!
            val uri = URI.create("http://192.249.30.125:80") // Wifi
//            val uri = URI.create("http://192.249.18.196:80") // KCloud
            val opt = IO.Options.builder().build()
            mSocket = IO.socket(uri, opt)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun getSocket() : Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}