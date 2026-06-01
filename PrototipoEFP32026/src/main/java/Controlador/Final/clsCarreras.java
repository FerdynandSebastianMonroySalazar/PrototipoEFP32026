//Ferdynand Monroy junio 01 del 2026
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Final;

/**
 *
 * @author ferito
 */
public class clsCarreras {
    
    private int codigoCarrera; 
private String nombreCarrera;
private String codigoFacultad;
private String estatusCarrera;

//constructor vacio
    public clsCarreras(){
    }

    //constructor con parametros
    public clsCarreras(int codigoCarrera, String nombreCarrera, String codigoFacultad, String estatusCarrera) {
        this.codigoCarrera = codigoCarrera;
        this.nombreCarrera = nombreCarrera;
        this.codigoFacultad = codigoFacultad;
        this.estatusCarrera = estatusCarrera;
    }

    //getters y setters
    public int getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(int codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public String getEstatusCarrera() {
        return estatusCarrera;
    }

    public void setEstatusCarrera(String estatusCarrera) {
        this.estatusCarrera = estatusCarrera;
    }

    //to string
    @Override
    public String toString() {
        return "clsCarreras{" + "codigoCarrera=" + codigoCarrera + ", nombreCarrera=" + nombreCarrera + ", codigoFacultad=" + codigoFacultad + ", estatusCarrera=" + estatusCarrera + '}';
    }
    
}
