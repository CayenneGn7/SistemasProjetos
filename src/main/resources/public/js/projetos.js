const API_URL = "/api/projetos";

const formProjeto = document.getElementById("formProjeto");
const listaProjetos = document.getElementById("listaProjetos");
const mensagem = document.getElementById("mensagem");

async function listarProjetos() {
    try {
        const resposta = await fetch(API_URL);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os projetos.");
        }

        const projetos = await resposta.json();
        exibirProjetos(projetos);
    } catch (erro) {
        mensagem.textContent = erro.message;
    }
}

function exibirProjetos(projetos) {
    listaProjetos.replaceChildren();

    if (projetos.length === 0) {
        listaProjetos.textContent = "Nenhum projeto cadastrado.";
        return;
    }

    projetos.forEach(projeto => {
        const card = document.createElement("article");
        card.className = "projeto";

        const titulo = document.createElement("h3");
        titulo.textContent = projeto.nome;

        const descricao = document.createElement("p");
        descricao.textContent = projeto.descricao || "Sem descrição.";

        const categoria = document.createElement("p");
        categoria.textContent = `Categoria: ${projeto.categoria}`;

        const status = document.createElement("p");
        status.textContent = `Status: ${projeto.status}`;

        const id = document.createElement("p");
        id.textContent = `ID: ${projeto.id}`;

        const botaoExcluir = document.createElement("button");
        botaoExcluir.textContent = "Excluir";
        botaoExcluir.type = "button";
        botaoExcluir.addEventListener("click", () => {
            excluirProjeto(projeto.id);
        });

        card.append(titulo, descricao, categoria, status, id, botaoExcluir);
        listaProjetos.appendChild(card);
    });
}

formProjeto.addEventListener("submit", async evento => {
    evento.preventDefault();

    const projeto = {
        id: Number(document.getElementById("id").value),
        nome: document.getElementById("nome").value.trim(),
        descricao: document.getElementById("descricao").value.trim(),
        categoria: document.getElementById("categoria").value,
        status: document.getElementById("status").value
    };

    try {
        const resposta = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(projeto)
        });

        if (!resposta.ok) {
            const erro = await resposta.json();
            throw new Error(erro.message || "Não foi possível cadastrar o projeto.");
        }

        mensagem.textContent = "Projeto cadastrado com sucesso!";
        formProjeto.reset();
        await listarProjetos();
    } catch (erro) {
        mensagem.textContent = erro.message;
    }
});

async function excluirProjeto(id) {
    try {
        const resposta = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!resposta.ok) {
            const erro = await resposta.json();
            throw new Error(erro.message || "Não foi possível excluir o projeto.");
        }

        mensagem.textContent = "Projeto excluído com sucesso!";
        await listarProjetos();
    } catch (erro) {
        mensagem.textContent = erro.message;
    }
}

listarProjetos();