package com.arcade;

import java.io.*;
import java.util.*;

public class LanzadorPuntuacionPaso5 {
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
        File errores = new File("errores.log");
        File salida = new File("salida.txt");
        pb.redirectError(errores);
        pb.redirectOutput(salida);

        Process proc = pb.start();
        
        int exit = proc.waitFor();
        if (exit == 0) {
            System.out.println("Resultado escrito en salida.txt");
        } else {
            System.out.println("Se han producido errores. Revise errores.log");
        }
    }
}
