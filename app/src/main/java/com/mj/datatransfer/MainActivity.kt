package com.mj.datatransfer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.mj.datatransfer.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var isConnecting = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "启动数据连接", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            connectServer();
        }
    }

    private fun connectServer() {


        // 连接到电脑端口上的TCP服务器
        // 连接到电脑端口上的TCP服务器
        Thread {
            Log.e(TAG, "启动线程")
            connect()
        }.start()
    }

    private fun connect() {
        if (isConnecting) {
            return
        }
        isConnecting = true;
        var retry = false;
        var remainTimes = 10
        var connected = false;
        while (!connected && remainTimes > 0) {
            try {
                Socket("localhost", 3333).use { socket ->
                    connected =true;
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
                connected =false
            }
        }
        isConnecting = false;
    }


}