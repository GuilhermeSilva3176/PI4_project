import React, {useState} from "react";
import Editar from "./editar";
import CriarItem from "./criarItem";
import './detalhesFinancas.css';
import axios from "axios";

function Detalhes({onClose, show, financeList}) {
    const [showEditar, setShowEditar] = useState(false);
    const [showCriar, setShowCriar] = useState(false);
    const [editItem, setEditItem] = useState(null);

    const handleEditar = (id, nome, valor, tipo) => {
        // Implemente a lógica de edição aqui
        const financePutRequest = {
            id: id,
            name: nome,
            value: valor,
            type: tipo,
            description: "Description", //Will have utility in the future
            start_date: Date.now(),
            end_date: Date.now()
        }

        axios.put('http://localhost:8080/api/v1/finances', financePutRequest, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        }).then(response => {
                if (response.status === 200) {
                    console.log(`Editar item com ID ${id}, nome: ${nome}, valor: ${valor}, tipo: ${tipo}`)
                    alert("Item editado com sucesso!")
                    setShowEditar(false)
                    window.location.reload()
                }
        }).catch(error => {
            console.error(error);
            alert("Erro ao editar item " + error)
        })
    }

    const handleCriar = (nome, valor, tipo) => {
        // Implemente a lógica de criação aqui
        const financePostRequest = {
            name: nome,
            value: valor,
            type: tipo,
            description: "Description", //Will have utility in the future
            start_date: Date.now(),
            end_date: Date.now()
        }

        axios.post('http://localhost:8080/api/v1/finances', financePostRequest, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                if (response.status === 201) {
                    console.log(`Criar novo item com nome: ${nome}, valor: ${valor}, tipo: ${tipo}`);
                    alert("Item criado com sucesso!")
                    setShowCriar(false)
                    window.location.reload()
                }
            })
            .catch(error => {
                console.error(error);
                alert("Erro ao criar item " + error)
            })
    };

    const handleExcluir = (tipo, id) => {
        console.log(`Excluir ${tipo} com ID ${id}`);
    };

    return (
        <div className={`detalhesFinancas-overlay ${show ? "active" : ""}`} onClick={onClose}>
            <div className={`detalhesFinancas-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
                <h2 className="detalhesTitleFinancas">Detalhes</h2>

                <button onClick={onClose} className="fecharAbaFinancas"></button>
                {editItem && <Editar show={showEditar} onClose={() => setShowEditar(false)} item={editItem}
                                     handleEditar={handleEditar}/>}
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
                        {financeList?.map((finance) => (
                            <tr key={finance.id}>
                                <td>{finance.name}</td>
                                <td>{finance.type}</td>
                                <td>{finance.value}</td>
                                <td>
                                    <div className="dropdown">
                                        <button className="dropbtn">⋮</button>
                                        <div className="dropdown-content">
                                            <a href="#editar" className="botao-editar" onClick={(e) => {
                                                e.preventDefault();
                                                setShowEditar(true);
                                                setEditItem(finance);
                                            }}>Editar</a>
                                            <a href="#excluir" className="botao-excluir" onClick={(e) => {
                                                e.preventDefault();
                                                handleExcluir(finance.type, finance.id);
                                            }}>Excluir</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <button className="botao-criar" onClick={(e) => {
                        e.preventDefault();
                        setShowCriar(true);
                    }}>Novo
                    </button>
                    {showCriar &&
                        <CriarItem show={showCriar} onClose={() => setShowCriar(false)} handleCriar={handleCriar}/>}
                </div>
            </div>
        </div>
    );
}

export default Detalhes;
