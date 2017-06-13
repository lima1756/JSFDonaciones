/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Database.Usuarios;
import java.util.Collection;
import java.util.Date;
import javax.faces.application.FacesMessage;
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
 * @author darik
 */

@ManagedBean
@Named(value = "RegistroBean")
@SessionScoped
public class RegistroBean {
    private Integer id;
    private String userName;
    private String password;
    private String nombre;
    private String confirmPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String usuario) {
        this.userName = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    
    public String registro() {
        //if(password != confirmPassword){
        //    System.out.println("contraseñas diferentes");
        //    return null;
        //}
        FacesContext context = FacesContext.getCurrentInstance();
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        String result = (String) context.getExternalContext().getSessionMap().get("user");
        if(result != null) {
            System.out.println(result);
            return null;
        }
        else {
            Usuarios user = new Usuarios();
            Query query = entitymanager.createNamedQuery("Usuarios.findByUsuario", Usuarios.class);
            query.setParameter("usuario", this.userName);
            Collection<Usuarios> results = query.getResultList();
            for(Usuarios x : results)
            {
            }
            if (results.size() > 0) {

                nombre = null;
                password = null;
                confirmPassword = null;
                userName = null;
                id = 0;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Usuario previamente registrado, pruebe iniciando sesión"));
                return "/forms/faces/registro.xhtml?error=existe";
            } else if(!password.equals(confirmPassword))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Porfavor revise que las contraseñas coincidan"));
                return null;
            }
            else
            {
                entitymanager.getTransaction().begin();
                Usuarios usuario = new Usuarios();
                usuario.setNombre(nombre);
                usuario.setUsuario(userName);
                usuario.setIdusuarios(id);
                usuario.setPass(password);
                entitymanager.persist(usuario);
                entitymanager.getTransaction().commit();
                entitymanager.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listo", "Usuario registrado"));
                return null;
            }
        }
    }
    
}
