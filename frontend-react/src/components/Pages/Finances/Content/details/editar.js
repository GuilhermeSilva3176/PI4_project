import React from "react";
import './editar.css';

function Editar({ onClose, show, item, handleEditar }) {
    const [nome, setNome] = React.useState(item.nome);
    const [valor, setValor] = React.useState(item.valor);
    const [tipo, setTipo] = React.useState(item.tipo);


    const handleSubmit = (e) => {
        e.preventDefault();
        handleEditar(item.id, nome, valor, tipo);
        onClose();
    };

    return (
        <div className={`editarFinancas-overlay ${show ? "active" : ""}`} onClick={onClose}>
            <div className={`editarFinancas-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
                <button onClick={onClose} className="fecharAbaFinancas"/>
                <form className="formEditar" onSubmit={handleSubmit}>
                    <label className="editValues">
                        <p className="valueNameTitle">Nome:</p>
                        <input className="valuesChange" type="text" value={nome} onChange={(e) => setNome(e.target.value)} />
                    </label>
                    <label className="editValues">
                        <p className="valueNameTitle">Valor:</p>
                        <input className="valuesChange" type="number" value={valor} onChange={(e) => setValor(e.target.value)} />
                    </label>
                    <label className="editValues">
                        <p className="valueNameTitle">Tipo:</p>
                        <select className="valuesChange" value={tipo} onChange={(e) => setTipo(e.target.value)} required>
                            <option className="optionsEditar" value="">Selecione o tipo</option>
                            <option className="optionsEditar" value="Despesa">Despesa</option>
                            <option className="optionsEditar" value="Renda">Renda</option>
                        </select>
                    </label>
                    <button type="submit">Salvar</button>
                </form>
            </div>
        </div>
    );
}

export default Editar;
