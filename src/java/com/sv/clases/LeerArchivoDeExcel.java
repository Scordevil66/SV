/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.clases;

import com.sv.controladores.UsuarioCT;
import com.sv.dao.CiudadDao;
import com.sv.dao.CorreoDao;
import com.sv.dao.DepartamentoDao;
import com.sv.dao.InventarioDao;
import com.sv.dao.PedidoDao;
import com.sv.dao.UsuarioDao;
import com.sv.modelos.Ciudad;
import com.sv.modelos.Departamento;
import com.sv.modelos.Empresa;
import com.sv.modelos.Inventario;
import com.sv.modelos.Pedido;
import com.sv.modelos.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.read.biff.BiffException;

/**
 *
 * @author Gustavo Cardenas
 */
public class LeerArchivoDeExcel {

    static Usuario usuario = new Usuario();
    static Usuario temp = new Usuario();
    static Ciudad tempC = new Ciudad();
    static Pedido tempP = new Pedido();
    static Departamento tempD = new Departamento();
    static Pedido pedido = new Pedido();
    static Inventario inventario = new Inventario();
    static UsuarioCT us = new UsuarioCT();

    public static void main(String[] args) throws IOException, BiffException {
//
//        String a = "C:\\Users\\Gustavo\\Downloads\\Colpatria - Base Beneficiarios el Faro1.xls";
//
//        registrarUsuarioYPedido(a, 7);
    }

    public static void registrarUsuarioYPedido(String path, int idEmpresa, int envioCorreo) throws IOException, BiffException {

        boolean equals1 = false, equals2 = false, equals3 = false, equals4 = false, equals5 = false, equals6 = false, equals7 = false, equals8 = false, equals9 = false, equals10 = false, equals11 = false, equals12 = false, equals13 = false, equals14 = false, equals15 = false, equals16 = false, equals17 = false, equals18 = false, equals19 = false, equals20 = false;

        File file = new File(path);
        if (file.exists() == true) {

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setEncoding("ISO-8859-1");
            wbSettings.setLocale(new Locale("es", "ES"));
            wbSettings.setExcelDisplayLanguage("ES");
            wbSettings.setExcelRegionalSettings("ES");
            wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());

            Workbook workbook = Workbook.getWorkbook(new File(path), wbSettings); //Pasamos el excel que vamos a leer
            Sheet sheet = workbook.getSheet(0); //Seleccionamos la hoja que vamos a leer
            String nombre = "", cedula = "", departamento = "", ciudad = "", oficina = "", area = "", telefono = "", email = "", hijo = "", sexo = "", nombreE = "", ciudadE = "", emailE = "", telefonoE = "", fechaE = "", horaE = "", direccionE = "";
            String contrasena = "", user = "";
            int codigo = 0, edad = 0;

            //recorrer nombre de las columnas
            for (int fil = 0; fil < 1; fil++) {
                equals1 = sheet.getCell(0, fil).getContents().equals("NOMBRE COLABORADOR");
                equals2 = sheet.getCell(1, fil).getContents().equals("CODIGO COLABORADOR");
                equals3 = sheet.getCell(2, fil).getContents().equals("CEDULA");
                equals4 = sheet.getCell(3, fil).getContents().equals("DEPARTAMENTO");
                equals5 = sheet.getCell(4, fil).getContents().equals("CIUDAD");
                equals6 = sheet.getCell(5, fil).getContents().equals("OFICINA");
                equals7 = sheet.getCell(6, fil).getContents().equals("AREA DE TRABAJO");
                equals8 = sheet.getCell(7, fil).getContents().equals("TELEFONO");
                equals9 = sheet.getCell(8, fil).getContents().equals("EMAIL");
                equals10 = sheet.getCell(9, fil).getContents().equals("NOMBRE HIJO");
                equals11 = sheet.getCell(10, fil).getContents().equals("EDAD HIJO");
                equals12 = sheet.getCell(11, fil).getContents().equals("SEXO HIJO");
                equals13 = sheet.getCell(12, fil).getContents().equals("NOMBRE ENCARGADO");
                equals14 = sheet.getCell(13, fil).getContents().equals("CIUDAD ENCARGADO");
                equals15 = sheet.getCell(14, fil).getContents().equals("EMAIL ENCARGADO");
                equals16 = sheet.getCell(15, fil).getContents().equals("TELEFONO ENCARGADO");
                equals17 = sheet.getCell(16, fil).getContents().equals("FECHA ENTREGA");
                equals18 = sheet.getCell(17, fil).getContents().equals("HORA ENTREGA");
                equals19 = sheet.getCell(18, fil).getContents().equals("DIRECCION DE ENTREGA");
                equals20 = sheet.getCell(19, fil).getContents().equals("USUARIO");
            }
            //Validar la estructura
            if (equals1 == true && equals2 == true && equals3 == true && equals4 == true
                    && equals5 == true && equals6 == true && equals7 == true && equals8 == true
                    && equals9 == true && equals10 == true && equals11 == true && equals12 == true
                    && equals13 == true && equals14 == true && equals15 == true && equals16 == true
                    && equals17 == true && equals18 == true && equals19 == true && equals20 == true) {

                for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas

                    nombre = sheet.getCell(0, fila).getContents(); //setear la celda leida a nombre
                    if (!(sheet.getCell(1, fila).getContents().equals(""))) {
                        codigo = Integer.parseInt(sheet.getCell(1, fila).getContents());
                    }
                    cedula = sheet.getCell(2, fila).getContents();
                    departamento = sheet.getCell(3, fila).getContents();
                    ciudad = sheet.getCell(4, fila).getContents();
                    oficina = sheet.getCell(5, fila).getContents();
                    area = sheet.getCell(6, fila).getContents();
                    telefono = sheet.getCell(7, fila).getContents();
                    email = sheet.getCell(8, fila).getContents();
                    hijo = sheet.getCell(9, fila).getContents();
                    if (sheet.getCell(10, fila).getContents().trim().equals("") || sheet.getCell(10, fila).getContents().trim().equals("0")) {
                        edad = 0;
                    } else {
                        edad = Integer.parseInt(sheet.getCell(10, fila).getContents());
                    }
                    sexo = sheet.getCell(11, fila).getContents();
                    nombreE = sheet.getCell(12, fila).getContents();
                    ciudadE = sheet.getCell(13, fila).getContents();
                    emailE = sheet.getCell(14, fila).getContents();
                    telefonoE = sheet.getCell(15, fila).getContents();
                    fechaE = sheet.getCell(16, fila).getContents();
                    horaE = sheet.getCell(17, fila).getContents();
                    direccionE = sheet.getCell(18, fila).getContents();
                    user = sheet.getCell(19, fila).getContents();
                    contrasena = sheet.getCell(2, fila).getContents();

                    usuario.setNombre(nombre.trim());
                    usuario.setAreaTrabajo(area.trim());
                    usuario.setCc(cedula.trim());
                    usuario.setCodigoEmpleado(codigo);
                    usuario.setContrasena(contrasena.trim());
                    usuario.setUsuario(user.trim());
                    usuario.setTelefono(telefono.trim());
                    usuario.setOficina(oficina.trim());
                    usuario.setEmail(email.trim());
                    usuario.getIdCiudad().setNombre(ciudad);
//                    usuario.getIdCiudad().setIdCiudad(2);
                    usuario.getIdTipoUsuario().setIdTipoUsuario(4);
                    usuario.getIdEmpresa().setIdEmpresa(idEmpresa);

                    pedido.setNombreHijo(hijo.trim());
                    pedido.setSexoHijo(sexo.trim());
                    pedido.setEdadHijo(edad);
                    pedido.setIdInventario(0);

                    UsuarioDao usuarioDAO = new UsuarioDao();
                    PedidoDao pedidoDAO = new PedidoDao();
                    CorreoDao correoDAO = new CorreoDao();
                    DepartamentoDao departamentoDao = new DepartamentoDao();
                    CiudadDao ciudadDao = new CiudadDao();

                    Departamento depa = new Departamento();
                    depa.setNombre(departamento);
                    depa.setDescripcion(departamento);

                    Ciudad ci = new Ciudad();
                    ci.setNombre(ciudad);
                    ci.setDescripcion(ciudad);
                    ci.setIdCiudad(0);

                    //registrar departamentos
                    if (!(departamento.equals(""))) {
                        tempD = null;
                        tempD = new Departamento();
                        tempD = departamentoDao.consultarDepartamento(depa);
                        if (tempD.getIdDepartamento() > 0) {
                            usuario.setIdDepartamento(tempD);
                            ci.setIdDepartamento(tempD);
                        } else {
                            departamentoDao.registrarDepartamento(depa);
                            tempD = departamentoDao.consultarDepartamento(depa);
                            usuario.setIdDepartamento(tempD);
                            ci.setIdDepartamento(tempD);
                        }
                    }

                    //registrar ciudades
                    tempC = null;
                    tempC = new Ciudad();
                    tempC = ciudadDao.consultarCiudad(ci);
                    if (tempC.getIdCiudad() > 0) {
                        usuario.setIdCiudad(tempC);
                    } else {
                        ciudadDao.registrarCiudad(ci);
                        tempC = ciudadDao.consultarCiudad(ci);
                        usuario.setIdCiudad(tempC);
                    }

                    //Registrar Usuario y Pedido
                    temp = null;
                    temp = new Usuario();
                    tempP = null;
                    tempP = new Pedido();
                    temp = usuarioDAO.consultarUsuarioPorCC(usuario.getCc());
                    if (temp.getIdUsuario() > 0) {
                        List<Pedido> ps = new ArrayList<>();
                        ps = pedidoDAO.ConsultarExistenciaPedido(temp.getIdUsuario(), pedido.getNombreHijo(), pedido.getEdadHijo());
                        if (!(ps.size() > 0)) {
                            pedidoDAO.registrarPedido(temp, pedido);
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Registro realizado satisfactoriamente!", "");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        } else {
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Se intentar registrar información ya existente!", "");
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        }

                    } else {
                        usuarioDAO.registrarUsuario(usuario);
                        temp = usuarioDAO.consultarUsuarioPorCC(usuario.getCc());
                        pedidoDAO.registrarPedido(temp, pedido);
                        if (envioCorreo > 0) {
                            correoDAO.EnviarCorreoInicioSeleccionDeJuguete(usuario);
                        }

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Registro realizado satisfactoriamente!", "");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }

                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " La estructura es incorrecta!", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Archivo no encontrado!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public static void registrarMasivaInventario(String path, int idEmpresa) throws IOException, BiffException {

        boolean equals1 = false, equals2 = false, equals3 = false, equals4 = false, equals5 = false, equals6 = false, equals7 = false, equals8 = false, equals9 = false, equals10 = false, equals11 = false, equals12 = false, equals13 = false, equals14 = false, equals15 = false, equals16 = false, equals17 = false, equals18 = false, equals19 = false, equals20 = false;
        File file = new File(path);
        if (file.exists() == true) {

            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setEncoding("ISO-8859-1");
            wbSettings.setLocale(new Locale("es", "ES"));
            wbSettings.setExcelDisplayLanguage("ES");
            wbSettings.setExcelRegionalSettings("ES");
            wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());

            Workbook workbook = Workbook.getWorkbook(new File(path), wbSettings); //Pasamos el excel que vamos a leer
            Sheet sheet = workbook.getSheet(0); //Seleccionamos la hoja que vamos a leer
            String sku = "", nombre = "", descripcion = "", genero = "", url1 = "", url2 = "", url3 = "", url4 = "", url5 = "", url6 = "", url7 = "", url8 = "", url9 = "", url10 = "", url11 = "", url12 = "", observacion = "";
            int cantidad = 0, rangoD = 0, rangoH = 0;

            for (int fil = 0; fil < 1; fil++) {
                equals1 = sheet.getCell(0, fil).getContents().equals("SKU");
                equals2 = sheet.getCell(1, fil).getContents().equals("NOMBRE");
                equals3 = sheet.getCell(2, fil).getContents().equals("DESCRIPCION");
                equals4 = sheet.getCell(3, fil).getContents().equals("RANGO DESDE");
                equals5 = sheet.getCell(4, fil).getContents().equals("RANGO HASTA");
                equals6 = sheet.getCell(5, fil).getContents().equals("GENERO");
                equals7 = sheet.getCell(6, fil).getContents().equals("URL IMAGEN 1");
                equals8 = sheet.getCell(7, fil).getContents().equals("URL IMAGEN 2");
                equals9 = sheet.getCell(8, fil).getContents().equals("URL IMAGEN 3");
                equals10 = sheet.getCell(9, fil).getContents().equals("URL IMAGEN 4");
                equals11 = sheet.getCell(10, fil).getContents().equals("URL IMAGEN 5");
                equals12 = sheet.getCell(11, fil).getContents().equals("URL IMAGEN 6");
                equals13 = sheet.getCell(12, fil).getContents().equals("URL IMAGEN 7");
                equals14 = sheet.getCell(13, fil).getContents().equals("URL IMAGEN 8");
                equals15 = sheet.getCell(14, fil).getContents().equals("URL IMAGEN 9");
                equals16 = sheet.getCell(15, fil).getContents().equals("URL IMAGEN 10");
                equals17 = sheet.getCell(16, fil).getContents().equals("URL IMAGEN 11");
                equals18 = sheet.getCell(17, fil).getContents().equals("URL IMAGEN 12");
                equals19 = sheet.getCell(18, fil).getContents().equals("CANT");
                equals20 = sheet.getCell(19, fil).getContents().equals("OBSERVACION");
            }

            if (equals1 == true && equals2 == true && equals3 == true && equals4 == true
                    && equals5 == true && equals6 == true && equals7 == true && equals8 == true
                    && equals9 == true && equals10 == true && equals11 == true && equals12 == true
                    && equals13 == true && equals14 == true && equals15 == true && equals16 == true
                    && equals17 == true && equals18 == true && equals19 == true && equals20 == true) {

                for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas

                    sku = sheet.getCell(0, fila).getContents(); //setear la celda leida a nombre
                    nombre = sheet.getCell(1, fila).getContents();
                    descripcion = sheet.getCell(2, fila).getContents();
                    if (sheet.getCell(10, fila).getContents().trim().equals("") || sheet.getCell(10, fila).getContents().trim().equals("0")) {
                        rangoD = 0;
                    } else {
                        rangoD = Integer.parseInt(sheet.getCell(3, fila).getContents());
                    }
                    if (sheet.getCell(10, fila).getContents().trim().equals("") || sheet.getCell(10, fila).getContents().trim().equals("0")) {
                        rangoH = 0;
                    } else {
                        rangoH = Integer.parseInt(sheet.getCell(4, fila).getContents());
                    }
                    genero = sheet.getCell(5, fila).getContents();
                    url1 = sheet.getCell(6, fila).getContents();
                    url2 = sheet.getCell(7, fila).getContents();
                    url3 = sheet.getCell(8, fila).getContents();
                    url4 = sheet.getCell(9, fila).getContents();
                    url5 = sheet.getCell(10, fila).getContents();
                    url6 = sheet.getCell(11, fila).getContents();
                    url7 = sheet.getCell(12, fila).getContents();
                    url8 = sheet.getCell(13, fila).getContents();
                    url9 = sheet.getCell(14, fila).getContents();
                    url10 = sheet.getCell(15, fila).getContents();
                    url11 = sheet.getCell(16, fila).getContents();
                    url12 = sheet.getCell(17, fila).getContents();
                    if (!(sheet.getCell(18, fila).getContents().equals(""))) {
                        cantidad = Integer.parseInt(sheet.getCell(18, fila).getContents());
                    }
                    observacion = sheet.getCell(19, fila).getContents();
                    InventarioDao inventarioDao = new InventarioDao();
                    List<Inventario> inv = new ArrayList<>();
                    inv = inventarioDao.ConsultarJuguetesPorEmpresaCodigo(new Empresa(idEmpresa), sku.trim());
                    if (!(inv.size() > 0)) {

                        inventario.setNombre(nombre.trim());
                        inventario.setCodigo(sku.trim());
                        inventario.setDescripcion(descripcion.trim());
                        inventario.setEdadDesde(rangoD);
                        inventario.setEdadHasta(rangoH);
                        if (genero.trim().equals("Unisex")) {
                            inventario.setGenero("A");
                        } else {
                            inventario.setGenero(genero);
                        }

                        inventario.setUrl1(url1.trim());
                        inventario.setUrl2(url2.trim());
                        inventario.setUrl3(url3.trim());
                        inventario.setUrl4(url4.trim());
                        inventario.setUrl5(url5.trim());
                        inventario.setUrl6(url6.trim());
                        inventario.setUrl7(url7.trim());
                        inventario.setUrl8(url8.trim());
                        inventario.setUrl9(url9.trim());
                        inventario.setUrl10(url10.trim());
                        inventario.setUrl11(url11.trim());
                        inventario.setUrl12(url12.trim());
                        inventario.setCantidad(cantidad);
                        inventario.setObservacion(observacion.trim());
                        inventario.setIdEmpresa(new Empresa(idEmpresa));

                        InventarioDao inventarioDAO = new InventarioDao();

                        inventarioDAO.registrarJuguete(inventario);

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se Registro Satisfactoriamente el " + nombre.trim() + " !", "");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se Intento registrar Articulos ya existentes!", "");
                        FacesContext.getCurrentInstance().addMessage(null, message);

                    }
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " La estructura es incorrecta!", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Archivo no encontrado!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}
