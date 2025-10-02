package com.arcade;

public class CalculadoraPuntuacion {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Error: se esperan 3 enteros no negativos (kills, coins, time).");
            System.exit(1);
        }
        int kills, coins, time;
        try {
            kills = Integer.parseInt(args[0]);
            coins = Integer.parseInt(args[1]);
            time = Integer.parseInt(args[2]);
        } catch (NumberFormatException nfe) {
            System.err.println("Error: los argumentos deben ser enteros.");
            System.exit(1);
            return;
        }
        if (kills < 0 || coins < 0 || time < 0) {
            System.err.println("Error: los argumentos no pueden ser negativos.");
            System.exit(1);
            return;
        }
        long score = kills * 100L + coins * 10L - time * 2L;
        System.out.println("PUNTUACION: " + score);
    }
}
