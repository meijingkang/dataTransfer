package com.mj.core

/**
 * @author meijun  on 2023/4/15.
 */
abstract class Connection {
    abstract var isConnecting :Boolean
    abstract var isConnected :Boolean
    abstract fun connectServer();
}