// Bernard Allotey 18-10-20

package com.thesaulator;

import com.thesaulator.io.ApiHandler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter term here: ");
        ApiHandler.lookup(scanner.nextLine());
    }
}
