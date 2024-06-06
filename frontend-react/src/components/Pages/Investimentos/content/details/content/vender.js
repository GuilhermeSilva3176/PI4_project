import React, {useEffect, useState} from "react";
import './vender.css';
import PropTypes from "prop-types";
import axios from "axios";
import {USER_TOKEN_REF} from "../../../../../../constants/constants";

function Vender({onClose, investmentList}) {
    const [selectedInvestment, setSelectedInvestment] = useState(investmentList[0]);
    const [productName, setProductName] = useState(selectedInvestment?.name || 0);
    const [quantity, setQuantity] = useState(selectedInvestment?.quantity || 0);
    const [unitPrice, setUnitPrice] = useState(selectedInvestment?.price || 0);

    useEffect(() => {
        setQuantity(selectedInvestment?.quantity);
        setUnitPrice(selectedInvestment?.price);
    }, [productName]);

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Vendendo...');

        if (selectedInvestment.quantity <= quantity) {
            if (window.confirm('Selecionando esta quantidade, você confirma que venderá todo o seu investimento.')) {
                axios.delete(`http://localhost:8080/api/v1/investments/${selectedInvestment.id}`, {
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
        }

        const investmentPutRequest = {
            id: selectedInvestment.id,
            name: selectedInvestment.name,
            quantity: quantity,
            price: unitPrice,
            type: selectedInvestment.type,
            start_date: selectedInvestment.start_date,
            end_date: new Date().toISOString().split('T')[0],
        }

        axios.put('http://localhost:8080/api/v1/investments', investmentPutRequest, {
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

    const handleQuantityChange = (event) => {
        if (event.target.value < 0) {
            setQuantity(0);
        } else if (event.target.value > selectedInvestment.quantity) {
            setQuantity(selectedInvestment.quantity);
        } else {
            setQuantity(event.target.value);
        }
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
                            <select value={productName} onChange={(e) => {
                                setSelectedInvestment(investmentList.find(investment => investment.id === parseInt(e.target.value)));
                                setProductName(e.target.value)
                            }} id="produto" name="produto"
                                    className="form-select form-select-sm" required>
                                <option value="Produto" disabled>Selecione um investimento</option>
                                {investmentList?.map((investment) => (
                                    <option key={investment.id} value={investment.id}>{investment?.name}</option>
                                ))}
                            </select>
                        </label>
                    </div>
                    <div className="formVendas-group">
                        <label htmlFor="quantidade" id="quantidade">
                            Quantidade:
                            <input type="number" id="quantidade" value={quantity}
                                   onChange={handleQuantityChange} required/>
                        </label>
                    </div>
                    <div className="formVendas-group">
                        <label htmlFor="precoUnitario">
                            Preço Unitário:
                            <input type="number" id="precoUnitario" value={unitPrice}
                                   onChange={(e) => setUnitPrice(parseFloat(e.target.value))} required/>
                        </label>
                    </div>
                    <button className="btnVender" type="submit">Vender</button>
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
