package com.lab.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean(name="errorBean")
@RequestScoped
public class ErrorBean {
    private boolean saveFlag;
    private int id;
    private String brand;
    private String captain;
    private String engine;
    private String series;

    @PostConstruct
    public void init() {
        System.out.println("ERROR " + HttpServletResponse.SC_BAD_REQUEST);

        HttpServletRequest request =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        id = Integer.parseInt(request.getParameter("id"));
        brand = request.getParameter("brand");
        captain = request.getParameter("captain");
        engine = request.getParameter("engine");
        series = request.getParameter("series");
        saveFlag = Boolean.valueOf(request.getParameter("saveFlag"));
    }

    public String setErrorMessage(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletResponse hp = (HttpServletResponse) ec.getResponse();
        hp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return messageValidator();
    }

    private String messageValidator(){
        String message = "";
        if (saveFlag) return "ID already exist ";
        if (id == 0) message+="ID can't be ZERO ";
        if (brand.isEmpty()) message+="BRAND  must be specified ";
        if (captain.isEmpty()) message+="CAPTAIN  must be specified";
        if (engine.isEmpty()) message+="ENGINE  must be specified";
        if (series.isEmpty()) message+="SERIES  must be specified";
        return message;
    }

}
