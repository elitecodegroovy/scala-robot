package com.ai.common

import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}

/**
 * @author john.grails@gmail.com
 * @since  2015/11/28..
 */
class NetworkService(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run() {
    println(""+ System.currentTimeMillis()+ "--" + Thread.currentThread().getName())
    try {
      while (true) {
        // This will block until a connection comes in.
        val socket = serverSocket.accept()
        println("serverSocket.accept():"+ System.currentTimeMillis()+ "--" + Thread.currentThread().getName())
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run() {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}
