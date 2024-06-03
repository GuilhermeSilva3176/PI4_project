import React from "react";
import './comprar.css';

function Comprar({ onClose }) {
  return (
    <div className="comprar-overlay active" onClick={onClose}>
      <div className="comprar-content active" onClick={(e) => e.stopPropagation()}>
        <button onClick={onClose} className="fecharAba"></button>
        <h2 className="comprarTitle">Comprar</h2>
        <form className="formComprar">
          <div className="formComprar-group">
            <label htmlFor="produto">Produto:</label>
            <input type="text" id="produto" />
          </div>
          <div className="formComprar-group">
            <label htmlFor="quantidade">Quantidade:</label>
            <input type="number" id="quantidade" />
          </div>
          <div className="formComprar-group">
            <label htmlFor="precoUnitario">Preço Unitário:</label>
            <input type="number" id="precoUnitario" />
          </div>
          <button type="button">Comprar</button>
        </form>
      </div>
    </div>
  );
}

export default Comprar;
