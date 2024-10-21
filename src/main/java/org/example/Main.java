package org.example;

import java.util.Arrays;

public class Main {


    public static int mochila(int[] pesos, int[] valores, int capacidad, int n) {
        if (n == 0 || capacidad == 0) {
            return 0;
        }

        if (pesos[n - 1] > capacidad) {
            return mochila(pesos, valores, capacidad, n - 1);
        }

        int opcion1 = mochila(pesos, valores, capacidad, n - 1);

        int opcion2 = valores[n - 1] + mochila(pesos, valores, capacidad - pesos[n - 1], n - 1);

        return Math.max(opcion1, opcion2);
    }

    private static final int INF = Integer.MAX_VALUE;


    public static int viajero(int[][] distancias, int ciudadActual, int visitadas, int n, int[][] memo) {

        if (visitadas == (1 << n) - 1) {
            return distancias[ciudadActual][0];
        }
        if (memo[ciudadActual][visitadas] != -1) {
            return memo[ciudadActual][visitadas];
        }

        int distanciaMinima = INF;

        for (int ciudad = 0; ciudad < n; ciudad++) {
            if ((visitadas & (1 << ciudad)) == 0) {
                int nuevasVisitadas = visitadas | (1 << ciudad);
                int distanciaActual = distancias[ciudadActual][ciudad] + viajero(distancias, ciudad, nuevasVisitadas, n, memo);
                distanciaMinima = Math.min(distanciaMinima, distanciaActual);
            }
        }

        memo[ciudadActual][visitadas] = distanciaMinima;

        return distanciaMinima;
    }

    public static int productoMaximo(int[] arreglo, int inicio, int fin) {
        if (inicio == fin) {
            return arreglo[inicio];
        }

        int mitad = (inicio + fin) / 2;
        int productoIzquierda = productoMaximo(arreglo, inicio, mitad);
        int productoDerecha = productoMaximo(arreglo, mitad + 1, fin);
        int productoCruzado = productoMaximoCruzado(arreglo, inicio, mitad, fin);

        return Math.max(productoIzquierda, Math.max(productoDerecha, productoCruzado));
    }

    public static int productoMaximoCruzado(int[] arreglo, int inicio, int mitad, int fin) {
        int productoIzquierda = Integer.MIN_VALUE;
        int productoActual = 1;

        for (int i = mitad; i >= inicio; i--) {
            productoActual *= arreglo[i];
            productoIzquierda = Math.max(productoIzquierda, productoActual);
        }

        int productoDerecha = Integer.MIN_VALUE;
        productoActual = 1;

        for (int i = mitad + 1; i <= fin; i++) {
            productoActual *= arreglo[i];
            productoDerecha = Math.max(productoDerecha, productoActual);
        }

        return productoIzquierda * productoDerecha;
    }

    public static void main(String[] args) {
        //Problema de la mochila
        int[] pesos = {1, 2, 3, 5};
        int[] valores = {60, 100, 120, 80};
        int capacidad = 7;
        int n = pesos.length;
        int valorMaximo = mochila(pesos, valores, capacidad, n);

        System.out.println("El valor máximo que se puede obtener es: " + valorMaximo);


        //Problema del viajero
        int[][] distancias = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        int m = distancias.length;

        int[][] memoria = new int[m][(1 << m)];

        for (int[] fila : memoria) {
            Arrays.fill(fila, -1);
        }

        int resultado = viajero(distancias, 0, 1, n, memoria);

        System.out.println("La menor distancia para recorrer todas las ciudades es: " + resultado);


        //Producto maximo en un arreglo
        int[] arreglo = {2, 3, 5, 4, 1};
        int result = productoMaximo(arreglo, 0, arreglo.length - 1);
        System.out.println("El producto máximo en el arreglo es: " + result);



    }


}