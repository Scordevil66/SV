/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.clases;

/**
 *
 * @author Gustavo
 */
public class ruta {
    
    private static String rutas;
    
    public static String consultarRuta(){
    rutas = "http://107.180.72.83/SalaVirtualService/webresources";
//    
//    rutas = "http://localhost:8084/SalaVirtualService/webresources";
    
    return rutas;
    }

    public String getRutas() {
        return rutas;
    }

    public void setRutas(String rutas) {
        this.rutas = rutas;
    }
    
    
    
}
