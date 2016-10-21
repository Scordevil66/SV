/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.controladores;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sv.clases.LeerArchivoDeExcel;
import com.sv.clases.Sesion;
import com.sv.clases.Upload;
import com.sv.clases.UtilPath;
import com.sv.dao.ComiteDao;
import com.sv.dao.EmpresaDao;
import com.sv.dao.InventarioDao;
import com.sv.dao.PedidoDao;
import com.sv.dao.VotacionDao;
import com.sv.modelos.Ciudad;
import com.sv.modelos.Comite;
import com.sv.modelos.Empresa;
import com.sv.modelos.Inventario;
import com.sv.modelos.Pedido;
import com.sv.modelos.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jxl.read.biff.BiffException;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author CristianCamilo
 */
public class JugueteCT {

    private Inventario juguete;
    private Pedido pedido;
    private Usuario usuario;
    private Comite comite;
    private List<Inventario> inventarios;
    private List<Pedido> entregaArticulos;
    private List<Inventario> inventariosMasSeleccionados;
    private List<Inventario> inventariosSeleccionados;
    private List<Inventario> inventariosMasVotados;
    private List<Inventario> inventariosMasVotadosVerificacion;
    private List<Inventario> inventariosSinVotacionVerificacion;
    private List<Usuario> usuariosPorVotar;
    private List<String> images;
    private int vista;
    private int personasComite;
    private String buscar;
    private Empresa empresa;
    private UploadedFile excel;
    private Ciudad ciudad;
    private String fechaActual;
    private Date fechaActual2;
    private String anioActual;

    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private BarChartModel animatedModel3;
    private PieChartModel pieModel1;

    private UploadedFile arExcel;
    protected Upload archivo;
    private String archivoExcel;

    protected int operacion;
    private String nombreOperacion;
    private int calificacion;

    public JugueteCT() {
        juguete = new Inventario();
        inventarios = new ArrayList<>();
        inventariosMasSeleccionados = new ArrayList<>();
        inventariosSeleccionados = new ArrayList<>();
        inventariosMasVotados = new ArrayList<>();
        inventariosMasVotadosVerificacion = new ArrayList<>();
        inventariosSinVotacionVerificacion = new ArrayList<>();
        images = new ArrayList<>();
        vista = 0;
        buscar = "";
        nombreOperacion = "Registrar";
        operacion = 0;
        empresa = new Empresa();
        calificacion = 0;
        usuario = new Usuario();
        pedido = new Pedido();
        ciudad = new Ciudad();
        usuariosPorVotar = new ArrayList<>();
        personasComite = 0;
        entregaArticulos = new ArrayList<>();
        fechaActual = "";
        fechaActual2 = new Date();
        anioActual = "";
        comite = new Comite();
        archivo = new Upload();
    }

    @PostConstruct
    public void init() {

        consultarFecha();
        consultarAnio();

        InventarioDao inventarioDao = new InventarioDao();
        int idComite = 0;
        int valor = 0;
        ComiteDao comiteDao = new ComiteDao();
        idComite = comiteDao.consultarComitePorUsuario(Sesion.obtenerSesion().getIdUsuario());
        if (idComite > 0) {
            inventarios = inventarioDao.ConsultarJuguetesPorEmpresaComite(new Empresa(Sesion.obtenerSesion().getIdEmpresa().getIdEmpresa()), Sesion.obtenerSesion().getIdUsuario());

            comite = comiteDao.consultarComite(new Comite(idComite));
        } else {
//            inventarios = inventarioDao.ConsultarJuguetesPorEmpresa(new Empresa(Sesion.obtenerSesion().getIdEmpresa().getIdEmpresa()));
        }
        inventariosMasSeleccionados = new ArrayList<>();
        inventariosSeleccionados = new ArrayList<>();
        usuariosPorVotar = new ArrayList<>();
        entregaArticulos = new ArrayList<>();
        inventariosMasVotadosVerificacion = new ArrayList<>();
        inventariosSinVotacionVerificacion = new ArrayList<>();
        //-----------------------
        try {
            createAnimatedModels();
        } catch (IOException ex) {
            Logger.getLogger(JugueteCT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(JugueteCT.class.getName()).log(Level.SEVERE, null, ex);
        }
        createPieModels();

        EmpresaDao empresaDao = new EmpresaDao();
        if (null != Sesion.obtenerSesion().getIdTipoUsuario().getIdTipoUsuario()) {
            switch (Sesion.obtenerSesion().getIdTipoUsuario().getIdTipoUsuario()) {

                case 3:

                    Empresa emp = new Empresa(Sesion.obtenerSesion().getIdEmpresa().getIdEmpresa());
                    empresa = empresaDao.consultarEmpresa(emp);

                    break;
            }
        }
    }

    public Inventario getJuguete() {
        return juguete;
    }

    public void setJuguete(Inventario juguete) {
        this.juguete = juguete;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public int getVista() {
        return vista;
    }

    public void setVista(int vista) {
        this.vista = vista;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public List<Inventario> getInventariosMasSeleccionados() {
        return inventariosMasSeleccionados;
    }

    public void setInventariosMasSeleccionados(List<Inventario> inventariosMasSeleccionados) {
        this.inventariosMasSeleccionados = inventariosMasSeleccionados;
    }

    public List<Inventario> getInventariosMasVotados() {
        return inventariosMasVotados;
    }

    public void setInventariosMasVotados(List<Inventario> inventariosMasVotados) {
        this.inventariosMasVotados = inventariosMasVotados;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }

    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }

    public BarChartModel getAnimatedModel3() {
        return animatedModel3;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public UploadedFile getExcel() {
        return excel;
    }

    public void setExcel(UploadedFile excel) {
        this.excel = excel;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Inventario> getInventariosSeleccionados() {
        return inventariosSeleccionados;
    }

    public void setInventariosSeleccionados(List<Inventario> inventariosSeleccionados) {
        this.inventariosSeleccionados = inventariosSeleccionados;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Usuario> getUsuariosPorVotar() {
        return usuariosPorVotar;
    }

    public void setUsuariosPorVotar(List<Usuario> usuariosPorVotar) {
        this.usuariosPorVotar = usuariosPorVotar;
    }

    public List<Pedido> getEntregaArticulos() {
        return entregaArticulos;
    }

    public void setEntregaArticulos(List<Pedido> entregaArticulos) {
        this.entregaArticulos = entregaArticulos;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<Inventario> getInventariosMasVotadosVerificacion() {
        return inventariosMasVotadosVerificacion;
    }

    public void setInventariosMasVotadosVerificacion(List<Inventario> inventariosMasVotadosVerificacion) {
        this.inventariosMasVotadosVerificacion = inventariosMasVotadosVerificacion;
    }

    public List<Inventario> getInventariosSinVotacionVerificacion() {
        return inventariosSinVotacionVerificacion;
    }

    public void setInventariosSinVotacionVerificacion(List<Inventario> inventariosSinVotacionVerificacion) {
        this.inventariosSinVotacionVerificacion = inventariosSinVotacionVerificacion;
    }

    public Comite getComite() {
        return comite;
    }

    public void setComite(Comite comite) {
        this.comite = comite;
    }

    public String getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(String anioActual) {
        this.anioActual = anioActual;
    }

    public Date getFechaActual2() {
        return fechaActual2;
    }

    public void setFechaActual2(Date fechaActual2) {
        this.fechaActual2 = fechaActual2;
    }

    public UploadedFile getArExcel() {
        return arExcel;
    }

    public void setArExcel(UploadedFile arExcel) {
        this.arExcel = arExcel;
    }

    public String getArchivoExcel() {
        return archivoExcel;
    }

    public void setArchivoExcel(String archivoExcel) {
        this.archivoExcel = archivoExcel;
    }

    //Metodos
    public void metodo() throws IOException {

        if (operacion == 0) {
            registrar();
        } else if (operacion == 1) {
            modificar();
            //Reiniciamos banderas
            nombreOperacion = "Registrar";
            operacion = 0;
        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {
            nombreOperacion = "Modificar";
        }
    }

    public void cancelar() {
        juguete = new Inventario();
        nombreOperacion = "Registrar";
        operacion = 0;
    }

    public void registrar() {
        InventarioDao inventarioDao = new InventarioDao();
        List<Inventario> inv = new ArrayList<>();
        inv = inventarioDao.ConsultarJuguetesPorEmpresaCodigo(new Empresa(juguete.getIdEmpresa().getIdEmpresa()), juguete.getCodigo());
        if (!(inv.size() > 0)) {

            int resultado = inventarioDao.registrarJuguete(juguete);

            if (resultado == 1) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El juguete ha sido registrado correctamente");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else if (resultado == 0) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Imposible registrar juguete");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta!", "El Inventario que intenta crear ya esta registrado");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

//        inventarios = inventarioDao.consultarJuguetes();
        juguete = new Inventario();
    }

    public void modificar() {
        InventarioDao inventarioDao = new InventarioDao();
        int resultado = inventarioDao.editarJuguete(juguete);

        if (resultado == 1) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El juguete ha sido modificada correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (resultado == 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Imposible modificar juguete");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
//        inventarios = inventarioDao.consultarJuguetes();
        juguete = new Inventario();
    }

    public void eliminar() {
        InventarioDao inventarioDao = new InventarioDao();
        int resultado = inventarioDao.eliminarInventario(juguete);

        if (resultado == 1) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El juguete ha sido eliminado correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (resultado == 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Imposible eliminar juguete");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

//        inventarios = inventarioDao.consultarJuguetes();
        juguete = new Inventario();
    }

    public void consultarJuguetePorGeneroYEdad(int edad, String genero, int idPedido, int idEmpresa) {
        System.out.println("prueba: " + edad + "-" + genero + "-" + idPedido);
        InventarioDao inventarioDao = new InventarioDao();
        inventarios = inventarioDao.ConsultarJuguetesEdadGenero(edad, genero, idEmpresa);
        vista++;
        PedidoDao pedidoDao = new PedidoDao();

        pedido = pedidoDao.ConsultarPedido(idPedido);
    }

    public void consultarJuguetePorRangoYEdad() {
        InventarioDao inventarioDao = new InventarioDao();
        if (juguete.getEdadDesde() != 999999 && juguete.getEdadHasta() != 999999 && (juguete.getGenero().trim().equals("M") || juguete.getGenero().trim().equals("F") || juguete.getGenero().trim().equals("A"))) {
            inventarios = inventarioDao.ConsultarJuguetesRangoGenero(juguete.getEdadDesde(), juguete.getEdadHasta(), juguete.getGenero(), Sesion.obtenerSesion().getIdUsuario());
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", "No puede tener campos vacios");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void consultarJuguetePorRangoYEdad2() { // informacion precargada para luego de votar
        inventarios = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();
        inventarios = inventarioDao.ConsultarJuguetesRangoGenero(0, 20, "A", Sesion.obtenerSesion().getIdUsuario());

    }

    public void consultarJuguetePorComite(int idComite) {
        InventarioDao inventarioDao = new InventarioDao();
        inventarios = inventarioDao.ConsultarJuguetesComite(idComite);

    }

    public void editarStatusInventario(int idInventario) {
        InventarioDao inventarioDao = new InventarioDao();

        int valor = 0;
        valor = inventarioDao.editarStatusInventario(idInventario);
        if (valor == 1) {
            consultarJuguetesMasVotadosVerificacion(empresa.getIdEmpresa());
        }

    }

    public String consultarJuguetePorId(int idInventario, int valor) {
        images = new ArrayList<String>();

        InventarioDao inventarioDao = new InventarioDao();
        juguete = inventarioDao.ConsultarJuguetePorId(idInventario);
        juguete.setIdInventario(idInventario);
        int urls = 0;

//        if (!juguete.getUrl1().equals("") && juguete.getUrl1() != null) {
//            urls++;
//        }
        if (juguete.getUrl1() != null) {
            if (!juguete.getUrl1().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl2() != null) {
            if (!juguete.getUrl2().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl3() != null) {
            if (!juguete.getUrl3().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl4() != null) {
            if (!juguete.getUrl4().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl5() != null) {
            if (!juguete.getUrl5().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl6() != null) {
            if (!juguete.getUrl6().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl7() != null) {
            if (!juguete.getUrl7().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl8() != null) {
            if (!juguete.getUrl8().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl9() != null) {
            if (!juguete.getUrl9().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl10() != null) {
            if (!juguete.getUrl10().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl11() != null) {
            if (!juguete.getUrl11().isEmpty()) {
                urls++;
            }
        }
        if (juguete.getUrl12() != null) {
            if (!juguete.getUrl12().isEmpty()) {
                urls++;
            }
        }

        for (int i = 1; i <= urls; i++) {
            images.add(juguete.getCodigo() + "-" + i + ".jpg");
        }

        String link = "";
        if (valor != 2) {
            link = "Seleccion";
        } else {
            link = "Votar";
        }
        return link;
    }

    public void atras() {
        vista--;
    }

    public List<String> getImages() {
        return images;
    }

    public void buscarJuguetes() {
        inventarios = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();
        if (buscar.isEmpty()) {
            inventarios = inventarioDao.consultarJuguetes();
        } else {
            inventarios = inventarioDao.buscarJuguetes(buscar);
        }
    }

    public void consultarJuguetesMasSeleccionados(int idEmpresa) {
        inventarios = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();

        inventarios = inventarioDao.consultarJuguetesMasSeleccionados(idEmpresa);

    }

    public void consultarJuguetesMasVotados(int idEmpresa) {
        inventarios = new ArrayList<>();
        inventariosMasVotados = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();

        inventarios = inventarioDao.consultarJuguetesMasVotados(idEmpresa);
        inventariosMasVotados = inventarioDao.consultarJuguetesMasVotados(idEmpresa);

    }

    public void consultarJuguetesMasVotadosVerificacion(int idEmpresa) {

        inventariosMasVotadosVerificacion = new ArrayList<>();
        inventariosSinVotacionVerificacion = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();
        inventariosMasVotadosVerificacion = inventarioDao.consultarJuguetesMasVotados(idEmpresa);
        inventariosSinVotacionVerificacion = inventarioDao.consultarJuguetesSinVotar(idEmpresa);

    }

    public void consultarJuguetesPorEmpresa() {
        inventarios = new ArrayList<>();
        InventarioDao inventarioDao = new InventarioDao();
//        if (empresa.getIdEmpresa() == 0) {
//            inventarios = inventarioDao.consultarJuguetes();
//        } else {

        Empresa emp = new Empresa(empresa.getIdEmpresa());

        inventarios = inventarioDao.ConsultarJuguetesPorEmpresa(emp);
//        }
    }

    private void createAnimatedModels() throws IOException, DocumentException {
        int a = 5;
        int b = 5;
        animatedModel1 = initLinearModel();
        animatedModel1.setTitle("Line Chart");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

        animatedModel2 = initBarModel();
        animatedModel2.setTitle("Articulos Mas Seleccionados");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        Axis yAxis2 = animatedModel2.getAxis(AxisType.Y);
        yAxis2.setMin(0);
        if (inventariosMasSeleccionados.size() > 0) {
            if (inventariosMasSeleccionados.get(0).getSeleccion() > 0) {
                b = b + inventariosMasSeleccionados.get(0).getSeleccion();
                yAxis2.setMax(b);
            } else {
                yAxis2.setMax(5);
            }
        } else {
            yAxis2.setMax(b);
        }

        animatedModel3 = initBarModel2();
        animatedModel3.setTitle("Articulos Mas Votados");
        animatedModel3.setAnimate(true);
        animatedModel3.setLegendPosition("ne");
        Axis yAxis3 = animatedModel3.getAxis(AxisType.Y);
        yAxis3.setMin(0);
        if (inventariosMasVotados.size() > 0) {
            if (inventariosMasVotados.get(0).getSumatoria() > 0) {
                a = a + inventariosMasVotados.get(0).getSumatoria();
                yAxis3.setMax(a);
            } else {
                yAxis3.setMax(5);
            }
        } else {
            yAxis3.setMax(a);
        }
//        generarPDFOnClick();
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries inventario = new ChartSeries();
        inventario.setLabel("Articulos");

        if (inventariosMasSeleccionados.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                inventario.set(inventariosMasSeleccionados.get(i).getCodigo(), inventariosMasSeleccionados.get(i).getSeleccion());
            }
        } else if (inventariosMasSeleccionados.size() >= 1) {
            for (int i = 0; i < inventariosMasSeleccionados.size(); i++) {
                inventario.set(inventariosMasSeleccionados.get(i).getCodigo(), inventariosMasSeleccionados.get(i).getSeleccion());
            }
        } else {
            inventario.set("", 0);
        }

        model.addSeries(inventario);

        return model;
    }

    private BarChartModel initBarModel2() {
        BarChartModel model = new BarChartModel();

        ChartSeries inventario = new ChartSeries();
        inventario.setLabel("Articulos");

        if (inventariosMasVotados.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                inventario.set(inventariosMasVotados.get(i).getCodigo(), inventariosMasVotados.get(i).getSumatoria());
            }
        } else if (inventariosMasVotados.size() >= 1) {
            for (int i = 0; i < inventariosMasVotados.size(); i++) {
                inventario.set(inventariosMasVotados.get(i).getCodigo(), inventariosMasVotados.get(i).getSumatoria());
            }
        } else {
            inventario.set("", 0);
        }

        model.addSeries(inventario);

        return model;
    }

    private void createPieModels() {
        createPieModel1();
    }

    private void createPieModel1() {

        int votaron = 0, fvotar = 0;
        fvotar = usuariosPorVotar.size();
        votaron = personasComite - fvotar;

        pieModel1 = new PieChartModel();

        pieModel1.set("Votaron", votaron);
        pieModel1.set("Faltan por Votar", fvotar);

        pieModel1.setTitle("Votaciones");
        pieModel1.setLegendPosition("w");
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void uploadExcel(int idEmpresa) throws IOException, BiffException {

        if (arExcel != null) {

            if (idEmpresa != 0) {
                if (!(arExcel.getFileName().isEmpty())) {
                    String nombreArchivo = arExcel.getFileName();
                    String[] forms = nombreArchivo.split("\\.");
                    String formato1 = forms[0];
                    String formato = forms[1]; // 034556

                    if (formato.equals("xls")) {

//                        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//
//                        String path = UtilPath.getUrlDefinida(ec.getRealPath("/"));
//
//                        String string = path;
//                        String quitar1 = "Documents";
//                        String[] parts = string.split(quitar1);
//                        String part1 = parts[0]; // 004
//                        String part2 = parts[1]; // 034556
//
//                        String realPath = part1 + "Dropbox" + File.separator + "Cargas" + File.separator + excel.getFileName();
                        uploadAExcel(idEmpresa);

                    } else {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, " El formato subido es Incorrecto!", "");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Debe Seleccionar un Archivo!", "");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Debe Seleccionar una Empresa!", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, " Debe Seleccionar un Archivo!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

//        }
    }

    public void consultarJuguetesEdadGeneroCiudadEmpresa() {
        inventariosSeleccionados = new ArrayList<>();
        PedidoDao pedidoDao = new PedidoDao();
        inventariosSeleccionados = pedidoDao.ConsultarJuguetesEdadGeneroCiudadEmpresa(usuario.getIdEmpresa().getIdEmpresa(),
                pedido.getEdadHijo(), juguete.getGenero().trim(), ciudad.getIdCiudad());
    }

    public void filtrarPorEmpresaJuguetesMasVotadosYSeleccionados() throws IOException, DocumentException {

        InventarioDao inventarioDao = new InventarioDao();
        VotacionDao votacionDao = new VotacionDao();
        PedidoDao pedidoDao = new PedidoDao();

        if ((juguete.getGenero().trim().equals("F") || juguete.getGenero().trim().equals("M") || juguete.getGenero().trim().equals("A")) && (juguete.getEdadDesde() == 999999 || juguete.getEdadHasta() == 999999)) {
            inventariosMasSeleccionados = inventarioDao.consultarJuguetesMasSeleccionadosGenero(empresa.getIdEmpresa(), juguete.getGenero().trim());

            inventariosMasVotados = inventarioDao.consultarJuguetesMasVotadosGenero(empresa.getIdEmpresa(), juguete.getGenero().trim());

        } else if ((juguete.getGenero().trim().equals("")) && (juguete.getEdadDesde() == 999999 && juguete.getEdadHasta() == 999999)) {
            inventariosMasSeleccionados = inventarioDao.consultarJuguetesMasSeleccionados(empresa.getIdEmpresa());

            inventariosMasVotados = inventarioDao.consultarJuguetesMasVotados(empresa.getIdEmpresa());

        } else if ((juguete.getGenero().trim().equals("F") || juguete.getGenero().trim().equals("M") || juguete.getGenero().trim().equals("A")) && (juguete.getEdadDesde() >= 0 && juguete.getEdadHasta() >= 0)) {

            inventariosMasSeleccionados = inventarioDao.consultarJuguetesMasSeleccionadosRangoGenero(empresa.getIdEmpresa(), juguete.getGenero().trim(), juguete.getEdadDesde(), juguete.getEdadHasta());

            inventariosMasVotados = inventarioDao.consultarJuguetesMasVotadosRangoGenero(empresa.getIdEmpresa(), juguete.getGenero().trim(), juguete.getEdadDesde(), juguete.getEdadHasta());

        } else if ((juguete.getGenero().trim().equals("")) && (juguete.getEdadDesde() >= 0 && juguete.getEdadHasta() >= 0)) {

            inventariosMasSeleccionados = inventarioDao.consultarJuguetesMasSeleccionadosRangoGenero(empresa.getIdEmpresa(), juguete.getGenero().trim(), juguete.getEdadDesde(), juguete.getEdadHasta());

            inventariosMasVotados = inventarioDao.consultarJuguetesMasVotadosRangoGenero(empresa.getIdEmpresa(), juguete.getGenero().trim(), juguete.getEdadDesde(), juguete.getEdadHasta());

        }

        usuariosPorVotar = votacionDao.ConsultarQuienNoVoto(empresa.getIdEmpresa());

        personasComite = votacionDao.ConsultarQuienVoto(empresa.getIdEmpresa());

        entregaArticulos = pedidoDao.ConsultarPedidoPorEmpresa(empresa.getIdEmpresa());

        createAnimatedModels();

        createPieModels();
    }

    public void consultarFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = new Date();
        fechaActual = sdf.format(fecha);
    }

    public void consultarFecha2() {
        Date fecha = new Date();
        fechaActual2 = fecha;
    }

    public void consultarAnio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date fecha = new Date();
        anioActual = sdf.format(fecha);
    }

    public void uploadAExcel(int idEmpresa) throws IOException, BiffException {
        if (!arExcel.getFileName().isEmpty()) {
            try {
                String destino;
                HashMap<String, String> map = Upload.getMapPathArchivosInventarios();
                destino = map.get("path");

                if (arExcel != null) {

                    archivo.uploadFile(IOUtils.toByteArray(arExcel.getInputstream()), arExcel.getFileName(), destino);
//                empresa.setUrlLogo(map.get("url") + arExcel.getFileName());
                    destino = destino +arExcel.getFileName();
                    LeerArchivoDeExcel.registrarMasivaInventario(destino, idEmpresa);
                }
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su archivo (" + arExcel.getFileName() + ")  se ha guardado con exito.", ""));
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

}
