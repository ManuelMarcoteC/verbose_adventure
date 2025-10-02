package com.arcade;

import java.io.*;
import java.util.*;

public class LanzadorPuntuacionPaso3 {
    public static void main(String[] args) throws Exception {
        File f = new File("entrada.txt");
        if (!f.exists()) {
            System.err.println("No se encontró entrada.txt en el directorio de trabajo: " + new File(".").getAbsolutePath());
            System.exit(1);
        }
        String kills, coins, time;
        try (Scanner sc = new Scanner(f)) {
            if (!sc.hasNext()) { System.err.println("entrada.txt vacío"); System.exit(1); return; }
            kills = sc.next();
            if (!sc.hasNext()) { System.err.println("Faltan valores en entrada.txt"); System.exit(1); return; }
            coins = sc.next();
            if (!sc.hasNext()) { System.err.println("Faltan valores en entrada.txt"); System.exit(1); return; }
            time = sc.next();
        }

        String classpath = System.getProperty("java.class.path");
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";

        ProcessBuilder pb = new ProcessBuilder( 
        	    javaBin,
        	    "-cp",
        	    new File("bin").getAbsolutePath(),   
        	    "com.arcade.CalculadoraPuntuacion",
        	    kills, coins, time
        	);
        
        Process proc = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exit = proc.waitFor();
        if (exit != 0) {
            try (BufferedReader brErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                String line;
                while ((line = brErr.readLine()) != null) {
                    System.err.println(line);
                }
            }
            System.out.println("El proceso terminó con código: " + exit);
        }

    }
}
