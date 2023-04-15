package com.mj.server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

/**
 * @author meijun  on 2023/4/15.
 */
internal class Server {


    /**
     * 启动服务器
     */
    fun start() {
// 启动服务器并监听指定端口
        // 启动服务器并监听指定端口
        try {
            ServerSocket(1234).use { server ->
                println("Server started...")

                // 等待客户端连接并处理客户端发来的字符串消息
                while (true) {
                    val client: Socket = server.accept()
                    System.out.println("Client connected: " + client.getInetAddress())
                    val reader = BufferedReader(
                        InputStreamReader(client.getInputStream())
                    )
                    val message: String = reader.readLine()
                    println("Received message from client: $message")
                    val out = PrintWriter(client.getOutputStream(), true)
                    out.println("Response from server")
                    println("Sent response to client")
                    client.close()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}