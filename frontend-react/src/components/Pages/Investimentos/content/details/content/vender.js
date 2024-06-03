import React from "react";
import './vender.css';

function Vender({ onClose }) {
  return (
    <div className="vender-overlay active" onClick={onClose}>
      <div className="vender-content active" onClick={(e) => e.stopPropagation()}>
        <h2 className="venderTitle">Vender</h2>
        <button onClick={onClose} className="fecharAba"></button>
        <form className="formVender">
          <div className="formVendas-group">
            <label htmlFor="produto">
              Produto:
              <input type="text" id="produto" />
            </label>
          </div>
          <div className="formVendas-group">
            <label htmlFor="quantidade">
              Quantidade:
              <input type="number" id="quantidade" />
            </label>
          </div>
          <div className="formVendas-group">
            <label htmlFor="precoUnitario">
              Preço Unitário:
              <input type="number" id="precoUnitario" />
            </label>
          </div>
          <button type="button">Vender</button>
        </form>
      </div>
    </div>
  );
}

export default Vender;
