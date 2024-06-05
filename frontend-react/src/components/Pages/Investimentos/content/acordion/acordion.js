import React, {useState} from "react";
import './acordion.css';
import PropTypes from "prop-types";

function Accordion({handleVerDetalhesClick, investmentList}) {
    const [previousRadioButtonId, setPreviousRadioButtonId] = useState('');

    const getInvestmentsByType = ({investmentType}) => {
        return investmentList?.size > 0 ? investmentList?.filter((investment) => investment.type === investmentType) : []
    }

    const stocks = getInvestmentsByType({investmentType: "STOCK"})
    const fiis = getInvestmentsByType({investmentType: "FII"})
    const fixedIncome = getInvestmentsByType({investmentType: "FIXED_INCOME"})

    const handleChange = (event) => {
        if (previousRadioButtonId === event.target.id) {
            event.target.checked = false;
            setPreviousRadioButtonId('');
        } else {
            setPreviousRadioButtonId(event.target.id);
        }
    }

    return (
        <ul className="accordion">
            <h2 className='title'>Minha Carteira</h2>
            <li className='val1'>
                <input type="radio" name="accordion" id="acoes" onClick={handleChange}/>
                <label className="acLabels" htmlFor="acoes">AÇÕES</label>
                <div className="content">
                    <div className="content-body">
                        <table className="table table-valores">
                            <thead>
                            <tr>
                                <th scope="col">Nome</th>
                                <th scope="col">Preço Pago</th>
                                <th scope="col">Data de Compra</th>
                            </tr>
                            </thead>
                            <tbody>
                            {stocks.map((stock) => (
                                <tr key={stock.id}>
                                    <td>{stock.name}</td>
                                    <td>{stock.value}</td>
                                    <td>{stock.start_date}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="content-footer">
                        <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
                    </div>
                </div>
            </li>
            <li className='val2'>
                <input type="radio" name="accordion" id="fiis" onClick={handleChange}/>
                <label className="acLabels" htmlFor="fiis">FIIS</label>
                <div className="content">
                    <div className="content-body">
                        <table className="table table-valores">
                            <thead>
                            <tr>
                                <th scope="col">Nome</th>
                                <th scope="col">Preço Pago</th>
                                <th scope="col">Data de Compra</th>
                            </tr>
                            </thead>
                            <tbody>
                            {fiis.map((fii) => (
                                <tr key={fii.id}>
                                    <td>{fii.name}</td>
                                    <td>{fii.value}</td>
                                    <td>{fii.start_date}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="content-footer">
                        <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
                    </div>
                </div>
            </li>
            <li className='val3'>
                <input type="radio" name="accordion" id="cdb" onClick={handleChange}/>
                <label className="acLabels" htmlFor="cdb">RENDA FIXA</label>
                <div className="content">
                    <div className="content-body">
                        <table className="table table-valores">
                            <thead>
                            <tr>
                                <th scope="col">Nome</th>
                                <th scope="col">Preço Pago</th>
                                <th scope="col">Data de Compra</th>
                            </tr>
                            </thead>
                            <tbody>
                            {fixedIncome.map((fixedIncomeUnit) => (
                                <tr key={fixedIncomeUnit.id}>
                                    <td>{fixedIncomeUnit.name}</td>
                                    {/*Quantity will be added*/}
                                    <td>{fixedIncomeUnit.value}</td>
                                    <td>{fixedIncomeUnit.start_date}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div className="content-footer">
                        <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
                    </div>
                </div>
            </li>
        </ul>
    );
}

Accordion.propTypes = {
    investmentList: PropTypes.array.isRequired
}

export default Accordion;