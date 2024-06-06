import React, {useState} from "react";
import './vender.css';
import PropTypes from "prop-types";
import axios from "axios";
import {USER_TOKEN_REF} from "../../../../../../constants/constants";

function Vender({ onClose, investmentList }) {
  const [productName, setProductName] = useState('');
  const [investmentId, setInvestmentId] = useState(0);

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log('Vendendo...');

    axios.delete('api/v1/investments/' + investmentId, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem(USER_TOKEN_REF)}`
        }
        }).then((response) => {
        if (response.status === 200) {
            alert('Venda de investimento realizada com sucesso!');
        }
        }).catch((error) => {
        console.error('Erro ao vender investimento: ' + error);
    })
  }

  const handleSelectChange = (event) => {
    const selectedId = event.target.value;
    const selectedInvestment = investmentList.find(investment => investment.id === selectedId);
    if (selectedInvestment) {
      setProductName(selectedInvestment.name);
    }
    setInvestmentId(selectedId);
  }

  return (
    <div className="vender-overlay active" onClick={onClose}>
      <div className="vender-content active" onClick={(e) => e.stopPropagation()}>
        <h2 className="venderTitle">Vender</h2>
        <button onClick={onClose} className="fecharAba"></button>
        <form className="formVender" onSubmit={handleSubmit}>
          <div className="formVendas-group">
            <label htmlFor="produto">
              Produto:
              <select value={productName} onChange={handleSelectChange} required>
                <option value="Produto" disabled>Selecione um investimento</option>
                {investmentList?.map((investment) => (
                  <option key={investment.id} value={investment.id}>{investment.name}</option>
                ))}
              </select>
            </label>
          </div>
          <div className="formVendas-group">
            <label htmlFor="quantidade">
              Quantidade:
              <input type="number" id="quantidade" required/>
            </label>
          </div>
          <div className="formVendas-group">
            <label htmlFor="precoUnitario">
              Preço Unitário:
              <input type="number" id="precoUnitario" />
            </label>
          </div>
          <button type="submit">Vender</button>
        </form>
      </div>
    </div>
  );
}

Vender.propTypes = {
  investmentList: PropTypes.array.isRequired,
    onClose: PropTypes.func.isRequired
}

export default Vender;
