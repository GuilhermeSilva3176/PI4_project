import React, {useState} from "react";
import './comprar.css';
import axios from "axios";

function Comprar({onClose}) {
    const [productName, setProductName] = useState('');
    const [quantity, setQuantity] = useState(0);
    const [unitPrice, setUnitPrice] = useState(0);
    const [investmentType, setInvestmentType] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log('Comprando...');

        const investmentPostRequest = {
            name: productName,
            quantity: quantity,
            price: unitPrice,
            type: investmentType,
            start_date: Date.now(),
            end_date: null,
        }

        axios.post('api/v1/investments', investmentPostRequest, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        }).then((response) => {
            if (response.status === 201) {
                alert('Compra realizada com sucesso!');
                window.location.reload()
            }
        }).catch((error) => {
            alert('Erro ao comprar investimento: ' + error);
        })
    }

    return (
        <div className="comprar-overlay active" onClick={onClose}>
            <div className="comprar-content active" onClick={(e) => e.stopPropagation()}>
                <button onClick={onClose} className="fecharAba"></button>
                <h2 className="comprarTitle">Comprar</h2>
                <form className="formComprar">
                    <div className="formComprar-group">
                        <label htmlFor="produto">Produto:</label>
                        <input type="text" id="produto" value={productName} onChange={(event) =>
                            setProductName(event.target.value)}/>
                    </div>
                    <div className="formComprar-group">
                        <label htmlFor="quantidade">Quantidade:</label>
                        <input type="number" id="quantidade" onChange={(event) =>
                            setQuantity(parseInt(event.target.value))}/>
                    </div>
                    <div className="formComprar-group">
                        <label htmlFor="precoUnitario">Preço Unitário:</label>
                        <input type="number" id="precoUnitario" onChange={(event) =>
                            setUnitPrice(parseFloat(event.target.value))}/>
                    </div>
                    <div className="formComprar-group">
                        <label htmlFor="tipoInvestimento">Tipo:</label><br/>
                        <select value={investmentType} id="tipoInvestimento"
                                onChange={(event) => setInvestmentType(event.target.value)}>
                            <option value="" disabled>Selecione uma categoria</option>
                            <option value="STOCK">Ação</option>
                            <option value="FIIS">FIIs</option>
                            <option value="FIXED_INCOME">Renda Fixa</option>
                        </select>
                    </div>
                    <button type="button">Comprar</button>
                </form>
            </div>
        </div>
    );
}

export default Comprar;
