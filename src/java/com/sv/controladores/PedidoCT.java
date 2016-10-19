/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.controladores;

import com.sv.clases.Sesion;
import com.sv.dao.CorreoDao;
import com.sv.dao.InventarioDao;
import com.sv.dao.PedidoDao;
import com.sv.dao.UsuarioDao;
import com.sv.modelos.Inventario;
import com.sv.modelos.Pedido;
import com.sv.modelos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Gustavo Cardenas
 */
public class PedidoCT {

    private Pedido pedido;
    private List<Pedido> pedidos;
    private String informacion;

    public PedidoCT() {
        pedido = new Pedido();
        pedidos = new ArrayList<>();
        informacion = "";
    }

    @PostConstruct
    public void init() {
        PedidoDao pedidoDao = new PedidoDao();
        //   pedido.getIdUsuario().setIdUsuario(3);

        pedidos = pedidoDao.ConsultarPedidoPorId(Sesion.obtenerSesion().getIdUsuario());

        if (pedidos.isEmpty()) {

            quitarAccesos();
        }

    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

//        //Metodos
//    public void registrar(){
//        PedidoDao pedidoDao = new PedidoDao();
//        pedido.setIdUsuario(new Usuario(3));
//        usuarioDao.registrarUsuario(usuario);
//        
//        usuario = new Usuario();
//        usuarios = usuarioDao.consultarUsuarios();
//        
//    }
    public void consultarPedido(int idPedido) {
        PedidoDao pedidoDao = new PedidoDao();
        pedido = pedidoDao.ConsultarPedido(idPedido);
    }

    public String editarPedido(int idUsuario, int idPedido, int idInventario, String codigo, String anio, String nombre) {
        String link = "";
        int valor = 0;
        InventarioDao inventarioDao = new InventarioDao();
        PedidoDao pedidoDao = new PedidoDao();
        Pedido ped = new Pedido();
        ped.setIdInventario(idInventario);
        ped.setIdPedido(idPedido);
        ped.setTicket(idUsuario + codigo + idPedido + anio);
        valor = pedidoDao.EditarPedido(ped);

        if (valor == 1) {

            inventarioDao.actualizarInventario(idInventario, 1);

            CorreoDao correoDao = new CorreoDao();
            
            Inventario juguete = new Inventario();
            juguete.setIdInventario(idInventario);
            juguete.setCodigo(codigo);
            juguete.setNombre(nombre);
            
            Usuario usuario = new Usuario();
            usuario.setNombre(Sesion.obtenerSesion().getNombre());
            usuario.setUsuario(Sesion.obtenerSesion().getUsuario());
            usuario.setEmail(Sesion.obtenerSesion().getEmail());
            pedido.setTicket(ped.getTicket());
            correoDao.EnviarConfirmacionSeleccion(usuario, pedido, juguete);

            Sesion.cerrarHttpSesion();

            link = "Login";
        }

        return link;

    }

    public void quitarAccesos() {

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.quitarAcceso(Sesion.obtenerSesion().getIdUsuario());
//        
        informacion = "Ya usted a seleccionado lo(s) Articulo para su(s) hijo(s). Por favor cierre Sesion";

    }

}
