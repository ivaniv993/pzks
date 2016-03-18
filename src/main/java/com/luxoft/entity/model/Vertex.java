package com.luxoft.entity.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by iivaniv on 18.03.2016.
 *
 */
//@Entity
//@Table(name="vertex", schema = "public")
public class Vertex {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "upload_id")
    private Etl etl;

//    @ManyToOne
    private Vertex vertex;

//    @OneToMany
    private List<Vertex> relatedVertex;

    public Vertex() { }

    public Etl getEtl() {
        return etl;
    }

    public void setEtl(Etl etl) {
        this.etl = etl;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public List<Vertex> getRelatedVertex() {
        return relatedVertex;
    }

    public void setRelatedVertex(List<Vertex> relatedVertex) {
        this.relatedVertex = relatedVertex;
    }
}
