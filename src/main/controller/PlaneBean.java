package controller;

import JPA.ManageDataBase;
import entity.PlaneEntity;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "planeBean")
@RequestScoped
public class PlaneBean implements Serializable {
    private ManageDataBase manageDataBase = ManageDataBase.getPlaneDAO();
    private List<PlaneEntity> planeList;

    private int id;
    private String brand;
    private String captain;
    private String engine;
    private String series;


    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getCaptain() {
        return captain;
    }

    public String getEngine() {
        return engine;
    }

    public String getSeries() {
        return series;
    }


    public String savePlane() {
        PlaneEntity insertPlane = new PlaneEntity(id, brand, captain, engine, series);
        if (requestIsValid()) {
            sendError();
        } else
            manageDataBase.persistEntity(insertPlane);
        return "addList.xhtml";
    }

    public String toUpdatePlane(int id) {
        this.setId(id);
        PlaneEntity newPlane = manageDataBase.findById(id);
        setBrand(newPlane.getBrand());
        setCaptain(newPlane.getCaptain());
        setEngine(newPlane.getCaptain());
        setSeries(newPlane.getSeries());

        return "planeRefactor";
    }

    public List<PlaneEntity> planeListFromDb() {
        planeList = manageDataBase.getAll();
        return planeList;
    }

    public String updatePlane(int id) {
        if (requestIsValid()) {
            sendError();
        }
        PlaneEntity updatePlane = new PlaneEntity(id, brand, captain, engine, series);
        manageDataBase.update(id, updatePlane);
        return "addList";
    }

    public static void sendError() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseStatus(HttpServletResponse.SC_BAD_REQUEST);
        facesContext.responseComplete();
    }

    public void deletePlane(int id) {
        manageDataBase.remove(id);
    }

    private boolean requestIsValid() {
        return (brand.isEmpty() || captain.isEmpty() || engine.isEmpty() || series.isEmpty());
    }

}
