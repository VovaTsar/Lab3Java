package JPA;

import entity.PlaneEntity;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PlaneEntity planeEntity = new PlaneEntity();
        //planeEntity.setId(45);
        planeEntity.setBrand("wefwef");
        planeEntity.setCaptain("wefwe");
        planeEntity.setCaptain("efefef");
        planeEntity.setEngine("efqefqe");
        planeEntity.setSeries("99999");
        PlaneEntity planeEntity1 = new PlaneEntity();
        //planeEntity.setId(45);
        planeEntity1.setBrand("wefwef");
        planeEntity1.setCaptain("wefwe");
        planeEntity1.setCaptain("efefef");
        planeEntity1.setEngine("efqefdqe");
        planeEntity1.setSeries("888");


        ManageDataBase manageDataBase = new ManageDataBase();
        List<PlaneEntity> plane = new ArrayList<>();

        manageDataBase.persistEntity(planeEntity);
        //manageDataBase.persistEntity(planeEntity1);
        //manageDataBase.removeEntity(planeEntity);
        //  manageDataBase.deleteProduct(76);
        //plane=manageDataBase.getAll();
        for (PlaneEntity o : plane) {
            System.out.println(o.toString());

        }


    }
}
