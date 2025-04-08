package com.pedroguerra.dao;

import com.pedroguerra.model.Empresa;
import java.sql.*;
import java.util.*;

public class EmpresaDAO {
    private final String url = "jdbc:mysql://localhost:3306/pedroguerra";
    private final String user = "root";
    private final String password = "Felipe2006$";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean inserir(Empresa empresa) {
        String sql = "INSERT INTO Empresa (cnpj, nome, contato, numero, bairro, cidade, rua) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getCnpj());
            stmt.setString(2, empresa.getNome());
            stmt.setString(3, empresa.getContato());
            stmt.setString(4, empresa.getNumero());
            stmt.setString(5, empresa.getBairro());
            stmt.setString(6, empresa.getCidade());
            stmt.setString(7, empresa.getRua());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Empresa> listar() {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empresa";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Empresa emp = new Empresa();
                emp.setCnpj(rs.getString("cnpj"));
                emp.setNome(rs.getString("nome"));
                emp.setContato(rs.getString("contato"));
                emp.setNumero(rs.getString("numero"));
                emp.setBairro(rs.getString("bairro"));
                emp.setCidade(rs.getString("cidade"));
                emp.setRua(rs.getString("rua"));
                lista.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}