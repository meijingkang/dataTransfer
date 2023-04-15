package com.mj.server

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author meijun  on 2023/4/15.
 */
class DeviceDetector {
    fun detectDeviceOnLoop() {
        // 启动ADB守护进程
        val startAdbCommand = arrayOf("adb", "start-server")
        val processBuilder = ProcessBuilder(*startAdbCommand)
        var process = processBuilder.start()
        process.waitFor()
         // 查找已连接的设备

        // 查找已连接的设备
        val devicesCommand = arrayOf("adb", "devices")
        processBuilder.command(*devicesCommand)
        process = processBuilder.start()
        process.waitFor()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line = ""
        while (reader.readLine()?.also { line = it } != null) {
            if (line != null && line.endsWith("device")) {
                val device =
                    line.split("\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                // 在设备上启动TCP服务器并将其端口转发到电脑端口
                val forwardCommand = arrayOf("adb", "-s", device, "reverse", "tcp:3333", "tcp:1234")
                processBuilder.command(*forwardCommand)
                process = processBuilder.start()
                process.waitFor()
                println("Forwarded port from device: $line")
            }
        }
    }
}