// detalhesInvestimentos.js
import React, { useState } from "react";
import Comprar from "./content/comprar";
import Vender from "./content/vender";
import "./detalhesInvestimentos.css";
import PropTypes from "prop-types";

function Detalhes({ onClose, show, investmentsList }) {
  const [showComprar, setShowComprar] = useState(false);
  const [showVender, setShowVender] = useState(false);

  const convertEnum = (type) => {
    switch (type) {
      case "STOCK":
        return "Ação";
      case "FII":
        return "FII";
      case "FIXED_INCOME":
        return "Renda Fixa";
    }
  }

  const handleVerComprarClick = () => {
    setShowComprar(true);
  };

  const handleVerVenderClick = () => {
    setShowVender(true);
  };
  return (
    <div className={`detalhes-overlay ${show ? "active" : ""}`} onClick={onClose}>
      <div className={`detalhes-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
        <h2 className="detalhesTitle">Detalhes</h2>
        <button onClick={onClose} className="fecharAba"></button>
        <table className="tabela-detalhes">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Quantidade</th>
              <th>Preço Unitário</th>
              <th>Tipo</th>
            </tr>
          </thead>
          <tbody>
          {investmentsList?.map((item) => (
              <tr key={item.id}>
                <td>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.price}</td>
                <td>{convertEnum(item.type)}</td>
                {/*<td>{item.precoAtual}</td> Actual price should be fetched from API, will be added later*/}
              </tr>
            ))}
          </tbody>
        </table>
        <button id="btnAcoesInv" className="btn-vender" onClick={handleVerVenderClick}>Vender</button>
        <button id="btnAcoesInv" className="btn-comprar" onClick={handleVerComprarClick}>Comprar</button>
        {showComprar && <Comprar onClose={() => setShowComprar(false)} />}
        {showVender && <Vender investmentList={investmentsList} onClose={() => setShowVender(false)} />}
      </div>
    </div>
  );
}
Detalhes.propTypes = {
    onClose: PropTypes.func.isRequired,
    show: PropTypes.bool.isRequired,
    investmentsList: PropTypes.array.isRequired,
};
 
export default Detalhes;
