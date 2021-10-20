/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

/**
 *
 * @author vitya
 */
public class PingPong implements Runnable{
private String name;
private PingPong ball;
private int delay;
    public PingPong() { }
    public PingPong (String name, PingPong ball, int delay) {
    this.name = name;
    this.ball= ball;
    this.delay= delay;
    }
    public static void main(String[] args) {
        System.out.println("Start game...");
        PingPong ball = new PingPong();
        PingPong ping = new PingPong("ping", ball, 200);
        PingPong pong = new PingPong ("pong",ball, 500);
        Thread t1 = new Thread (ping, ping.name);
        Thread t2 = new Thread (pong, pong.name);
        t1.start();
        t2.start();
        try {
        t1.join(7500);
        t2.join(7500);
        synchronized (ball){
        ball.notify();
        }
        } catch (InterruptedException e) {
            System.out.println("Interrupted#1 : " + e.getMessage());
        }
        System.out.println("\nGame over.");
      
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; ++i){
             ball.hit(Thread.currentThread(), delay);
           try {
                   Thread.sleep(delay);
           } catch (InterruptedException e) {
               System.out.println("Interrupted #2: " + e.getMessage());
           }
        }
    }
    
    private synchronized void hit (Thread t, int time ) {
        System.out.println(t.getName() + " -> ");
        notify ();
      
        try {
              wait();
        Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}
