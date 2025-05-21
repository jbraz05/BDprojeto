# BDprojeto
Projeto da Disciplina de Banco de Dados do 4º Período

---

<details>

<summary>Links Gerais</summary>

  - Relatório: https://docs.google.com/document/d/10fnU7U5O56TH1g30A9MZzKfAZ_S37P5FUuB6zCR-rLA/edit?tab=t.0
  
</details>

<details>

<summary>Modelo Conceitual</summary>

![image](https://github.com/user-attachments/assets/259070bb-c3a9-4ab9-afa1-deda0eb869ee)


</details>

<details>

<summary>Modelo Lógico</summary>

![image](https://github.com/user-attachments/assets/ee238aa7-b6d1-44c2-8de2-35377833c498)


</details>

<details>
<summary>💾 Como conectar ao banco de dados (duas opções)</summary>
<br>

Após vc criar um banco de dados(No dbeaver por exemplo), com o Scrpit que fornecemos na entrega e criar as tabelas:

  
Este projeto utiliza acesso direto ao banco via JDBC com `ConnectionFactory`, sem ORM.  
A senha do banco deve ser definida como **variável de ambiente `DB_PASSWORD`** para manter segurança e portabilidade.


### ✅ Opção 1 – Rodando com Spring Boot Dashboard (VS Code):


Se você está usando a extensão **Spring Boot Dashboard**:


### 🪟 No Windows:

1. **Abra o PowerShell** como adminstrador e execute:

   ```powershell
   [System.Environment]::SetEnvironmentVariable("DB_PASSWORD", "suaSenhaAqui", "User")

### 🪟 No Mac:
```bash
nano ~/.zshrc
```

Adicione esta linha no final do arquivo:
   
```bash
export DB_PASSWORD="suaSenhaAqui"

```
3. Salve e saia (Ctrl + O, depois Enter, depois Ctrl + X)


4. Aplique as alterações:

```bash
source ~/.zshrc
```

 ### ✅Verificação para ver se funcionou:

 ### 🪟 No Windows:
Powershell:
```powershell
echo $env:DB_PASSWORD
```

CMD:
```cmd
echo %DB_PASSWORD%
````
Se estiver correta, o terminal retorna:

```bash
suaSenhaAqui
```

### 🪟 No Mac:
Bash:
```bash
echo $DB_PASSWORD
```

👉 Ele deve exibir:
```bash
suaSenhaAqui
```


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
