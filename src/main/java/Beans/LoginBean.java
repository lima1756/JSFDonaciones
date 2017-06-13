/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Database.Usuarios;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@Named(value = "LoginBean")
@SessionScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        
    }
    
    private String username;
    private String password;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    


    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        
        Query query = entitymanager.createNamedQuery("Usuarios.findByUsuario", Usuarios.class);
        query.setParameter("pass", this.password);
        query.setParameter("usuario", this.username);
        List<Usuarios> results = query.getResultList();
        for(Usuarios x: results)
        {
        }
        if (results.size() < 1) {
            username = null;
            password = null;
            try {
                context.getExternalContext().redirect("/JSFDonaciones?error=log");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "/forms?error=log";
        } else {
            
            context.getExternalContext().getSessionMap().put("user", username);
            Database.Usuarios usuario = (Database.Usuarios) results.get(0);
            context.getExternalContext().getSessionMap().put("id", usuario.getIdusuarios());
            try {
                context.getExternalContext().redirect("/JSFDonaciones/faces/start.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("user");
        context.getExternalContext().getSessionMap().remove("id");
        try {
                context.getExternalContext().redirect("/JSFDonaciones");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        return "/JSFDonaciones";
    }
    
}
