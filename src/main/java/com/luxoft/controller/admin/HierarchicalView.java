package com.luxoft.controller.admin;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

@ManagedBean(name="diagramHierarchicalView")
@RequestScoped
public class HierarchicalView {

    private DefaultDiagramModel model;

    @PostConstruct
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        Element vertex0 = new Element("Vertex 0", "25em", "4em");
        vertex0.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        vertex0.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
        model.addElement(vertex0);

        //CFO
        Element vertex1 = new Element("Vertex 1", "10em", "10em");
        vertex1.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        vertex1.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));

        Element vertex2 = new Element("Vertex 2", "5em", "16em");
        vertex2.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        vertex2.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));

        Element vertex3 = new Element("Vertex 3", "20em", "16em");
        vertex3.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        vertex3.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));

        //CTO
        Element vertex4 = new Element("Vertex 4", "40em", "20em");
        vertex4.addEndPoint(createEndPoint(EndPointAnchor.TOP));
        vertex4.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));

        model.addElement(vertex1);
        model.addElement(vertex2);
        model.addElement(vertex3);
        model.addElement(vertex4);

        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#aa4a4e', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");

        //connections
        model.connect(new Connection(vertex0.getEndPoints().get(1), vertex1.getEndPoints().get(0), connector));
        model.connect(new Connection(vertex0.getEndPoints().get(1), vertex4.getEndPoints().get(0), connector));
        model.connect(new Connection(vertex1.getEndPoints().get(1), vertex2.getEndPoints().get(0), connector));
        model.connect(new Connection(vertex4.getEndPoints().get(1), vertex3.getEndPoints().get(0), connector));
        model.connect(new Connection(vertex2.getEndPoints().get(1), vertex4.getEndPoints().get(0), connector));
    }

    private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#20282b'}");

        return endPoint;
    }

    public DiagramModel getModel() {
        return model;
    }
}