import React from "react";
import PropTypes from "prop-types";
import './FinanceTable.css';

const FinanceTable = ({tableName, financeListFiltered}) => {
    return (
        <div>
            <div className="card card-custom">
                <div className="card-header card-header-custom text-center">
                    {tableName}
                </div>
                <div className="card-body card-body-custom">
                    <table className="table table-valores">
                        <thead>
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">Valor</th>
                            <th scope="col">Tipo</th>
                        </tr>
                        </thead>
                        <tbody>
                        {financeListFiltered.map((finance) => (
                            <tr className="valuesFinance" key={finance.id}>
                                <td>{finance.name}</td>
                                <td>{finance.value}</td>
                                <td>{finance.type === 'EXPENSE' ? "Despesa" : "Renda"}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
                <div className="card-footer card-footer-custom">
                </div>
            </div>
        </div>
    )
}
FinanceTable.propTypes = {
    tableName: PropTypes.string.isRequired,
    financeListFiltered: PropTypes.array.isRequired
}

export default FinanceTable;