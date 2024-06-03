// detalhesInvestimentos.js
import React, { useState } from "react";
import Comprar from "./content/comprar";
import Vender from "./content/vender";
import "./detalhesInvestimentos.css";

function Detalhes({ onClose, show }) {
  const [showComprar, setShowComprar] = useState(false);
  const [showVender, setShowVender] = useState(false);

  const handleVerComprarClick = () => {
    setShowComprar(true);
  };

  const handleVerVenderClick = () => {
    setShowVender(true);
  };

  const dadosFicticios = [
    { id: 1, nome: "Item 1", quantidade: 5, precoUnitario: 10, precoAtual: 50},
    { id: 2, nome: "Item 2", quantidade: 3, precoUnitario: 15, precoAtual: 50},
    { id: 3, nome: "Item 3", quantidade: 2, precoUnitario: 20, precoAtual: 40},
    { id: 4, nome: "Item 4", quantidade: 1, precoUnitario: 25, precoAtual: 40},
    { id: 5, nome: "Item 5", quantidade: 3, precoUnitario: 20, precoAtual: 30},
    { id: 6, nome: "Item 6", quantidade: 5, precoUnitario: 25, precoAtual: 30},
  ];

  return (
    <div className={`detalhes-overlay ${show ? "active" : ""}`} onClick={onClose}>
      <div className={`detalhes-content ${show ? "active" : ""}`} onClick={(e) => e.stopPropagation()}>
        <h2 className="detalhesTitle">Detalhes</h2>
        <button onClick={onClose} className="fecharAba"></button>
        <table className="tabela-detalhes">
          <thead>
            <tr>
              <th>Ticker</th>
              <th>Quantidade</th>
              <th>Preço Unitário</th>
              <th>Preço Atual</th>
            </tr>
          </thead>
          <tbody>
          {dadosFicticios.map((item) => (
              <tr key={item.id}>
                <td>{item.nome}</td>
                <td>{item.quantidade}</td>
                <td>{item.precoUnitario}</td>
                <td>{item.precoAtual}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <button id="btnAcoesInv" className="btn-vender" onClick={handleVerVenderClick}>Vender</button>
        <button id="btnAcoesInv" className="btn-comprar" onClick={handleVerComprarClick}>Comprar</button>
        {showComprar && <Comprar onClose={() => setShowComprar(false)} />}
        {showVender && <Vender onClose={() => setShowVender(false)} />}
      </div>
    </div>
  );
}

export default Detalhes;
