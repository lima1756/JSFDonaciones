/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Database.Objeto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Luis Iván Morett
 */
@ManagedBean
@ViewScoped
public class ObjetosBean {

    
    private Objeto obj;
    private List<Objeto> lista;
    private List<Objeto> misObjetos;
    private List<Objeto> misDonaciones;
    private List<String> tipos;

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }
    

    public List<Objeto> getMisObjetos() {
        return misObjetos;
    }

    public void setMisObjetos(List<Objeto> misObjetos) {
        this.misObjetos = misObjetos;
    }

    public List<Objeto> getMisDonaciones() {
        return misDonaciones;
    }

    public void setMisDonaciones(List<Objeto> misDonaciones) {
        this.misDonaciones = misDonaciones;
    }

    public List<Objeto> getLista() {
        return lista;
    }

    public void setLista(List<Objeto> lista) {
        this.lista = lista;
    }
    
    public Objeto getObj() {
        return obj;
    }

    public void setObj(Objeto obj) {
        this.obj = obj;
    }
    /**
     * Creates a new instance of ObjetosBean
     */
    public ObjetosBean() {
    }
    
    @PostConstruct
    public void init()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer userId = (Integer) context.getExternalContext().getSessionMap().get("id");
        if(userId != null)
        {
            obj = new Objeto();
            lista = new ArrayList();
            misDonaciones = new ArrayList();
            misObjetos = new ArrayList();
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "persistence" );
            EntityManager entitymanager = emfactory.createEntityManager();
            Query query = entitymanager.createNamedQuery("Objeto.findAll", Objeto.class);
            List<Objeto> aux = query.getResultList();
            for(Objeto x: aux)
            {
                if(x.getDonadoA()==null)
                {   
                    System.out.println(x.getNombre());
                    lista.add(x);
                }
                if(x.getDonadoPor()==userId)
                { 
                    misDonaciones.add(x);
                }
                if(x.getDonadoA() == userId)
                {
                    misObjetos.add(x);
                }
            }
            query = entitymanager.createQuery("SELECT o.tipo FROM Objeto o GROUP BY o.tipo");
            tipos = query.getResultList();
        }
        else
        {
            try {
                context.getExternalContext().redirect("/JSFDonaciones");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void saveObj()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer userId = (Integer) context.getExternalContext().getSessionMap().get("id");
        obj.setDonadoPor(userId);
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        entitymanager.persist(obj);
        entitymanager.getTransaction().commit();
        entitymanager.close();
        try {
            context.getExternalContext().redirect("/JSFDonaciones/faces/start.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mine(Integer id)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer userId = (Integer) context.getExternalContext().getSessionMap().get("id");
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        Objeto x = entitymanager.find(Objeto.class, id);
        x.setDonadoA(userId);
        
        Query query = entitymanager.createQuery("Update Objeto o SET o.donadoA = " + userId.toString() + " WHERE o.idobjeto = " + id.toString());
        query.executeUpdate();
        entitymanager.getTransaction().commit();
        try {
            context.getExternalContext().redirect("/JSFDonaciones/faces/start.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
