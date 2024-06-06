import React, {useState} from "react";
import './criarItem.css';

function CriarItem({ onClose, show, handleCriar }) {
  const [nome, setNome] = useState("");
  const [valor, setValor] = useState("");
  const [tipo, setTipo] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    handleCriar(nome, valor, tipo);
    onClose();
  };

  return (
    <div className={`criarItem-overlay ${show ? "active" : ""}`} onClick={onClose}>
      <div className={`criarItem-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
        <h2 className="criarTitle">Novo Item</h2>
        <button className="fecharAbaFinancas" onClick={onClose}></button>
        <form className="formCriar" onSubmit={handleSubmit}>
          <label>
            Nome:
            <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
          </label>
          <label>
            Valor:
            <input type="number" value={valor} onChange={(e) => setValor(e.target.value)} required />
          </label>
          <label>
            Tipo:
            <select value={tipo} onChange={(e) => setTipo(e.target.value)} required>
              <option className="optionsCriar" value="">Selecione o tipo</option>
              <option className="optionsCriar" value="EXPENSE">Despesa</option>
              <option className="optionsCriar" value="INCOME">Renda</option>
            </select>
          </label>
          <button type="submit" className="createItemBtn">Criar</button>
        </form>
      </div>
    </div>
  );
}
 
export default CriarItem;
