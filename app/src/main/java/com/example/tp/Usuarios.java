package com.example.tp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuarios {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "usuario")
    String usuario;
    @ColumnInfo(name = "password")
    String password;

    public Usuarios(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
