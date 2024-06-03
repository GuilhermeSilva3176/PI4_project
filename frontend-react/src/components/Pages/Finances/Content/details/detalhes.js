import React, { useState } from "react";
import Editar from "./editar";
import CriarItem from "./criarItem";
import './detalhesFinancas.css';

function Detalhes({ onClose, show }) {
    const [showEditar, setShowEditar] = useState(false);
    const [showCriar, setShowCriar] = useState(false);
    const [editItem, setEditItem] = useState(null);

    const handleEditar = (id, nome, valor) => {
        // Implemente a lógica de edição aqui
        console.log(`Editar item com ID ${id} para nome: ${nome}, valor: ${valor}`);
    };

    const handleCriar = (nome, valor, tipo) => {
        // Implemente a lógica de criação aqui
        console.log(`Criar novo item com nome: ${nome}, valor: ${valor}, tipo: ${tipo}`);
    };

    const despesas = [
        { id: 1, nome: "Aluguel", valor: 1000, tipo: "Despesa" },
        { id: 2, nome: "Comida", valor: 500, tipo: "Despesa" },
        { id: 3, nome: "Transporte", valor: 200, tipo: "Despesa" }
    ];

    const rendas = [
        { id: 1, nome: "Salário", valor: 2000, tipo: "Renda" }
    ];

    const handleExcluir = (tipo, id) => {
        console.log(`Excluir ${tipo} com ID ${id}`);
    };

    return (
        <div className={`detalhesFinancas-overlay ${show ? "active" : ""}`} onClick={onClose}>
            <div className={`detalhesFinancas-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
                <h2 className="detalhesTitleFinancas">Detalhes</h2>

                <button onClick={onClose} className="fecharAbaFinancas"></button>
                {editItem && <Editar show={showEditar} onClose={() => setShowEditar(false)} item={editItem} handleEditar={handleEditar} />}
                <div className="table-createbtn">
                    <table className="tabela-detalhesFinancas">
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Tipo</th>
                                <th>Valor</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {despesas.map((despesa) => (
                                <tr key={despesa.id}>
                                    <td>{despesa.nome}</td>
                                    <td>{despesa.tipo}</td>
                                    <td>{despesa.valor}</td>
                                    <td>
                                        <div className="dropdown">
                                            <button className="dropbtn">⋮</button>
                                            <div className="dropdown-content">
                                                <a href="#editar" className="botao-editar" onClick={(e) => { e.preventDefault(); setShowEditar(true); setEditItem(despesa); }}>Editar</a>
                                                <a href="#excluir" className="botao-excluir" onClick={(e) => { e.preventDefault(); handleExcluir(despesa.tipo, despesa.id); }}>Excluir</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            {rendas.map((renda) => (
                                <tr key={renda.id}>
                                    <td>{renda.tipo}</td>
                                    <td>{renda.nome}</td>
                                    <td>{renda.valor}</td>
                                    <td>
                                        <div className="dropdown">
                                            <button className="dropbtn">⋮</button>
                                            <div className="dropdown-content">
                                                <a href="#editar" className="botao-editar" onClick={(e) => { e.preventDefault(); setShowEditar(true); setEditItem(renda); }}>Editar</a>
                                                <a href="#excluir" className="botao-excluir" onClick={(e) => { e.preventDefault(); handleExcluir(renda.tipo, renda.id); }}>Excluir</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <button className="botao-criar" onClick={(e) => { e.preventDefault(); setShowCriar(true); }}>Novo</button>
                    {showCriar && <CriarItem show={showCriar} onClose={() => setShowCriar(false)} handleCriar={handleCriar} />}
                </div>
            </div>
        </div>
    );
}

export default Detalhes;
