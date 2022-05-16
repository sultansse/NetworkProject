package com.example.networkproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

class Quiz{
    public String Store1(int c , int d){
        return set[c][d];
    }
    public String Store2(int c){
        return temp[c] + ": ";
    }
    public String answer(int c){
        return ans[c] ;
    }
    public int max(){
        return fin;
    }

    String[] f = new String[20] ;
    File file ;
    String[] temp = new String[20];
    int count = 0;
    int fin ;
    Scanner s;
    String[] ans = new String[20];
    String[][]set = new String[20][8];

    public void loader(File f){
        this.file = f;
    }

    public void start () throws FileNotFoundException {
        s = new Scanner(file) ;
        while (s.hasNext()) {
            temp[count] = s.nextLine();
            set[count][0] = s.nextLine();
            set[count][1] = s.nextLine();
            ans[count] = set[count][0];
            if (set[1] != null && !Objects.equals(set[count][1], "")) {
                f[count] = "true";
                set[count][2] = s.nextLine();
                set[count][3] = s.nextLine();
                Random r = new Random();
                for (int i = 3; i > 0; i--) {
                    int j = r.nextInt(i+1);
                    String temp = set[count][i];
                    set[count][i] = set[count][j];
                    set[count][j] = temp;
                }
                s.nextLine();
            } else {
                f[count] = "false";
            }
            count++;
        }
        fin = count;

    }
}