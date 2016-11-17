package beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Created by arpi on 30.10.2016.
 * some state class
 */
public class Client{
    private String id;
    private String fullName;
    private String greeting;
    private double type;

    public void setType(double type) {
        this.type = type;
    }

    public Client() {
    }

    public Client(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @PreDestroy
    private void destroy(){
        System.out.println("Client" + fullName + " bean deleted----");
    }
}
