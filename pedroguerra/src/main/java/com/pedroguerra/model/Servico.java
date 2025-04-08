package main.java.com.pedroguerra.model;
import java.util.Date;

public class Servico {
    private String id;
    private Date data;
    private String tipo;

    public Servico() {}

    public Servico(String id, Date data, String tipo) {
        this.id = id;
        this.data = data;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}