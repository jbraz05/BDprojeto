# BDprojeto
Projeto da Disciplina de Banco de Dados do 4º Período

---

<details>

<summary>Links Gerais</summary>

  - Relatório: https://docs.google.com/document/d/10fnU7U5O56TH1g30A9MZzKfAZ_S37P5FUuB6zCR-rLA/edit?tab=t.0
  
</details>

<details>

<summary>Modelo Conceitual</summary>

  ![image](https://github.com/user-attachments/assets/80ed9b1a-24a0-4b4a-9b58-19ba422366e8)

</details>

<details>

<summary>Modelo Lógico</summary>

  ![image](https://github.com/user-attachments/assets/a5f0c0eb-b674-471c-b488-f7be48826811)

</details>

<details>
<summary>💾 Como conectar ao banco de dados (duas opções)</summary>

  
Após vc criar um banco de dados(No dbeaver por exemplo), com o Scrpit que fornecemos na entrega e criar as tabelas:

  
Este projeto utiliza acesso direto ao banco via JDBC com `ConnectionFactory`, sem ORM.  
A senha do banco deve ser definida como **variável de ambiente `DB_PASSWORD`** para manter segurança e portabilidade.


### ✅ Opção 1 – Rodando com Spring Boot Dashboard (VS Code):


Se você está usando a extensão **Spring Boot Dashboard** (sem Maven instalado):


#### 🪟 No Windows:

1. **Abra o PowerShell** como adminstrador e execute:

   ```powershell
   [System.Environment]::SetEnvironmentVariable("DB_PASSWORD", "suaSenhaAqui", "User")


2.Feche e reabra o VS Code.


3.No Spring Boot Dashboard(Extensão do VSCODE), clique em ▶️ Run na aplicação.


### ✅ Opção 2 – Rodando com Maven no terminal


Se você prefere rodar o projeto manualmente via terminal (e tem Maven instalado):


📁 Passo 1 – Acesse a pasta raiz do projeto:

```poweshell
cd pedroguerra
```
### 💻 Passo 2 – Defina a variável DB_PASSWORD com sua senha:
▶️ No Windows (CMD):
```cmd
set DB_PASSWORD=suaSenhaAqui
mvn spring-boot:run
```
▶️ No Windows (POWERSHELL):
```powershell
$env:DB_PASSWORD = "suaSenhaAqui"
mvn spring-boot:run
```

▶️ No macOS / Linux:
```bash
export DB_PASSWORD="suaSenhaAqui"
mvn spring-boot:run
```
</details>
