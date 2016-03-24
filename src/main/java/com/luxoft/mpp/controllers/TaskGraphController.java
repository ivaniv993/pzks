package com.luxoft.mpp.controllers;

import com.luxoft.mpp.entity.model.Etl;
import com.luxoft.mpp.entity.model.TaskVertex;
import com.luxoft.mpp.entity.model.enumeration.TaskType;
import com.luxoft.mpp.service.TaskService;
import com.luxoft.mpp.service.TaskVertexServiceImpl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iivaniv on 18.03.2016.
 */
@ManagedBean(name="taskGraphController")
@ViewScoped
public class TaskGraphController extends AbstractGraphController {

    @ManagedProperty("#{taskVertexServiceImpl}")
    private TaskVertexServiceImpl taskVertexServiceImpl;

    private DefaultDiagramModel model;

    private boolean suspendEvent;

    private TaskVertex taskVertex;

    @PostConstruct
    public void init() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
        model.setDefaultConnector(connector);
        taskVertex = new TaskVertex();

    }

    public void saveGraph(){

        System.out.println();

    }

    public void onNewDiagram(){
        init();
    }

    public void addPlusTask(){
        taskVertex.setTaskType(TaskType.PLUS);
        addNewVertex(taskVertex);
    }

    public void addMinusTask(){
        taskVertex.setTaskType(TaskType.PLUS);
        addNewVertex(taskVertex);
    }

    public void addDivideTask(){
        taskVertex.setTaskType(TaskType.PLUS);
        addNewVertex(taskVertex);
    }

    public void addMultiplyTask(){
        taskVertex.setTaskType(TaskType.PLUS);
        addNewVertex(taskVertex);
    }

    private void updateDiagram(){
        List<Element> elements = model.getElements();
        int x=10, y=4;
        for(int i = 0; i < elements.size(); i++ ){

            x += 15;

            if( i == 0 )
                continue;

            if ( i % 5 ==0){
                x = 10;
                y += 15;
            }
            System.out.println("[X = "+x);
            System.out.println("Y = "+y+"]");
            elements.get(i).setX(x + "em");
            elements.get(i).setY(y + "em");

        }
    }


    public DiagramModel getModel() {
        return model;
    }

    public void onConnect(ConnectEvent event) {
        if(!suspendEvent) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected",
                    "From " + event.getSourceElement().getData()+ " To " + event.getTargetElement().getData());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            RequestContext.getCurrentInstance().update("form:msgs");
        }
        else {


            suspendEvent = false;
        }
        System.out.println("Source = "+event.getSourceElement().getData());
        System.out.println("Target = "+event.getTargetElement().getData());
    }

    public void onDisconnect(DisconnectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Disconnected",
                "From " + event.getSourceElement().getData()+ " To " + event.getTargetElement().getData());

        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().update("form:msgs");
    }

    public void onConnectionChange(ConnectionChangeEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connection Changed",
                "Original Source:" + event.getOriginalSourceElement().getData() +
                        ", New Source: " + event.getNewSourceElement().getData() +
                        ", Original Target: " + event.getOriginalTargetElement().getData() +
                        ", New Target: " + event.getNewTargetElement().getData());

        FacesContext.getCurrentInstance().addMessage(null, msg);

        RequestContext.getCurrentInstance().update("form:msgs");
        suspendEvent = true;
    }

    private EndPoint createDotEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setScope("network");
        endPoint.setTarget(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

        return endPoint;
    }

    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
        endPoint.setScope("network");
        endPoint.setSource(true);
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

        return endPoint;
    }

    private void addNewVertex(TaskVertex networkElement){
        Element vertex = new Element( networkElement, "10em", "6em");
        EndPoint endPointTop = createDotEndPoint(EndPointAnchor.TOP);
        EndPoint endPointBot = createRectangleEndPoint(EndPointAnchor.BOTTOM);
        endPointBot.setSource(true);
        endPointTop.setSource(true);
        vertex.addEndPoint(endPointBot);
        vertex.addEndPoint(endPointTop);
        model.addElement(vertex);

        updateDiagram();
    }


    public TaskVertexServiceImpl getTaskVertexServiceImpl() {
        return taskVertexServiceImpl;
    }

    public void setTaskVertexServiceImpl(TaskVertexServiceImpl taskVertexServiceImpl) {
        this.taskVertexServiceImpl = taskVertexServiceImpl;
    }

    public TaskVertex getTaskVertex() {
        return taskVertex;
    }

    public void setTaskVertex(TaskVertex taskVertex) {
        this.taskVertex = taskVertex;
    }
}



