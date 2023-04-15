package com.mj.core

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

/**
 * @author meijun  on 2023/4/15.
 */
private const val TAG = "PhoneConnection"
class PhoneConnection : Connection() {
    override var isConnecting: Boolean= false
    override var isConnected: Boolean = false


    override fun connectServer() {
        if (isConnecting) {
            Log.i(TAG, "current is connecting")
            return ;
        }
        if (isConnected) {
            return
        }
        connectServer()
    }


    private fun connect() {
        //待优化，改为协程处理
        // 连接到电脑端口上的TCP服务器
        // 连接到电脑端口上的TCP服务器
        Thread {
            Log.e(TAG, "启动线程")
            realConnect()
        }.start()
    }

    private fun realConnect() {
        if (isConnecting) {
            return
        }
        isConnecting = true;
        var remainTimes = 10
        while (!isConnected && remainTimes > 0) {
            try {
                Socket("localhost", 3333).use { socket ->
                    isConnected =true;
                    // 向服务器发送字符串消息
                    val out = PrintWriter(socket.getOutputStream(), true)
                    val message = "Hello, World!"
                    out.println(message)
                    Log.e(TAG, "Sent message to server: $message")

                    // 从服务器接收字符串消息并打印出来
                    val reader = BufferedReader(
                        InputStreamReader(socket.getInputStream())
                    )
                    val response: String = reader.readLine()
                    Log.e(TAG, "Received response from server: $response")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                remainTimes--
                isConnected =false
            }
        }
        isConnecting = false;
    }

}