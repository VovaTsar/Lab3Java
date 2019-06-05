package entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "plane", schema = "jdbc")
public class PlaneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "Brand")
    private String brand;
    @Column(name = "Captain")
    private String captain;
    @Column(name = "Engine")
    private String engine;
    @Column(name = "Series")
    private String series;

    public PlaneEntity() {
    }

    public PlaneEntity(int id, String brand, String captain, String engine, String series) {
        this.id = id;
        this.brand = brand;
        this.captain = captain;
        this.engine = engine;
        this.series = series;
    }


}
