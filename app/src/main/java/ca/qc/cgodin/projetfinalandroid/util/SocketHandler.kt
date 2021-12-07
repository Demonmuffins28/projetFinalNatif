package ca.qc.cgodin.projetfinalandroid.util

import io.socket.client.Socket
import io.socket.client.IO
import io.socket.emitter.Emitter
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(Constants.BASE_URL)
        } catch (e: URISyntaxException) {}
    }

    @Synchronized
    fun getSocket(): Socket {
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