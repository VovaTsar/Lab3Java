package com.lab.controller;

import com.lab.dao.DaoFactory;
import com.lab.dao.PlaneDao;
import com.lab.dao.UserDao;
import com.lab.entity.Plane;
import com.lab.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "planeBean")
@RequestScoped
@Getter
@Setter
public class PlaneBean implements Serializable {
    private PlaneDao planeDao ;
    private List<Plane> planeList;

    private long id;
    private String brand;
    private String captain;
    private String engine;
    private String series;
    private String userLogin;
    private boolean saveFlag = false;


public String savePlane() {

    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    User currentUser = (User)
            ((HttpSession) externalContext.getSession(false))
                    .getAttribute("User");
    long userId = currentUser.getId();
    Plane insertPlane = new Plane(id,brand,captain,engine,series,currentUser);

    try(PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {

        System.out.println(insertPlane);
        if (planeDao.create(insertPlane))
            return "/user/planeList";
        else return "/error/400?faces-redirect=true&includeViewParams=true";
    }
}

    public String savePlaneAdmin() {
        User forUser;
        try (UserDao userDao = DaoFactory.getInstance().getUserDao()){
            forUser = userDao.getByLogin(userLogin).get();
        }
        Plane insertPlane = new Plane(id,brand,captain,engine,series,forUser);

        try(PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {
            System.out.println(insertPlane);
            if (planeDao.create(insertPlane))
                return "/admin/planeList";
            else return "/error/400?faces-redirect=true&includeViewParams=true";
        }
    }


    public List<Plane> getAllPlanes() {
        try (PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {
            return planeDao.getAll();
        }
    }
    public String toUpdatePage(int id){
        this.id = id;
        try(PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {
           Plane  newPlane = planeDao.getById(id).get();
            this.brand = newPlane.getBrand();
            this.captain = newPlane.getCaptain();
            this.engine = newPlane.getEngine();
            this.series = newPlane.getSeries();
        }
        System.out.println("FORWARD TO UPDATE PAGE");
        return "/user/updatePlane?faces-redirect=true&includeViewParams=true";
    }


    public String updatePlane(int id){
        try(PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {
            if (validation() || !planeDao.getById(id).isPresent()) {
                return "/error/400?faces-redirect=true&includeViewParams=true";
            }
            Plane updatePlane = planeDao.getById(id).get();
            updatePlane.setBrand(brand);
            updatePlane.setCaptain(captain);
            updatePlane.setEngine(engine);
            updatePlane.setSeries(series);
            planeDao.update(updatePlane);
        }
        System.out.println("UPDATE PLANE " + id + " " + brand + " " + captain+ " " + engine+ " " + series);
        return "/user/planeList?faces-redirect=true";
    }


    public void deletePlane(int id){
        try(PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao()) {
            planeDao.remove(id);
        }
    }

    private boolean validation(){
        boolean result = (id == 0 || brand.isEmpty()|| captain.isEmpty()|| engine.isEmpty()|| series.isEmpty());
        System.out.println("VALIDATION FAILS: " + result);
        return result;
    }
}
