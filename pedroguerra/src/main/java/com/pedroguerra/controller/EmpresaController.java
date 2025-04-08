package main.java.com.pedroguerra.controller;

import com.pedroguerra.dao.EmpresaDAO;
import com.pedroguerra.model.Empresa;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private final EmpresaDAO dao = new EmpresaDAO();

    @PostMapping
    public String criar(@RequestBody Empresa empresa) {
        return dao.inserir(empresa) ? "Inserido com sucesso!" : "Erro ao inserir.";
    }

    @GetMapping
    public List<Empresa> listar() {
        return dao.listar();
    }

    @PutMapping
    public String atualizar(@RequestBody Empresa empresa) {
        return dao.atualizar(empresa) ? "Atualizado com sucesso!" : "Erro ao atualizar.";
    }

    @DeleteMapping("/{cnpj}")
    public String deletar(@PathVariable String cnpj) {
        return dao.deletar(cnpj) ? "Deletado com sucesso!" : "Erro ao deletar.";
    }
}
