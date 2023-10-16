package com.example.tp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsuariosDao {
    @Query("SELECT * FROM usuarios")
    List<Usuarios> getAll();

    @Query("SELECT * FROM usuarios WHERE usuario LIKE :usuario LIMIT 1")
    Usuarios findByName(String usuario);

    @Insert
    Long insert(Usuarios usuarios);
}
