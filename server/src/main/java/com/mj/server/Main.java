package com.mj.server;

/**
 * @author meijun  on 2023/4/15.
 */

class Main {
   public static void main(String[] args) {
      new Thread(new Runnable() {
         @Override
         public void run() {
            System.out.println("启动设备检测");
            DeviceDetector deviceDetector = new DeviceDetector();
            deviceDetector.detectDeviceOnLoop();

         }
      }).start();
      System.out.println("启动服务器");
      new Server().start();
   }
}
